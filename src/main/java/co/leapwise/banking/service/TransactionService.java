package co.leapwise.banking.service;

import co.leapwise.banking.manager.AccountManager;
import co.leapwise.banking.manager.CurrencyManager;
import co.leapwise.banking.manager.MTransactionManager;
import co.leapwise.banking.model.Transaction;
import co.leapwise.banking.request.TransactionRequest;
import co.leapwise.banking.request.params.TransactionParams;
import co.leapwise.banking.response.PageableResponse;
import co.leapwise.banking.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
  private final MTransactionManager transactionManager;
  private final AccountManager accountManager;
  private final CurrencyManager currencyManager;
  private final ModelMapper modelMapper;

  public Long insertTransaction(TransactionRequest request) {
    var sender = accountManager.getAccount(request.getSenderId());
    var receiver = accountManager.getAccount(request.getReceiverId());
    var currency = currencyManager.getCurrency(request.getCurrencyId());

    var transaction =
        Transaction.builder()
            .sender(sender)
            .receiver(receiver)
            .amount(request.getAmount())
            .currency(currency)
            .message(request.getMessage())
            .build();

    sender.getSenderTransactions().add(transaction);
    receiver.getReceiverTransactions().add(transaction);
    currency.getTransactions().add(transaction);

    transactionManager.insertTransaction(transaction);

    return transaction.getTransactionId();
  }

  public PageableResponse<TransactionResponse> getTransactionsByCustomer(
      Long customerId, TransactionParams params) {
    var transactions = transactionManager.getByCustomerId(customerId, params);

    return PageableResponse.<TransactionResponse>builder()
        .total(transactions.getTotalElements())
        .data(
            transactions.stream().map(t -> modelMapper.map(t, TransactionResponse.class)).toList())
        .build();
  }
}
