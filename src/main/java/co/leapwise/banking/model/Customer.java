package co.leapwise.banking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long customerId;

  @NotBlank(message = "Name is required.")
  private String name;

  @NotBlank(message = "Address is required.")
  private String address;

  @NotBlank(message = "Email is required.")
  private String email;

  @NotBlank(message = "Phone number is required.")
  private String phoneNumber;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
  private List<Account> accounts;
}
