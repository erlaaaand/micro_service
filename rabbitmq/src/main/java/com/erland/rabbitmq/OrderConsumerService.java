package com.erland.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderConsumerService {
    @Autowired 
    private JavaMailSender javaMailSender;
    
    @Value("${app.mail.username}") 
    private String sender;
    
    private final OrderRepository orderRepository;

    public OrderConsumerService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    @Transactional
    public void receiveOrder(@Payload Order order) {
        try {
            System.out.println("Order received from RabbitMQ: " + order);

            // Update status order
            order.setStatus(Order.OrderStatus.PROCESSING);
            orderRepository.save(order);

            // Simulasi proses bisnis
            processOrder(order);

            // Update status setelah selesai diproses
            order.setStatus(Order.OrderStatus.COMPLETED);
            order.setProcessedAt(java.time.LocalDateTime.now());
            orderRepository.save(order);

            System.out.println("Order processed successfully: " + order.getId());

        } catch (Exception e) {
            System.err.println("Error processing order: " + order.getId() + ", Error: " + e.getMessage());

            // Update status jika gagal
            order.setStatus(Order.OrderStatus.FAILED);
            orderRepository.save(order);

            // Bisa ditambahkan logic untuk retry atau dead letter queue
            throw new RuntimeException("Failed to process order", e);
        }
    }

    private void processOrder(Order order) {
        // Simulasi proses bisnis
        System.out.println("Processing order: " + order.getId());
        System.out.println("Sending confirmation email to: " + order.getCustomerEmail());

        // Simulasi delay processing
        try {
            Thread.sleep(2000); // Tambahkan delay simulasi
            
            // Kirim email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(order.getCustomerEmail());
            mailMessage.setSubject("Order Confirmation - " + order.getId());
            
            // Buat pesan email sederhana
            String emailText = String.format(
                "Dear Customer,\n\n" +
                "Your order has been confirmed:\n" +
                "Order ID: %s\n" +
                "Product: %s\n" +
                "Quantity: %d\n" +
                "Price: %.2f\n\n" +
                "Thank you for your purchase!\n\n" +
                "Best regards,\n" +
                "Your E-Commerce Team",
                order.getId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice()
            );
            
            mailMessage.setText(emailText);
            javaMailSender.send(mailMessage);
            
            System.out.println("Email sent successfully to: " + order.getCustomerEmail());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Processing interrupted", e);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            throw new RuntimeException("Email sending failed", e);
        }

        System.out.println("Order processing completed: " + order.getId());
    }
}