package hu.mywebshop.service;

import hu.mywebshop.entity.Order;
import hu.mywebshop.entity.OrderItem;
import hu.mywebshop.entity.Product;
import hu.mywebshop.entity.User;
import hu.mywebshop.repository.OrderRepository;
import hu.mywebshop.util.PaymentMethod;
import hu.mywebshop.util.ShippingMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public double calculateShippingCost(double totalWeight) {
        // Pl. 0-2 kg 1.000 Ft, 2-10 kg 2.000 Ft, 10+ kg 3.500 Ft
        if (totalWeight <= 2.0) {
            return 1000.0;
        } else if (totalWeight <= 10.0) {
            return 2000.0;
        } else {
            return 3500.0;
        }
    }

    public Order createOrder(User user, PaymentMethod paymentMethod, ShippingMethod shippingMethod, 
                             java.util.List<OrderItem> orderItems) {
        // Először kiszámoljuk a teljes tömeget a szállításhoz
        double totalWeight = 0.0;
        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            totalWeight += product.getWeight() * item.getQuantity();
        }

        double shippingCost = calculateShippingCost(totalWeight);
        
        Order newOrder = Order.builder()
                .user(user)
                .items(orderItems)
                .paymentMethod(paymentMethod)
                .shippingMethod(shippingMethod)
                .shippingCost(shippingCost)
                .status("Új")
                .orderDate(LocalDateTime.now())
                .build();
        
        return orderRepository.save(newOrder);
    }

    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("Rendelés nem található!"));
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    // Rendelés visszaküldés (8 munkanapos elállás)
    public Order returnOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        // Itt ellenőriznénk a dátumot, termék típusát, stb.
        // Ha megfelel, beállíthatjuk a státuszt pl. "Visszaküldve"
        order.setStatus("Visszaküldve");
        return orderRepository.save(order);
    }
    
    // Csomag átvétel visszautasítása - pl. COD esetén
    public void refusePackageOnCod(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        if (order.getPaymentMethod() == PaymentMethod.COD) {
            // Logika: user.setOnlyPrepaid(true), stb.
            User user = order.getUser();
            user.setOnlyPrepaid(true);
            // Itt elmenthetnénk a user-t is, ha a userRepository rendelkezésre áll...
            order.setStatus("Visszaküldve - COD visszautasítás");
            orderRepository.save(order);
        }
    }
}
