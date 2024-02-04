package com.project.shopapp.controllers;


import com.project.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
  @PostMapping
  public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO newOrderDetail){
    return ResponseEntity.ok("createOrderDetail successfully");
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id){
    return ResponseEntity.ok("getOrderDetail successfully");
  }

  @GetMapping("/order/{orderId}")
  public ResponseEntity<?> getOrderDetailfromorder(@Valid @PathVariable("orderId") Long orderId){
    return ResponseEntity.ok("getOrderDetail successfully from orderId");
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id, @RequestBody OrderDetailDTO newOrderDetailData){
    return ResponseEntity.ok("UpdateOrderDetail successfully from orderId" + newOrderDetailData);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id){
    return ResponseEntity.noContent().build();
  }
}
