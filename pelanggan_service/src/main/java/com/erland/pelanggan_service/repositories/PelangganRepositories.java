package com.erland.pelanggan_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.erland.pelanggan_service.model.Pelanggan;

@Repository

public interface PelangganRepositories extends JpaRepository<Pelanggan, Long> {

}
