package com.abcbank.ekyc.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entity Class đại diện cho bảng Account trong Database.
 * Lưu trữ thông tin tài khoản cơ bản của khách hàng sau khi eKYC.
 */
@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String phone;
    private String email;
    private String citizenId;
    
    private String accountNumber;
    
    // Trạng thái tài khoản: PENDING, ACTIVE, REJECTED
    private String status;
    
    private LocalDateTime createdAt;
}
