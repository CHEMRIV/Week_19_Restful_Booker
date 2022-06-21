package com.herokuapp.booker.cucumber.steps;

import com.herokuapp.booker.bookininfo.AuthSteps;
import com.herokuapp.booker.bookininfo.BookingSteps;
import com.herokuapp.booker.utils.TestUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;

import static org.hamcrest.CoreMatchers.equalTo;

public class MyStepdefs {
    static String firstName = "PrimUser" + TestUtils.getRandomValue();
    static String lastName = "PrimeUser" + TestUtils.getRandomValue();
    static int totalPrice = 500;
    static boolean depositPaid = true;
    static String checkIn = "2022-01-07";
    static String checkOut = "2022-01-20";
    static String additionalNeeds = "Breakfast";
    static String username = "admin";
    static String password = "password123";
    static int bookingId;
    static String token;
    static ValidatableResponse response;

    @Steps
    BookingSteps bookingSteps;
    @Steps
    AuthSteps authSteps;

    @When("^User sends a GET request to list endpoint$")
    public void userSendsAGETRequestToListEndpoint() {
        response = bookingSteps.getAllBookingInfo();
    }

    @Then("^User should get back a valid status code 200$")
    public void userShouldGetBackAValidStatusCode() {
        response.statusCode(200);
    }

    @When("^User sends a POST request to create booker endpoint$")
    public void userSendsAPOSTRequestToCreateBookerEndpoint() {
         response = bookingSteps.createBooking(firstName, lastName, totalPrice, depositPaid, checkIn,
                checkOut, additionalNeeds);
        response.statusCode(200).log().ifValidationFails();
        bookingId = response.extract().path("bookingid");
    }

    @Then("^User must get back a validable status code 201$")
    public void userMustGetBackAValidableStatusCode() {
        response.statusCode(200);
    }

    @When("^User sends a GET request to get new booker endpoint$")
    public void userSendsAGETRequestToGetNewBookerEndpoint() {
        ValidatableResponse response = bookingSteps.getBookingWithBookingId(bookingId);
        response.statusCode(200).log().ifValidationFails();
        response.body("firstname", equalTo(firstName), "lastname", equalTo(lastName),
                "totalprice", equalTo(totalPrice), "depositpaid", equalTo(depositPaid),
                "bookingdates.checkin", equalTo(checkIn), "bookingdates.checkout", equalTo(checkOut),
                "additionalneeds", equalTo(additionalNeeds));
    }

    @Then("^User successfully added id in product list and status code is 200$")
    public void userSuccessfullyAddedIdInProductListAndStatusCodeIs() {
        response.statusCode(200);
    }

    @When("^User sends a PATCH request to update booker endpoint$")
    public void userSendsAPATCHRequestToUpdateBookerEndpoint() {
        firstName = firstName + "_partial";
        lastName = lastName + "_partial";
        token = authSteps.getAuthToken(username, password);
        ValidatableResponse response = bookingSteps.updatePartialBooking(bookingId, firstName, lastName, null,
                null, null, null, null, token);
        response.log().ifValidationFails().statusCode(200);
        response.body("firstname", equalTo(firstName), "lastname", equalTo(lastName),
                "additionalneeds", equalTo(additionalNeeds));
    }

    @Then("^User should update single data successfully with status code 200$")
    public void userShouldUpdateSingleDataSuccessfullyWithStatusCode() {
        response.statusCode(200);
    }

    @When("^User sends a Delete request to delete endpoint$")
    public void userSendsADeleteRequestToDeleteEndpoint() {
        ValidatableResponse response = bookingSteps.deleteBookingWithBookingId(bookingId, token);

    }

    @Then("^User should delete category id from application successfully with status code 200$")
    public void userShouldDeleteCategoryIdFromApplicationSuccessfullyWithStatusCode() {
        response.statusCode(200).log().ifValidationFails();

    }

    @And("^User check delete should not more exist in category list$")
    public void userCheckDeleteShouldNotMoreExistInCategoryList() {
        bookingSteps.getBookingWithBookingId(bookingId);
    }

    @And("^User check status code should be 200$")
    public void userCheckStatusCodeShouldBe() {
        response.statusCode(200);
    }
}
