// package com.example.fashion2.repository;

// import com.example.fashion2.model.Order;
// import com.example.fashion2.model.User;
// import com.example.fashion2.model.Comment;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;

// public interface DashboardRepository extends JpaRepository<Order, Integer> {

//     // Đếm tổng số đơn hàng
//     long countByIdNotNull();

//     // Tính tổng giá trị đơn hàng
//     @Query("SELECT SUM(o.price) FROM Order o")
//     Integer countTotalPrice();

//     @Query("SELECT COUNT(o) FROM Order o WHERE o.user.role = :role")
//     long countByIdNotNullAndRole(User.Role role);

//     // Đếm tổng số bình luận
//     long countByIdNotNullAndProductIdNotNull(Comment product);
// }
