package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "token", length = 255)
  private String token;

  @Column(name = "token_type", length = 50)
  private String tokenType;

  @Column(name = "expiration_date")
  private LocalDateTime expirationDate;

  private boolean revoked;
  private boolean expired;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private Users user;
}