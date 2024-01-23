package co.leapwise.banking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import co.leapwise.banking.TestUtils;
import co.leapwise.banking.manager.AccountManager;
import co.leapwise.banking.manager.CurrencyManager;
import co.leapwise.banking.manager.MTransactionManager;
import co.leapwise.banking.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
  @Mock private MTransactionManager transactionManager;
  @Mock private AccountManager accountManager;
  @Mock private CurrencyManager currencyManager;
  @Mock private CurrencyConverter currencyConverter;
  @Mock private EmailService emailService;
  @Mock private ModelMapper modelMapper;

  @InjectMocks private TransactionService transactionService;

  @Test
  void insertTransaction() {
    // TODO
  }

  @Test
  void getTransactionsByCustomer_multipleTransactions_allOk() {
    var transactionsList = TestUtils.getTransactions(1000L, 2000L);
    var transactionsPage = new PageImpl<>(transactionsList, PageRequest.of(0, 10), 2);

    when(transactionManager.getByCustomerId(any(), any())).thenReturn(transactionsPage);

    var result = transactionService.getTransactionsByCustomer(null, null);

    verify(modelMapper, times(2)).map(any(), any());
    assertEquals(2, result.getTotal());
  }

  @Test
  void getTransactionsByCustomer_noTransactions_allOk() {
    var transactionsList = new ArrayList<Transaction>();
    var transactionsPage = new PageImpl<>(transactionsList, PageRequest.of(0, 10), 0);

    when(transactionManager.getByCustomerId(any(), any())).thenReturn(transactionsPage);

    var result = transactionService.getTransactionsByCustomer(null, null);

    verify(modelMapper, times(0)).map(any(), any());
    assertEquals(0, result.getTotal());
  }
}
