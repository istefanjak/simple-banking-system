package co.leapwise.banking.controller;

import co.leapwise.banking.common.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  @GetMapping("/{customerId}")
  public String getCustomer(@PathVariable Long customerId) {
    return customerId.toString();
  }
}
