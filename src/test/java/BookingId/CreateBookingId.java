package BookingId;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.FileWriter;
import java.io.IOException;
import org.testng.annotations.DataProvider;
import java.io.BufferedReader;
import java.io.FileReader;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;



public class CreateBookingId {
    private Faker faker;
    @BeforeTest
    public void setUp() {
        faker = new Faker();
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test(priority = 0)
    public void createBookingIdFromFile() {

        // Generate random firstname and lastname
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();


        String requestBody = /* {\n" +
                "  \"name\": \"" + firstName + " " + lastName + "\",*/
                /* "{\n" +
                "  \"firstname\" : \"John\",\n" +
                "  \"lastname\" : \"Doe\",\n" + */
                "{\n" +
                        "  \"firstname\": \"" + firstname + "\",\n" +
                        "  \"lastname\": \"" + lastname + "\",\n" +
                        "  \"totalprice\" : 100,\n" +
                        "  \"depositpaid\" : true,\n" +
                        "  \"bookingdates\" : {\n" +
                        "    \"checkin\" : \"2023-01-01\",\n" +
                        "    \"checkout\" : \"2023-01-05\"\n" +
                        "  },\n" +
                        "  \"additionalneeds\" : \"Breakfast\"\n" +
                        "}";

       // System.out.println("RequestBody :" + requestBody);

        // Sending Post request
        Response response =
                    given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract().response();


        int bookingId = response.jsonPath().getInt("bookingid");

        // Storing the booking ID in a text file
        try (FileWriter writer = new FileWriter("bookingId.txt")) {
            writer.write(String.valueOf(bookingId));
                } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
