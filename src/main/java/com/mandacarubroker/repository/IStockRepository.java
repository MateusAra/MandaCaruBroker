package com.mandacarubroker.repository;


import com.mandacarubroker.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IStockRepository extends JpaRepository<Stock,String> {
}
