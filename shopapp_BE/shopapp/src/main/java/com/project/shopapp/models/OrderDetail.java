package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private order order;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "price", nullable = false)
  private Float price;

  @Column(name = "number_of_products", nullable = false)
  private int numberOfProducts;

  @Column(name = "total_money", nullable = false)
  private int totalMoney;
}
