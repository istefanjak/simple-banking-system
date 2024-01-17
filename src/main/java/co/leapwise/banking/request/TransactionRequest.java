package co.leapwise.banking.request;

import lombok.Data;

@Data
public class TransactionRequest {
    private Long senderId;
    private Long receiverId;
    private Long amount;
    private Long currencyId;
    private String message;
}
