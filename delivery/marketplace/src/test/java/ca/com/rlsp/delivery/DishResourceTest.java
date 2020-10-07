package ca.com.rlsp.delivery;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DishResourceTest {

    @Test
    public void testHelloEndpoint() {
        String body = given()
                .when().get("/dishes")
                .then()
                .statusCode(200).extract().asString();
        System.out.println(body);
    }

}
