package com.erland.product_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erland.product_service.model.Product;
import com.erland.product_service.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService produkService;

    @GetMapping
    public List<Product> getAllProduks() {
        return produkService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProdukById(@PathVariable Long id) {
        Product produk = produkService.getProductById(id);
        return produk != null ? ResponseEntity.ok(produk) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Product createProduk(@RequestBody Product product) {
        return produkService.createProduct(product);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduk(@PathVariable Long id) {
        produkService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}