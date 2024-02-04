package com.mandacarubroker.responses;

import com.mandacarubroker.model.Stock;

public class GetStockByIdResponse {
    public String message;
    public Stock stock;

    public GetStockByIdResponse(){ }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setStock(Stock stock){
        this.stock = stock;
    }
}
