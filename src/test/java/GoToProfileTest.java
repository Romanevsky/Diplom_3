import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.API.*;
import org.example.Pages.LoginPage;
import org.example.Pages.MainPage;
import org.example.Pages.ProfilePage;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;

public class GoToProfileTest {

    private WebDriver driver;

    @Before
    public void setup() {
        RestAssured.baseURI = BaseURL.BASE_URI;
        driver = WebDriverConfig.setDriver();
        driver.manage().timeouts().implicitlyWait(WebDriverConfig.WAIT_SEC_TIMEOUT, TimeUnit.SECONDS);
        driver.navigate().to(APIconfig.MAIN_PAGE_URL);
    }

    @Test
    @DisplayName("Go to the profile as an authorized user")
    public void goToProfileAuthUserGetProfile() {
        User user = Generator.generateUser();
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        UserOperations.createUser(user);
        mainPage.clickProfileButton();
        loginPage.loginUser(user);

        Assert.assertTrue(profilePage.btnProfileTabIsEnabled());
        UserOperations.deleteUser(UserOperations.getAccessToken(user));
    }

    @Test
    @DisplayName("Go to the profile as an unauthorized user")
    public void goToProfileUnauthUserGetLogin() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        mainPage.clickProfileButton();

        MatcherAssert.assertThat(loginPage.getLoginTextFromHeader(), equalTo("Вход"));
    }

    @After
    public void teardown() {
        driver.quit();
    }
}