package com.project.shopapp.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
  @JsonProperty("order_id")
  @Min(value = 1, message = "Order's ID must be > 0")
  private Long orderId;

  @JsonProperty("product_id")
  @Min(value = 1, message = "product_id must be > 0")
  private Long productId;

  @Min(value = 0, message = "Order's ID must be >= 0")
  private Long price;

  @JsonProperty("number_of_products")
  @Min(value = 1, message = "number_of_products must be > 0")
  private int numberOfProducts;

  @JsonProperty("total_money")
  @Min(value = 1, message = "total_money must be > 0")
  private Float totalMoney;

  private String color;
}
