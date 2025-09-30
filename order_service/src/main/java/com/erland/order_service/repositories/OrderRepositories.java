package com.erland.order_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.erland.order_service.model.Order;

@Repository
public interface OrderRepositories extends JpaRepository<Order, Long> {

}
