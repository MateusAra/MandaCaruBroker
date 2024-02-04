package com.mandacarubroker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record StockDTO(
        @Pattern(regexp = "[A-Za-z]{2}[0-9]{1}", message = "o Simbolo deve ter 2 letras seguidas de 1 número.(Ex.:Bs3).")
        String symbol,
        @NotBlank(message = "O nome da empresa não pode ficar em branco.")
        String companyName,
        @DecimalMin(value = "0.1" , message = "o preço não pode ser zero ou menor que zero.")
        double price
) {
}
