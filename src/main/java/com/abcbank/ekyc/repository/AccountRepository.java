package com.abcbank.ekyc.repository;

import com.abcbank.ekyc.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface để giao tiếp với Database (Tầng Data Access).
 * Kế thừa JpaRepository để sử dụng các hàm CRUD cơ bản.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByCitizenId(String citizenId);
    boolean existsByPhone(String phone);
}
