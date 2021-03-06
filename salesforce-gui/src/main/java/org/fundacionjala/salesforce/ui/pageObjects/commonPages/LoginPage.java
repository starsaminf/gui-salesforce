package org.fundacionjala.salesforce.ui.pageObjects.commonPages;

import org.fundacionjala.core.selenium.interaction.GuiInteractioner;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * [MR] Class that represent Salesforce Login page.
 */
public class LoginPage extends BasePage {
    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "Login")
    private WebElement loginBtn;

    private void setUsernameField(final String username) {
        GuiInteractioner.setInputText(usernameField, username);
    }

    private void setPasswordField(final String username) {
        GuiInteractioner.setInputText(passwordField, username);
    }

    private void clickLoginBtn() {
        GuiInteractioner.clickWebElement(loginBtn);
    }

    /**
     * Allows the browser to log in to Salesforce UI.
     *
     * @param username to set
     * @param password to set
     */
    public void login(final String username, final String password) {
        setUsernameField(username);
        setPasswordField(password);
        clickLoginBtn();
    }

    @Override
    protected final void waitLoadPage() {
        getDriverWait().until(ExpectedConditions.visibilityOf(loginBtn));
    }
}
