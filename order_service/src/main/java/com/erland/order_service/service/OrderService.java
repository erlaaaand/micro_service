package com.erland.order_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.erland.order_service.model.Order;
import com.erland.order_service.repositories.OrderRepositories;
import com.erland.order_service.vo.Pelanggan;
import com.erland.order_service.vo.Product;
import com.erland.order_service.vo.ResponseTemplate;

@Service
public class OrderService {
    @Autowired
    private OrderRepositories orderRepositories;
    @Autowired
    private RestTemplate restTemplate;

    public List<Order> getAllOrders() {
        return orderRepositories.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepositories.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {
        return orderRepositories.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepositories.findById(id).orElse(null);
        if (order != null) {
            order.setProdukId(orderDetails.getProdukId());
            order.setPelangganId(orderDetails.getPelangganId());
            order.setJumlah(orderDetails.getJumlah());
            order.setTanggal(orderDetails.getTanggal());
            order.setStatus(orderDetails.getStatus());
            order.setTotal(orderDetails.getTotal());
            return orderRepositories.save(order);
        }
        return null;
    }

    public void deleteOrder(Long id) {
        orderRepositories.deleteById(id);
    }

    public List<ResponseTemplate> getOrderWithProductById(Long Id) {
        List<ResponseTemplate> responseList = new ArrayList<>();
        Order order = getOrderById(Id);
        Product product = restTemplate.getForObject("http://localhost:8081/api/products/" + order.getProdukId(),
                Product.class);
        Pelanggan pelanggan = restTemplate.getForObject("http://localhost:8082/api/pelanggan/" + order.getPelangganId(),
                Pelanggan.class);
        ResponseTemplate vo = new ResponseTemplate();
        vo.setOrder(order);
        vo.setProduct(product);
        vo.setPelanggan(pelanggan);
        responseList.add(vo);
        return responseList;
    }
}
