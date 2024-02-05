package com.mandacarubroker.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mandacarubroker.dto.StockDTO;
import com.mandacarubroker.messages.StockMessages;
import com.mandacarubroker.responses.DeleteStockResponse;
import com.mandacarubroker.responses.GetAllStocksResponse;
import com.mandacarubroker.model.Stock;
import com.mandacarubroker.responses.GetStockByIdResponse;
import com.mandacarubroker.responses.UpdateStockResponse;
import com.mandacarubroker.service.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;
    StockMessages messages = new StockMessages();
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity getAllStocks() throws JsonProcessingException {
        List<Stock> stocks = stockService.getAllStocks();

        if(!stocks.isEmpty())
        {
            GetAllStocksResponse response = new GetAllStocksResponse(stocks.size(), messages.success, stocks);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        }

        GetAllStocksResponse response = new GetAllStocksResponse();
        response.setMessage(messages.noHaveStocks);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity getStockById(@PathVariable String id) {
        GetStockByIdResponse response = new GetStockByIdResponse();
        Stock stock = stockService.getStockById(id).orElse(null);

        if (stock != null){
            response.setMessage(messages.success);
            response.setStock(stock);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        }
        response.setMessage(messages.stockNoExists + id);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody @Valid StockDTO data) {
        Stock createdStock = stockService.createStock(data);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdStock);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStock(@PathVariable String id, @RequestBody @Valid StockDTO updatedStock) {
        UpdateStockResponse response = new UpdateStockResponse();
        Stock stock = stockService.getStockById(id).orElse(null);

        if (stock != null){
            response.setMessage(messages.success);
            response.setStock(stockService.updateStock(stock, updatedStock));
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(response);
        }
        response.setMessage(messages.stockNoExists + id);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStock(@PathVariable String id) {

        boolean deletedStock = stockService.deleteStock(id);
        DeleteStockResponse response = new DeleteStockResponse(deletedStock ? messages.stockDeleted: messages.stockNoExists + id);

        if (deletedStock){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
