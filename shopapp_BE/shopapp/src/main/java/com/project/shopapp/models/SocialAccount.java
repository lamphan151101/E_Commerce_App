package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(name = "social_accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "provider", length = 20, nullable = false)
  private String provider;

  @Column(name = "provider_id", length = 50)
  private String providerId;

  @Column(name = "name", length = 150)
  private String name;

  @Column(name = "email", length = 150)
  private String email;
}
