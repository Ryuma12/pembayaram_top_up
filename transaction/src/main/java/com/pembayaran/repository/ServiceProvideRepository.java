package com.pembayaran.repository;

import com.pembayaran.model.ServiceProvide;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceProvideRepository extends CrudRepository<ServiceProvide, Long> {

    List<ServiceProvide> findByActiveOrderById(Boolean active);

    ServiceProvide findByCodeIgnoreCase(String serviceCode);
}
