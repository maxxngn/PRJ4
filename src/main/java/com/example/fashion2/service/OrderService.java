package com.example.fashion2.service;

import com.example.fashion2.dto.OrderResponseDTO;
import com.example.fashion2.dto.OrderResponseDTO.UserDTO;
import com.example.fashion2.model.Address;
import com.example.fashion2.model.Order;
import com.example.fashion2.model.OrderDetail;
import com.example.fashion2.model.Product;
import com.example.fashion2.model.ProductVariant;
import com.example.fashion2.model.User;
import com.example.fashion2.model.Voucher;
import com.example.fashion2.repository.AddressRepository;
import com.example.fashion2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.fashion2.repository.ProductVariantRepository;
import com.example.fashion2.repository.VoucherRepository;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public Order createOrder(Order order) throws MessagingException {
        List<OrderDetail> orderDetails = order.getOrderDetails();
        if (orderDetails != null) {
            // Fetch the voucher once if it's part of the order
            Voucher voucher = order.getVoucher();
            if (voucher != null) {
                // Make sure voucher is loaded from the DB before updating
                voucher = voucherRepository.findById(voucher.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Voucher not found"));
            }

            for (OrderDetail orderDetail : orderDetails) {
                // Set the order for each orderDetail
                orderDetail.setOrder(order);

                // Fetch the productVariant again from the repository to ensure we have the
                // latest entity
                ProductVariant productVariant = productVariantRepository
                        .findById(orderDetail.getProductVariant().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Product variant not found"));

                System.out.println("Before Update: ProductVariant ID = " + productVariant.getId() + ", Qty = "
                        + productVariant.getQty());

                // Decrement the quantity of the product variant based on the order quantity
                int updatedQty = productVariant.getQty() - orderDetail.getQuantity();
                System.out.println("Updated Qty: " + updatedQty);
                if (updatedQty < 0) {
                    throw new IllegalArgumentException(
                            "Not enough stock for product variant: " + productVariant.getId());
                }
                productVariant.setQty(updatedQty); // Update the qty

                // Save the updated product variant
                productVariantRepository.save(productVariant);

                // Decrement the quantity of the voucher (if any) based on the order quantity
                if (voucher != null) {
                    int updatedVoucherQty = voucher.getQty() - 1; // assuming voucher usage is per order
                    System.out
                            .println("Voucher ID: " + voucher.getId() + ", Updated Voucher Qty: " + updatedVoucherQty);
                    if (updatedVoucherQty < 0) {
                        throw new IllegalArgumentException("Voucher is out of stock.");
                    }
                    voucher.setQty(updatedVoucherQty);
                }
            }

            if (order.getAddress() != null && order.getAddress().getId() != 0) {
                order.setAddress(addressRepository.findById(order.getAddress().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Address not found with ID " + order.getAddress().getId())));
            }
        
            order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrder(order));

            // Save the order with the updated orderDetails
            Order createdOrder = orderRepository.save(order);

            // Ensure the Address and User are not null before sending an email
            System.out.println("111" + order.getAddress());
            if (order.getAddress() != null && order.getAddress().getUser() != null) {
                String toEmail = order.getAddress().getUser().getEmail();
                String subject = "Order Confirmation #" + createdOrder.getId();
                String body = "Thank you for your order!";
                emailService.sendEmail(toEmail, subject, body);
            } else {
                throw new MessagingException(
                        "User's email address is not available for Address ID: "
                                + (order.getAddress() != null ? order.getAddress().getId() : "null"));
            }

            // Save the updated voucher if it's used
            if (voucher != null) {
                voucherRepository.save(voucher); // Save the updated voucher
            }
        }

        return order;
    }

    // Get a list of all orders
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO convertToDto(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setPhone(order.getPhone());
        dto.setPrice(order.getPrice());
        dto.setStatus(order.getStatus());
        dto.setPayment(order.getPayment().toString());

        // Lấy thông tin người dùng từ Address
        User user = order.getAddress().getUser();
        UserDTO userDTO = new UserDTO();
        userDTO.setFullname(user.getFullname());
        userDTO.setUsername(user.getUsername()); // Set the username here
        dto.setUser(userDTO); // Set the UserDTO object

        List<OrderResponseDTO.OrderDetailDTO> detailDTOs = order.getOrderDetails().stream().map(orderDetail -> {
            OrderResponseDTO.OrderDetailDTO detailDTO = new OrderResponseDTO.OrderDetailDTO();
            detailDTO.setQuantity(orderDetail.getQuantity());
            detailDTO.setPrice(orderDetail.getPrice());

            Product product = orderDetail.getProductVariant().getProduct();
            OrderResponseDTO.ProductDTO productDTO = new OrderResponseDTO.ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setPrice(product.getPrice());
            productDTO.setImageUrls(product.getImageUrls());

            detailDTO.setProduct(productDTO);
            return detailDTO;
        }).collect(Collectors.toList());

        dto.setOrderDetails(detailDTOs);
        return dto;
    }

    // Get an order by id
    public Order getOrderById(int id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            // Initialize product details for each order detail
            order.getOrderDetails().forEach(orderDetail -> {
                ProductVariant variant = orderDetail.getProductVariant();
                if (variant != null && variant.getProduct() != null) {
                    Product product = variant.getProduct();
                    System.out.println("xxxx " + product.getId());
                    // Truy cập các trường của Product
                    product.getName();
                    product.getImageUrls(); // Lấy thông tin hình ảnh nếu cần
                }
            });
        }
        return order;
    }    

    public Order updateOrderStatus(int id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null && order.getStatus() < 4) { // Giả sử status tối đa là 4
            order.setStatus(order.getStatus() + 1);
            return orderRepository.save(order);
        }
        return null;
    }

    public Order cancelOrder(int id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null && order.getStatus() == 0) {
            order.setStatus(5);
            return orderRepository.save(order);
        }
        return null;
    }

    public List<OrderResponseDTO> getOrdersByUserId(int userId) {
        // Find all orders where the user's address has the given user ID
        List<Order> orders = orderRepository.findByAddress_User_Id(userId);

        // Convert orders to DTO
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
