package com.mandacarubroker.service;

import com.mandacarubroker.dto.StockDTO;
import com.mandacarubroker.model.Stock;
import com.mandacarubroker.repository.IStockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class StockServiceTest {

    @Mock
    private IStockRepository stockRepository;

    @Autowired
    @InjectMocks
    private StockService stockService;

    private StockDTO stockDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        stockDTO = new StockDTO("Ba2", "UnitTests", 1);
    }

    @AfterEach
    void tearDown() {
        stockRepository.deleteAll();
    }

    @Test
    void getAllStocksSuccess() {
        // Arrange
        Stock newStock = new Stock(stockDTO);
        List<Stock> stocks = new ArrayList<>();
        stocks.add(newStock);
        when(stockService.getAllStocks()).thenReturn(stocks);

        //Act
        List<Stock> stocksReturned =  stockService.getAllStocks();

        //Assert
        verify(stockRepository, times(1)).findAll();
        assertThat(stocksReturned.size(), not(0));
    }


    @Test
    void getStockByIdSuccess() {
        //Arrange
        String stockId = String.valueOf(UUID.randomUUID());
        Stock stock = new Stock(stockId, stockDTO.symbol(), stockDTO.companyName(), stockDTO.price());
        when(stockService.getStockById(stockId)).thenReturn(Optional.of(stock));

        //Act
        Optional<Stock> stockReturned = stockService.getStockById(stockId);

        //Assert
        verify(stockRepository, times(1)).findById(stockId);
        assertTrue(stockReturned.isPresent());
    }

    @Test
    void updateStockSuccess() {
        //Arrange
        String stockId = String.valueOf(UUID.randomUUID());
        Stock stock = new Stock(stockId, "As2", "UnitTestsUpdate", 2);
        when(stockService.updateStock(stock, stockDTO)).thenReturn(stock);

        //Act
        Stock stockUpdated = stockService.updateStock(stock, stockDTO);

        //Assert
        verify(stockRepository, times(1)).save(stock);
        assertThat(stockUpdated, is(stock));
    }

    @Test
    void deleteStockSuccess() {
        //Arrange
        String stockId = String.valueOf(UUID.randomUUID());
        Stock stock = new Stock(stockId, "As2", "UnitTestsUpdate", 2);
        when(stockService.getStockById(stockId)).thenReturn(Optional.of(stock));
        doNothing().when(stockRepository).deleteById(stockId);

        //Act
        boolean deleted = stockService.deleteStock(stockId);

        //Assert
        verify(stockRepository, times(1)).deleteById(stockId);
        assertTrue(deleted);
    }

    @Test
    void createStockSuccess() {
        //Arrange
        Stock stock = new Stock(stockDTO);
        when(stockService.createStock(stockDTO)).thenReturn(stock);

        //Act
        Stock stockCreated = stockService.createStock(stockDTO);

        //Assert
        verify(stockRepository, times(1)).save(stock);
        assertThat(stock.getCompanyName(), is(stockCreated.getCompanyName()));
        assertThat(stock.getSymbol(), is(stockCreated.getSymbol()));
        assertThat(stock.getPrice(), is(stockCreated.getPrice()));
    }
}