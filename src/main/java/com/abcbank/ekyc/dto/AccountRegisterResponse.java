package com.abcbank.ekyc.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO trả về kết quả (Response) cho Client sau khi đăng ký thành công.
 */
@Data
@Builder
public class AccountRegisterResponse {
    private Long accountId;
    private String accountNumber;
    private String status;
    private String message;
}
