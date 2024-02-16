package com.mandacarubroker.responses;

import com.mandacarubroker.model.Stock;

import java.util.List;

public class GetAllStocksResponse {
    public int count;
    public String message;
    public List<Stock> stockList;

    public GetAllStocksResponse(int count, String message, List<Stock> stockList){
        this.count = count;
        this.message = message;
        this.stockList = stockList;
    }
    public GetAllStocksResponse(){ }
    // Getters e Setters
    public void setMessage(String message) {
        this.message = message;
    }

}
