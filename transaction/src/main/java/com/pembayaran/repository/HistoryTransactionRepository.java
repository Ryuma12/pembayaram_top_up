package com.pembayaran.repository;

import com.pembayaran.model.HistoryTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryTransactionRepository extends CrudRepository<HistoryTransaction, Long> {

    List<HistoryTransaction> findByUserIdOrderByDateTransactionDesc(Long userId);

    Page<HistoryTransaction> findByUserIdOrderByDateTransactionDesc(Long userId, Pageable pageable);

}
