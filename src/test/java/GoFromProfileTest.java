import io.qameta.allure.junit4.DisplayName;
import org.example.API.APIconfig;
import org.example.API.BackToMainDetails;
import org.example.API.WebDriverConfig;
import org.example.Pages.MainPage;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class GoFromProfileTest {

    private WebDriver driver;
    private final String button;

    @Before
    public void setup() {
        driver = WebDriverConfig.setDriver();
        driver.manage().timeouts().implicitlyWait(WebDriverConfig.WAIT_SEC_TIMEOUT, TimeUnit.SECONDS);
        driver.navigate().to(APIconfig.MAIN_PAGE_URL);
    }

    public GoFromProfileTest(String button) {
        this.button = button;
    }


    @Parameterized.Parameters(name = "Go from profile using the {0} button")
    public static Object[] backToMainButtons() {
        return new Object[][]{
                {BackToMainDetails.LOGO_BACK_TO_MAIN_PAGE},
                {BackToMainDetails.CONSTRUCTOR_BACK_TO_MAIN},
        };
    }

    @Test
    @DisplayName("Go from the profile to the main page")
    public void goFromProfileToMain() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickProfileButton();
        mainPage.backToMainPage(button);

        MatcherAssert.assertThat(mainPage.getCreateBurgerTextFromHeader(), equalTo("Соберите бургер"));
    }

    @After
    public void teardown() {
        driver.quit();
    }
}