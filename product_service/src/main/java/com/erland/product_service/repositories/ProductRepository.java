package com.erland.product_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.erland.product_service.model.Product;;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {

}
