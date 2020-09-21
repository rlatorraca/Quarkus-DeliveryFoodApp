package ca.com.rlsp.delivery.registration;

import javax.ws.rs.core.Response.Status;

import org.approvaltests.Approvals;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;

import ca.com.rlsp.delivery.registration.model.Restaurant;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(RegistrationTestLifeCycleManager.class)
public class RestaurantResourceTest {

    private String token;

//    @BeforeEach
//    public void gereToken() throws Exception {
//        token = TokenUtils.generateTokenString("/JWTProprietarioClaims.json", null);
//    }

    @Test
    @DataSet("restaurants-scenario-1.yml")
    public void testBuscarRestaurantes() {
        String resultado = given()
                .when().get("/restaurants")
                .then()
                .statusCode(Status.OK.getStatusCode())
                .extract().asString();
        Approvals.verifyJson(resultado);
    }
    
    

    private RequestSpecification given() {
        return RestAssured.given().contentType(ContentType.JSON);
    }

    //Test PUT example

    @Test
    @DataSet("restaurants-scenario-1.yml")
    public void testAlterarRestaurante() {
        Restaurant dto = new Restaurant();
        dto.name = "newName";
        Long parameterValue = 123L;
        String resultado = given()
                .with().pathParam("id", parameterValue)
                .body(dto)
                .when().put("/restaurants/{id}")
                .then()
                .statusCode(Status.NO_CONTENT.getStatusCode())
                .extract().asString();
        Assert.assertEquals("", resultado);
        Restaurant findById = Restaurant.findById(parameterValue);

        //poderia testar todos os outros atribudos
        Assert.assertEquals(dto.name, findById.name);

    }

}