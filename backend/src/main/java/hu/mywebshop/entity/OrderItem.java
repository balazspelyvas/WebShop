package hu.mywebshop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Melyik termék
    @ManyToOne
    private Product product;

    // Darabszám
    private int quantity;

    // Mekkora egységár volt a rendelés pillanatában
    private double unitPrice;
}
