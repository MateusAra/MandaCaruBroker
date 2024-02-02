package com.mandacarubroker.service;

import com.mandacarubroker.dto.StockDTO;
import com.mandacarubroker.model.Stock;
import com.mandacarubroker.repository.IStockRepository;
import com.mandacarubroker.validator.StockValidator;
import jakarta.validation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public Optional<Stock> updateStock(String id, Stock updatedStock) {
        return stockRepository.findById(id)
                .map(stock -> {
                    stock.setSymbol(updatedStock.getSymbol());
                    stock.setCompanyName(updatedStock.getCompanyName());
                    double newPrice = stock.changePrice(updatedStock.getPrice(), true);
                    stock.setPrice(newPrice);

                    return stockRepository.save(stock);
                });
    }

    public void deleteStock(String id) {
        stockRepository.deleteById(id);
    }

    public Stock createStock(StockDTO data) {
        Stock newStock = new Stock(data);

        return stockRepository.save(newStock);
    }
}
