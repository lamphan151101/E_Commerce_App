package com.project.shopapp.repositories;

import com.project.shopapp.models.order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<order, Long> {
  List<order> findByUserId(Long userId);
}
