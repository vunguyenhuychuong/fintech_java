package com.example.fintech_java.repository;

import com.example.fintech_java.entity.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWalletRepository
        extends JpaRepository<UserWallet, Long> {
}

