package com.project.shopapp.models;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private Users user;
  @Column(name = "fullname", length = 100)
  private String fullName;

  @Column(name = "email", length = 100)
  private String email;

  @Column(name = "phone_number", length = 10, nullable = false)
  private String phoneNUmber;

  @Column(name = "address", length = 200)
  private String address;

  @Column(name = "note", length = 200)
  private String note;

  @Column(name = "status")
  private String status;

  @Column(name = "total_money")
  private Integer totalMoney;

  @Column(name = "shipping_method")
  private String shippingMethod;

  @Column(name = "shipping_address")
  private String shoppingAddress;

  @Column(name = "shipping_date")
  private Date shippingDate;

  @Column(name = "tracking_number")
  private String trackingNumber;

  @Column(name = "payment_method")
  private String paymentMethod;

  @Column(name = "active")
  private Boolean active;

}
