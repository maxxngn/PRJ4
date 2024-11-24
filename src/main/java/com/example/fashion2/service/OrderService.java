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
        // Get the list of order details
        List<OrderDetail> orderDetails = order.getOrderDetails();
        if (orderDetails == null || orderDetails.isEmpty()) {
            throw new IllegalArgumentException("The order has no details.");
        }

        // Process the voucher if present
        Voucher voucher = null;
        if (order.getVoucher() != null) {
            voucher = voucherRepository.findById(order.getVoucher().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Voucher does not exist."));
        }

        // Process each OrderDetail
        for (OrderDetail orderDetail : orderDetails) {
            // Link OrderDetail to Order
            orderDetail.setOrder(order);

            // Load the full ProductVariant
            ProductVariant productVariant = productVariantRepository
                    .findById(orderDetail.getProductVariant().getId())
                    .orElseThrow(() -> new IllegalArgumentException("ProductVariant does not exist."));
            orderDetail.setProductVariant(productVariant); // Set full ProductVariant into OrderDetail

            // Check stock
            int updatedQty = productVariant.getQty() - orderDetail.getQuantity();
            if (updatedQty < 0) {
                throw new IllegalArgumentException("Not enough stock for product: " + productVariant.getId());
            }
            productVariant.setQty(updatedQty);
            productVariantRepository.save(productVariant);

            // Process the voucher quantity (if applicable)
            if (voucher != null) {
                int updatedVoucherQty = voucher.getQty() - 1; // Each order uses 1 voucher
                if (updatedVoucherQty < 0) {
                    throw new IllegalArgumentException("Voucher is out of stock.");
                }
                voucher.setQty(updatedVoucherQty);
            }
        }

        // Link the address to the Order
        if (order.getAddress() != null && order.getAddress().getId() != 0) {
            order.setAddress(addressRepository.findById(order.getAddress().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Address does not exist with ID: " + order.getAddress().getId())));
        }

        // Save Order and OrderDetails
        Order createdOrder = orderRepository.save(order);

        // Build the order confirmation email content
        StringBuilder emailBody = new StringBuilder("Thank you for your order!\n\n")
                .append("Order ID: ").append(createdOrder.getId()).append("\n")
                .append("Total Amount: ").append(order.getPrice()).append(" VND\n")
                .append("Shipping to: ").append(order.getAddress().getAddress()).append("\n")
                .append("Phone Number: ").append(order.getPhone()).append("\n\n")
                .append("Order Details:\n");

        for (OrderDetail detail : orderDetails) {
            ProductVariant productVariant = detail.getProductVariant();
            Product product = productVariant.getProduct(); // Ensure Product is fully loaded
            emailBody.append("Product Name: ").append(product.getName())
                    .append("\nDescription: ").append(product.getDescription())
                    .append("\nPrice: ").append(detail.getPrice()).append(" VND")
                    .append("\nQuantity: ").append(detail.getQuantity())
                    .append("\n\n");
        }

        // Send confirmation email
        if (order.getAddress() != null && order.getAddress().getUser() != null) {
            String toEmail = order.getAddress().getUser().getEmail();
            String subject = "Order Confirmation #" + createdOrder.getId();
            emailService.sendEmail(toEmail, subject, emailBody.toString());
        } else {
            throw new MessagingException("Cannot send email because the user's email address does not exist.");
        }

        // Save the voucher if updated
        if (voucher != null) {
            voucherRepository.save(voucher);
        }

        return createdOrder;
    }

    // Get a list of all orders
    public List<OrderResponseDTO> getAllOrders(Integer status) {
        List<Order> orders;

        if (status != null) {
            // Filter orders by status if the parameter is provided
            orders = orderRepository.findByStatus(status, Sort.by(Sort.Direction.DESC, "id"));
        } else {
            // If no status is provided, fetch all orders
            orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        }

        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
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
                    System.out.println("xxxx 123" + product.getId());
                    // Truy cập các trường của Product
                    product.getName();
                    System.out.println("xxxx 123" + product.getName());
                    product.getImageUrls(); // Lấy thông tin hình ảnh nếu cần
                    System.out.println("xxxx 123" + product.getImageUrls());
                }
            });
        }
        return order;
    }

    public Order updateOrderStatus(int id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null && order.getStatus() < 5) { // Giả sử status tối đa là 4
            order.setStatus(order.getStatus() + 1);
            return orderRepository.save(order);
        }
        if (order != null && order.getStatus() == 6) {
            order.setStatus(2);
            return orderRepository.save(order);
        }
        return null;
    }

    public Order cancelOrder(int id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null && order.getStatus() == 1) {
            order.setStatus(6);
            return orderRepository.save(order);
        }
        return null;
    }

    public Order acceptCancelOrder(int id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null && order.getStatus() == 6) {
            order.setStatus(7);
            return orderRepository.save(order);
        }
        return null;
    }

    public List<OrderResponseDTO> getOrdersByUserId(int userId) {
        // Find all orders where the user's address has the given user ID, sorted by ID
        // in descending order
        List<Order> orders = orderRepository.findByAddress_User_Id(userId, Sort.by(Sort.Order.desc("id")));

        // Convert orders to DTO
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Order returnOrder(int id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(8);
            return orderRepository.save(order);
        }
        return null;
    }

    public Order acceptReturnOrder(int id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null && order.getStatus() == 8) {
            order.setStatus(9);
            return orderRepository.save(order);
        }
        return null;
    }
}
