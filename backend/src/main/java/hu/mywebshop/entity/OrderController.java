package hu.mywebshop.controller;

import hu.mywebshop.entity.Order;
import hu.mywebshop.entity.OrderItem;
import hu.mywebshop.entity.User;
import hu.mywebshop.service.OrderService;
import hu.mywebshop.service.UserService;
import hu.mywebshop.util.PaymentMethod;
import hu.mywebshop.util.ShippingMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService,
                           UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // Új rendelés készítése
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam String username,
                                             @RequestParam PaymentMethod paymentMethod,
                                             @RequestParam ShippingMethod shippingMethod,
                                             @RequestBody List<OrderItem> items) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        // Ha a user onlyPrepaid = true, de mégis COD-ot kért, eldöntjük, hogy engedjük-e
        if (user.isOnlyPrepaid() && paymentMethod == PaymentMethod.COD) {
            return ResponseEntity.badRequest().body(null);
        }
        Order newOrder = orderService.createOrder(user, paymentMethod, shippingMethod, items);
        return ResponseEntity.ok(newOrder);
    }

    // Rendelés státusz frissítése
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, 
                                                   @RequestParam String status) {
        Order updated = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updated);
    }

    // Elállás 8 napon belül
    @PostMapping("/{orderId}/return")
    public ResponseEntity<Order> returnOrder(@PathVariable Long orderId) {
        Order returned = orderService.returnOrder(orderId);
        return ResponseEntity.ok(returned);
    }

    // COD csomag visszautasítás
    @PostMapping("/{orderId}/cod-refuse")
    public ResponseEntity<Void> refuseCod(@PathVariable Long orderId) {
        orderService.refusePackageOnCod(orderId);
        return ResponseEntity.ok().build();
    }
}
