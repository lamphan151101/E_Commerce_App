package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class userLoginDTO {

  @JsonProperty("phone_number")
  @NotBlank(message = "Phone number is required")
  private String phoneNumber;

  @NotBlank(message = "Password can not be blank")
  private String password;
}
