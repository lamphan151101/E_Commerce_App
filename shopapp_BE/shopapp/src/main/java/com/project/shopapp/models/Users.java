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
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "fullname", length = 300)
  private String fullName;

  @Column(name = "phone_number", length = 10, nullable = false)
  private String phoneNUmber;

  @Column(name = "address", length = 200)
  private String address;

  @Column(name = "password", length = 200, nullable = false)
  private String password;

  private boolean active;

  @Column(name = "date_of_birth")
  private Date dateOfBirth;

  @Column(name = "facebook_account_id")
  private int facebookAccountId;

  @Column(name = "google_account_id")
  private int googleAccountId;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private Roles role;
}
