package co.leapwise.banking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Currency {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long currencyId;

  @NotBlank(message = "Name is required.")
  @Column(unique = true)
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "currency", cascade = CascadeType.ALL)
  private List<Transaction> transactions;
}
