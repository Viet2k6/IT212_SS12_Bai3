package com.abcbank.ekyc.service;

import com.abcbank.ekyc.dto.AccountRegisterRequest;
import com.abcbank.ekyc.dto.AccountRegisterResponse;

/**
 * Interface định nghĩa các nghiệp vụ (Business Logic) liên quan đến Tài khoản.
 */
public interface AccountService {
    AccountRegisterResponse registerAccount(AccountRegisterRequest request);
}
