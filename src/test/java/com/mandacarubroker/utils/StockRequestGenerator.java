package com.mandacarubroker.utils;

import com.mandacarubroker.dto.StockDTO;

import java.util.UUID;

public class StockRequestGenerator {
    public static StockDTO RequestCreateStock(){
        String companyName = String.format("Stock.Integration.Test-%s", UUID.randomUUID());
        double price = 12;
        String symbol = "Bs2";

        return new StockDTO(symbol, companyName, price);
    }

    public static StockDTO RequestUpdateStock(){
        String companyName = String.format("Stock.Integration.Test-%s", UUID.randomUUID());
        double price = 10;
        String symbol = "Cc2";

        return new StockDTO(symbol, companyName, price);
    }
}
