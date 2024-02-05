package com.mandacarubroker.integrationTests;

import com.mandacarubroker.dto.StockDTO;
import com.mandacarubroker.messages.StockMessages;
import com.mandacarubroker.utils.StockRequestGenerator;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class StockTests {
    public static StockMessages messages;
    @BeforeClass
    public static void SetUp(){
        baseURI = "http://localhost:8080";
        messages = new StockMessages();
    }
    @Test
    public void givenGetAllStocks_WhenRequestIsValid_ThenResponseIsOkAndListReturnedIsValid(){
        given()
                .when()
                    .get("/stocks")
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("message", is(messages.success))
                    .body("stockList", notNullValue());
    }

    @Test
    public void givenGetStockById_WhenStockIdIsValid_ThenResponseIsOkAndReturnedIsValid(){
        String stockId = "24d283f2-7133-4c43-b50d-882b0d7b4b3f";

        given()
                .when()
                    .get("/stocks/" + stockId)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("message", is(messages.success))
                    .body("stock.id", is(stockId));
    }

    @Test
    public void givenGetStockById_WhenStockIdIsInvalid_ThenResponseIsNotFoundAndReturnedError(){
        UUID stockId = UUID.randomUUID();

        given()
                .when()
                    .get("/stocks/" + stockId)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .body("message", is(messages.stockNoExists + stockId))
                    .body("stock", nullValue());
    }

    @Test
    public void givenDeleteStock_WhenStockIdIsInvalid_ThenResponseIsNotFoundAndReturnedError(){
        UUID stockId = UUID.randomUUID();

        given()
                .when()
                    .delete("/stocks/" + stockId)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .body("message", is(messages.stockNoExists + stockId));
    }

    @Test
    public void givenDeleteStock_WhenStockIdIsValid_ThenResponseIsOkAndReturnedIsSuccessfully(){
        StockDTO stock = StockRequestGenerator.RequestCreateStock();

        String stockId = given().contentType(ContentType.JSON).body(stock).when().post("/stocks")
                            .then().statusCode(HttpStatus.SC_CREATED).extract().path("id");

        given()
                .when()
                    .delete("/stocks/" + stockId)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("message", is(messages.stockDeleted));
    }

    @Test
    public void givenCreateStock_WhenRequestBodyIsValid_ThenResponseIsOkAndReturnedStockCreated(){
        StockDTO stock = StockRequestGenerator.RequestCreateStock();

        given()
                    .contentType(ContentType.JSON)
                    .body(stock)
                .when()
                    .post("/stocks")
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .body("id", notNullValue())
                    .body("symbol", is(stock.symbol()))
                    .body("companyName", is(stock.companyName()));

    }
}
