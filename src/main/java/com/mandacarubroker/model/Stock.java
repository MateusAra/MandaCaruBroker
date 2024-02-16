package com.mandacarubroker.model;

import com.mandacarubroker.dto.StockDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name ="stock")
@Entity(name="stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Stock {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String symbol;
    private String companyName;
    private double price;

    public Stock(StockDTO stockDTO){
        this.symbol = stockDTO.symbol();
        this.companyName = stockDTO.companyName();
        this.price = stockDTO.price();
    }
}