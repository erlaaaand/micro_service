package com.erland.order_service.vo;

import com.erland.order_service.model.Order;

public class ResponseTemplate {
    Order order;
    Product product;
    Pelanggan pelanggan;
    
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Pelanggan getPelanggan() {
        return pelanggan;
    }
    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

}
