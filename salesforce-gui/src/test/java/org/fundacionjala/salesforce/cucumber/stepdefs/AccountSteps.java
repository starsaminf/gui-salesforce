package org.fundacionjala.salesforce.cucumber.stepdefs;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.fundacionjala.core.api.client.RequestManager;
import org.fundacionjala.salesforce.utils.ApiResponseDataExtractor;
import org.fundacionjala.salesforce.constants.AccountConstants;
import org.fundacionjala.salesforce.ui.context.Context;
import org.fundacionjala.salesforce.ui.entities.Account;
import org.fundacionjala.salesforce.ui.skins.ISkinFactory;
import org.fundacionjala.salesforce.ui.skins.SkinManager;
import org.fundacionjala.salesforce.utils.PageTransporter;
import org.testng.asserts.SoftAssert;

import java.net.MalformedURLException;
import java.util.Map;

/**
 * [MR] StepDefinitions class for salesforce Accounts.
 */
public class AccountSteps {

    private Account account;

    private ISkinFactory skin = SkinManager.getInstance().getSkinFactory();

    //Context
    private final Context context;

    private String incorrectAssertionMessage = "The %1$s from %2$s does not match with the %1$s edited previously.";

    /**
     * Adds Dependency injection to share Context information.
     * @param sharedContext
     */
    public AccountSteps(final Context sharedContext) {
        this.context = sharedContext;
    }

    /**
     * Creates an account from UI and update related account entity.
     *
     * @param accountInfo to create a new Account
     */
    @When("I create an Account with the following data")
    public void createAnAccount(final Map accountInfo) {
        //Updating Entity
        account = new Account();
        account.setInformation(accountInfo);
        account.setUpdatedFields(accountInfo.keySet());

        skin.getAccountsPage().goToAccountCreation();
        skin.getAccountCreationPage().fillAccountInformation(account.getUpdatedFields(), account);

        account.setId(skin.getAccountDetailsPage().getAccountId());
        context.setAccount(account);
    }

    /**
     * Makes assertions for Account Details and the recently account entity.
     */
    @Then("Account's new data should be displayed at details")
    public void accountSNewDataShouldBeDisplayedAtDetails() {
        Map<String, String> actualAccountDetails = skin.getAccountDetailsPage().
                getAccountDetails(account.getUpdatedFields());
        Map<String, String> expectedAccountDetails = account.getAccountInfo();
        SoftAssert softAssert = new SoftAssert();
        actualAccountDetails.forEach((field, actualValue) -> {
            softAssert.assertTrue(actualValue.startsWith(expectedAccountDetails.get(field)),
            String.format(incorrectAssertionMessage, field, "Account Details Page"));
        });
        softAssert.assertAll();
    }

    /**
     * Makes assertions with the data from Accounts Table and the recently account entity.
     *
     * @throws MalformedURLException for invalid navigation
     */
    @Then("Account's new data should be displayed in Accounts table")
    public void accountSNewDataShouldBeDisplayedInAccountsTable() throws MalformedURLException {
        PageTransporter.navigateToPage("ACCOUNTS");
        Map<String, String> actualTableData = skin.getAccountsPage().
                getAccountDataFromTable(account.getId());
        Map<String, String> expectedTableData = account.getAccountInfo(actualTableData.keySet());
        SoftAssert softAssert = new SoftAssert();
        actualTableData.forEach((field, actualValue) -> {
            softAssert.assertEquals(actualValue, expectedTableData.get(field),
                    String.format(incorrectAssertionMessage, field, "Account Table"));
        });
        softAssert.assertAll();
    }

    /**
     * Makes assertions with the data gotten via API and the edited fields of the account entity.
     */
    @Then("the gotten data via API about the Account should contain the new data")
    public void theGottenDataAboutTheAccountViaAPIShouldContainTheNewData() {
        Response response = RequestManager.get("/Account/" + account.getId());
        Map<String, String> actualApiResponseData = ApiResponseDataExtractor
                .getAccountDataFromApi(response, account.getUpdatedFields());
        Map<String, String> expectedApiResponseData = account.getAccountInfo();
        SoftAssert softAssert = new SoftAssert();
        actualApiResponseData.forEach((field, actualValue) -> {
            if (!field.equals(AccountConstants.PARENT_ACCOUNT_KEY)) {
                softAssert.assertEquals(actualValue, expectedApiResponseData.get(field),
                        String.format(incorrectAssertionMessage, field, "Account API response"));
            } else {
                softAssert.assertEquals(actualValue, account.getParentAccount().getId(),
                        String.format(incorrectAssertionMessage, field, "Account API response"));
            }
        });
        softAssert.assertAll();
    }
}
