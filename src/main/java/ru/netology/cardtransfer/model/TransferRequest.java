package ru.netology.cardtransfer.model;

public class TransferRequest {
    public String cardFromNumber;
    public String cardFromValidTill;
    public String cardFromCVV;
    public String cardToNumber;
    public Amount amount;
    public static class Amount {
        public Integer value;
        public String currency;
    }
}