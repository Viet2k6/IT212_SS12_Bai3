package com.abcbank.ekyc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO nhận dữ liệu đầu vào (Request) từ phía Client.
 * Áp dụng Data Validation cho các trường thông tin.
 */
@Data
public class AccountRegisterRequest {

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)[3|5|7|8|9][0-9]{8}$", message = "Số điện thoại không đúng định dạng VN")
    private String phone;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Số CCCD không được để trống")
    @Pattern(regexp = "^[0-9]{12}$", message = "Số CCCD phải bao gồm đúng 12 chữ số")
    private String citizenId;
}
