package com.abcbank.ekyc.service.impl;

import com.abcbank.ekyc.dto.AccountRegisterRequest;
import com.abcbank.ekyc.dto.AccountRegisterResponse;
import com.abcbank.ekyc.entity.Account;
import com.abcbank.ekyc.repository.AccountRepository;
import com.abcbank.ekyc.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Class triển khai (Implementation) các logic nghiệp vụ đăng ký tài khoản.
 * Được đánh dấu là @Service để Spring Boot quản lý thành một Bean.
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public AccountRegisterResponse registerAccount(AccountRegisterRequest request) {
        // 1. Kiểm tra xem CCCD hoặc SĐT đã tồn tại hay chưa (Business Rule)
        if (accountRepository.existsByCitizenId(request.getCitizenId())) {
            throw new IllegalArgumentException("CCCD đã được đăng ký trong hệ thống!");
        }
        if (accountRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("Số điện thoại đã được đăng ký!");
        }

        // 2. Map dữ liệu từ DTO sang Entity
        Account account = new Account();
        account.setFullName(request.getFullName());
        account.setPhone(request.getPhone());
        account.setEmail(request.getEmail());
        account.setCitizenId(request.getCitizenId());

        // 3. Sinh số tài khoản ngẫu nhiên (Giả lập logic của Core Banking)
        String generatedAccountNumber = generateRandomAccountNumber();
        account.setAccountNumber(generatedAccountNumber);
        
        // 4. Thiết lập trạng thái ban đầu và thời gian tạo
        account.setStatus("PENDING"); // Sẽ chuyển sang ACTIVE sau khi pass Face Matching
        account.setCreatedAt(LocalDateTime.now());

        // 5. Lưu vào Database thông qua Repository
        Account savedAccount = accountRepository.save(account);

        // 6. Trả về kết quả thông qua DTO Response
        return AccountRegisterResponse.builder()
                .accountId(savedAccount.getId())
                .accountNumber(savedAccount.getAccountNumber())
                .status(savedAccount.getStatus())
                .message("Đăng ký tài khoản thành công. Trạng thái hiện tại: PENDING chờ xác thực.")
                .build();
    }

    /**
     * Hàm private dùng để giả lập việc sinh số tài khoản ngân hàng 10 số.
     */
    private String generateRandomAccountNumber() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999999);
        return String.format("1%09d", number);
    }
}
