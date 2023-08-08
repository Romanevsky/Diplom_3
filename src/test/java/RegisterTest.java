import io.qameta.allure.junit4.DisplayName;
import org.example.API.*;
import org.example.Pages.LoginPage;
import org.example.Pages.ProfilePage;
import org.example.Pages.RegisterPage;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;

public class RegisterTest {
    private WebDriver driver;
    User user;

    @Before
    public void setup() {
        user = Generator.generateUser();

        driver = WebDriverConfig.setDriver();
        driver.manage().timeouts().implicitlyWait(WebDriverConfig.WAIT_SEC_TIMEOUT, TimeUnit.SECONDS);
        driver.navigate().to(APIconfig.REGISTER_PAGE_URL);
    }

    @Test
    @DisplayName("Register a new user with a short password")
    public void registerNewUserWithShortPasswordGetError() throws InterruptedException {
        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.setName(user.getName());
        registerPage.setEmail(user.getEmail());
        registerPage.setPassword(Generator.generateWrongUserPassword());
        registerPage.clickRegisterButton();
        Thread.sleep(3000);
        MatcherAssert.assertThat(registerPage.getInvalidPasswordText(), equalTo("Некорректный пароль"));
    }


    @After
    public void teardown() {
        UserOperations.deleteUser(UserOperations.getAccessToken(user));
        driver.quit();
    }
}