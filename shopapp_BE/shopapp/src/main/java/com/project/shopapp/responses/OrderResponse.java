package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse extends BaseResponse {
  private Long id;

  @JoinColumn(name = "user_id")
  private Long userId;

  @JsonProperty("fullname")
  private String fullName;

  @JsonProperty("phone_number")
  private String phoneNumber;

  private String address;

  private String note;

  @JsonProperty("order_date")
  private LocalDateTime orderDate;

  private String status;

  @JsonProperty("total_money")
  private Float totalMoney;

  @JsonProperty("shipping_method")
  private String shippingMethod;

  @JsonProperty("shipping_address")
  private String shoppingAddress;

  @JsonProperty("shipping_date")
  private LocalDate shippingDate;

  @JsonProperty("tracking_number")
  private String trackingNumber;

  @JsonProperty("payment_method")
  private String paymentMethod;

  @JsonProperty("active")
  private Boolean active;
}
