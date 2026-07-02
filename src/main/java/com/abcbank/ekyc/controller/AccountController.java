package com.abcbank.ekyc.controller;

import com.abcbank.ekyc.dto.AccountRegisterRequest;
import com.abcbank.ekyc.dto.AccountRegisterResponse;
import com.abcbank.ekyc.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller tiếp nhận các HTTP Request từ phía Client (Mobile App/Web).
 * Cung cấp API endpoint cho tính năng Đăng ký mở tài khoản.
 */
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * API Endpoint Đăng ký mở tài khoản cơ bản.
     * @param request Payload gửi từ Client
     * @return Kết quả đăng ký
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody AccountRegisterRequest request) {
        try {
            // Gọi tầng Service để xử lý nghiệp vụ
            AccountRegisterResponse response = accountService.registerAccount(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // Xử lý lỗi logic nghiệp vụ
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Xử lý lỗi hệ thống chung
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi hệ thống");
        }
    }
}
