package BookingId;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class GetBookingId {
    @DataProvider(name = "bookingIdProvider")
    public Object[][] provideBookingId() throws IOException {
        // Read booking ID from the file
        try (BufferedReader reader = new BufferedReader(new FileReader("bookingId.txt"))) {
            String bookingId = reader.readLine();
            return new Object[][]{{bookingId}};
        }
    }
    @Test(dataProvider = "bookingIdProvider")
    public void testGetBooking(String bookingId) {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";


        Response response = given()
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();


        String responseBody = response.getBody().asString();
        //assertTrue(responseBody.contains("f"), "Response should contain the first name 'John'");
        //assertTrue(responseBody.contains("Doe"), "Response should contain the last name 'Doe'");
        assertTrue(responseBody.contains("100"), "Response should contain the total price '100'");
    }
}


