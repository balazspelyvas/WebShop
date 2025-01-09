package hu.mywebshop.entity;

import hu.mywebshop.util.PaymentMethod;
import hu.mywebshop.util.ShippingMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ki rendelte
    @ManyToOne
    private User user;

    // A rendelés tételei
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;

    // Fizetési és szállítási módok
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod;

    // Szállítási költség
    private double shippingCost;

    // Rendelés státusz
    private String status; // pl. Új, Kiszállításra vár, Teljesítve

    // Rendelés dátuma
    private LocalDateTime orderDate;
}
