package com.mandacarubroker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record StockDTO(
        @Pattern(regexp = "[A-Za-z]{2}[0-9]{1}", message = "Symbol must be 3 letters followed by 1 number.")
        String symbol,
        @NotBlank(message = "Company name cannot be blank.")
        String companyName,
        @DecimalMin(value = "0.1" ,message = "price cannot be zero or less than zero.")
        double price
) {
}
