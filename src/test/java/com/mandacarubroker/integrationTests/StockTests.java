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
    @BeforeClass
    public static void SetUp(){
        baseURI = "http://localhost:8080";
    }
    @Test
    public void givenGetAllStocks_WhenRequestIsValid_ThenResponseIsOkAndListReturnedIsValid(){
        given()
                .when()
                    .get("/stocks")
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("message", is(StockMessages.success))
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
                    .body("message", is(StockMessages.success))
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
                    .body("message", is(StockMessages.stockNoExists + stockId))
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
                    .body("message", is(StockMessages.stockNoExists + stockId));
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
                    .body("message", is(StockMessages.stockDeleted));
    }

    @Test
    public void givenUpdateStock_WhenStockIdIsValid_ThenResponseIsOkAndReturnedIsSuccessfully(){
        StockDTO stock = StockRequestGenerator.RequestCreateStock();
        StockDTO updateStock = StockRequestGenerator.RequestUpdateStock();

        String stockId = given().contentType(ContentType.JSON).body(stock).when().post("/stocks")
                .then().statusCode(HttpStatus.SC_CREATED).extract().path("id");

        given()
                    .contentType(ContentType.JSON)
                    .body(updateStock)
                .when()
                    .put("/stocks/" + stockId)
                .then()
                    .statusCode(HttpStatus.SC_ACCEPTED)
                    .body("message", is(StockMessages.success))
                    .body("stock.id", is(stockId))
                    .body("stock.symbol", is(updateStock.symbol()))
                    .body("stock.companyName", is(updateStock.companyName()))
                    .body("stock.price", notNullValue());
    }

    @Test
    public void givenUpdateStock_WhenStockIdIsInvalid_ThenResponseIsNotFoundAndReturnedIsError(){
        UUID stockId = UUID.randomUUID();
        StockDTO updateStock = StockRequestGenerator.RequestUpdateStock();

        given()
                    .contentType(ContentType.JSON)
                    .body(updateStock)
                .when()
                    .put("/stocks/" + stockId)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .body("message", is(StockMessages.stockNoExists + stockId))
                    .body("stock", nullValue());
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

    @Test
    public void givenCreateStock_WhenCompanyNameAndPriceIsInvalid_ThenResponseIsBadRequestAndReturnedError(){
        StockDTO stock = new StockDTO("Bb2", null, 0.0);

        given()
                    .contentType(ContentType.JSON)
                    .body(stock)
                .when()
                    .post("/stocks")
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("price", is(StockMessages.priceInvalid))
                    .body("companyName", is(StockMessages.companyNameInvalid));

    }

    @Test
    public void givenCreateStock_WhenPriceAndSymbolIsInvalid_ThenResponseIsBadRequestAndReturnedError(){
        StockDTO stock = new StockDTO("null", "mtsTestes", 0.0);

        given()
                    .contentType(ContentType.JSON)
                    .body(stock)
                .when()
                    .post("/stocks")
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("price", is(StockMessages.priceInvalid))
                    .body("symbol", is(StockMessages.symbolInvalid));

    }

    @Test
    public void givenCreateStock_WhenSymbolAndCompanyNameIsInvalid_ThenResponseIsBadRequestAndReturnedError(){
        StockDTO stock = new StockDTO("null", null, 1.0);

        given()
                    .contentType(ContentType.JSON)
                    .body(stock)
                .when()
                    .post("/stocks")
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("companyName", is(StockMessages.companyNameInvalid))
                    .body("symbol", is(StockMessages.symbolInvalid));

    }

    @Test
    public void givenCreateStock_WhenSymbolAndCompanyNameAndPriceIsInvalid_ThenResponseIsBadRequestAndReturnedError(){
        StockDTO stock = new StockDTO("null", null, 0.0);

        given()
                    .contentType(ContentType.JSON)
                    .body(stock)
                .when()
                    .post("/stocks")
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("companyName", is(StockMessages.companyNameInvalid))
                    .body("symbol", is(StockMessages.symbolInvalid))
                    .body("price", is(StockMessages.priceInvalid));

    }
}
