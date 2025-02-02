package com.pembayaran.repository;

import com.pembayaran.model.BalanceUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceUserRepository extends CrudRepository<BalanceUser, Long> {

    BalanceUser findByUserId(Long userId);
}
