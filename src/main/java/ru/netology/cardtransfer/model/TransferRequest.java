package ru.netology.cardtransfer.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@DifferentCards
public class TransferRequest {
    @NotNull
    @Pattern(regexp = "\\d{16}")
    public String cardFromNumber;

    @NotNull
    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}")
    public String cardFromValidTill;

    @NotNull
    @Pattern(regexp = "^(?!000)\\d{3}$", message = "CVV must be a 3-digit number and not 000")
    public String cardFromCVV;

    @NotNull
    @Pattern(regexp = "\\d{16}")
    public String cardToNumber;

    @NotNull
    public Amount amount;

    public static class Amount {
        @NotNull
        @Min(1)
        public Integer value;
        @NotNull
        @NotEmpty
        public String currency;
    }
}