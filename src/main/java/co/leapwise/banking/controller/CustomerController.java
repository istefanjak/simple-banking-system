package co.leapwise.banking.controller;

import co.leapwise.banking.model.Customer;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  @GetMapping("/{customerId}")
  public Customer getCustomer(@PathVariable Long customerId) {
    return new Customer(customerId, "name", "Adresa 1", "Mail@mail.com", "091888888", List.of());
  }
}
