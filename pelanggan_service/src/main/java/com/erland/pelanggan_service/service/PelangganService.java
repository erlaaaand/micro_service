package com.erland.pelanggan_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erland.pelanggan_service.model.Pelanggan;
import com.erland.pelanggan_service.repositories.PelangganRepositories;

@Service

public class PelangganService {
    @Autowired
    private PelangganRepositories pelangganRepositories;

    public List<Pelanggan> getAllPelanggan() {
        return pelangganRepositories.findAll();
    }

    public Pelanggan getPelangganById(Long id) {
        return pelangganRepositories.findById(id).orElse(null);
    }

    public Pelanggan createPelanggan(Pelanggan pelanggan) {
        return pelangganRepositories.save(pelanggan);
    }

    public Pelanggan updatePelanggan(Long id, Pelanggan pelanggan) {
        pelanggan.setId(id);
        return pelangganRepositories.save(pelanggan);
    }

    public void deletePelanggan(Long id) {
        pelangganRepositories.deleteById(id);
    }
}
