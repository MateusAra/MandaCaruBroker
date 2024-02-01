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
        this.price = changePrice(stockDTO.price(), true);
    }

    public double changePrice(double amount, boolean increase) {
        if (increase) {
            if (amount < this.price) {
                return increasePrice(amount);
            } else {
                return decreasePrice(amount);
            }
        } else {
            if (amount > this.price) {
                return increasePrice(amount);
            } else {
                return this.decreasePrice(amount);
            }
        }
    }

    public double increasePrice(double amount) {
        return this.price + amount;
    }

    public double decreasePrice(double amount) {
        return this.price - amount;
    }

}