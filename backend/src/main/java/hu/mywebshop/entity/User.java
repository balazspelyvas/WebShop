package hu.mywebshop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data // Lombok - generálja a gettereket, settereket, toString stb.
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;
    private String email;

    // Számlázási adatok, pl. cégnév/cím, stb.
    private String billingAddress;

    // Szállítási adatok
    private String shippingAddress;

    // Extra mező a COD visszautasítás kezelésére
    private boolean onlyPrepaid;

    // Megfelelő role-ok (USER, ADMIN, stb.)
    private String role;
}
