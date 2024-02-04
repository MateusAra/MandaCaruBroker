package com.mandacarubroker.service;

import com.mandacarubroker.dto.StockDTO;
import com.mandacarubroker.model.Stock;
import com.mandacarubroker.repository.IStockRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {


    private final IStockRepository stockRepository;

    public StockService(IStockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(String id) {
        return stockRepository.findById(id);
    }

    public Stock updateStock(Stock stock, StockDTO updatedStock) {
        stock.setSymbol(updatedStock.symbol());
        stock.setCompanyName(updatedStock.companyName());
        stock.setPrice(updatedStock.price());

        stockRepository.save(stock);

        return stock;
    }

    public boolean deleteStock(String id) {
        Stock stock =  getStockById(id).orElse(null);

        if (stock ==null)
            return false;

        stockRepository.deleteById(id);
        return true;
    }

    public Stock createStock(StockDTO data) {
        Stock newStock = new Stock(data);

        return stockRepository.save(newStock);
    }
}
