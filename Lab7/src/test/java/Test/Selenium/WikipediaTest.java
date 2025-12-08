package Test.Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WikipediaTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "https://pl.wikipedia.org";


    private static final By SEARCH_INPUT = By.id("searchInput");
    private static final By ARTICLE_TITLE = By.id("firstHeading");
    private static final By SUGGESTION_DROPDOWN = By.cssSelector(".suggestions-dropdown");
    private static final By CONTENT_TABLE = By.cssSelector(".toc");
    private static final By IMAGES = By.cssSelector(".infobox img, .thumbimage");
    private static final By MAIN_PAGE_LINK = By.id("n-mainpage-description");

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(BASE_URL);

        List<WebElement> cookieBanner = driver.findElements(By.cssSelector(".cookie-banner-selector")); // <- sprawdź prawidłowy selektor w DevTools
        if (!cookieBanner.isEmpty()) {
            cookieBanner.get(0).click();
        }
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }





    @Test
    @DisplayName("PoleWyszukiwaniaNaStronieGlownej")
    void PoleWyszukiwaniaNaStronieGlownej() {
        WebElement search = driver.findElement(SEARCH_INPUT);

        assertThat(search.isDisplayed()).isTrue();
    }

    @Test
    @DisplayName("istniejacyArtykulJavaISprawdzaniePrzekierowania")
    void istniejacyArtykulJavaISprawdzaniePrzekierowania() {
        driver.findElement(SEARCH_INPUT).sendKeys("Java", Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_TITLE));

        assertThat(driver.getCurrentUrl()).contains("Java");
    }

    @Test
    @DisplayName("WeryfikacjaTytuluStrony")
    void WeryfikacjaTytuluStrony() {
        driver.findElement(SEARCH_INPUT).sendKeys("Java", Keys.ENTER);

        wait.until(ExpectedConditions.titleContains("Java"));

        assertThat(driver.getTitle()).contains("Java");
    }

    @Test
    @DisplayName("SprawdzanieWyszukiwaniaCzyDziala")
    void SprawdzeniePodpowiedziWSzukajce() {

        WebElement searchInput = driver.findElement(SEARCH_INPUT);
        searchInput.sendKeys("ja");

        wait.until(ExpectedConditions.visibilityOfElementLocated(SUGGESTION_DROPDOWN));

        List<WebElement> suggestions = driver.findElements(
                By.cssSelector(".suggestions-dropdown a")
        );


        assertThat(suggestions).isNotEmpty();
    }






    @Test
    @DisplayName("LinkIWeryfikacjaZmianyURL")
    void LinkIWeryfikacjaZmianyURL() {
        driver.findElement(SEARCH_INPUT).sendKeys("Java", Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_TITLE));

        driver.findElement(MAIN_PAGE_LINK).click();

        wait.until(ExpectedConditions.titleContains("Wikipedia"));

        assertThat(driver.getTitle()).contains("Wikipedia");
    }

    @Test
    @DisplayName("SprawdzenieDzialaniaLinkuStronaGlownawmenubocznym")
    void SprawdzenieLinkuStronaGlowna() {

        // 1. Wyszukaj coś, żeby nie być na stronie głównej
        driver.findElement(SEARCH_INPUT).sendKeys("Java", Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_TITLE));

        // 2. Kliknij link "Strona główna" w menu bocznym
        driver.findElement(By.id("n-mainpage-description")).click();

        // 3. Poczekaj aż załaduje się strona główna
        wait.until(ExpectedConditions.titleContains("Wikipedia"));

        // 4. Sprawdź, że faktycznie jesteś na stronie głównej
        assertThat(driver.getCurrentUrl()).contains("wikipedia.org");
    }

    @Test
    @DisplayName("WeryfikacjaObecnosciSekcjiSpisTreściWDłuższymArtykule")
    void WeryfikacjaSpisuTresci() {

        driver.findElement(SEARCH_INPUT).sendKeys("Java", Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_TITLE));

        WebElement toc = wait.until(
                ExpectedConditions.visibilityOfElementLocated(CONTENT_TABLE)
        );

        assertThat(toc.isDisplayed()).isTrue();
    }





    @Test
    @DisplayName("SprawdzanieZeNaglowekArtykuluOdpowiadaWyszukiwanejFrazie")
    void SprawdzanieZeNaglowekArtykuluOdpowiadaWyszukiwanejFrazie() {
        driver.findElement(SEARCH_INPUT).sendKeys("Java", Keys.ENTER);

        WebElement toc = wait.until(ExpectedConditions.visibilityOfElementLocated(CONTENT_TABLE));

        assertThat(toc.isDisplayed()).isTrue();
    }

    @Test
    @DisplayName("PrzynajmniejJedenObrazek")
    void shouldContainAtLeastOneImageInArticle() {
        driver.findElement(SEARCH_INPUT).sendKeys("Java", Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(IMAGES));

        assertThat(driver.findElements(IMAGES)).isNotEmpty();
    }

    @Test
    @DisplayName("Sprawdzenie, że artykuł zawiera sekcję 'Przypisy' lub 'Bibliografia'")
    void shouldContainReferencesOrBibliography() {

        driver.findElement(SEARCH_INPUT).sendKeys("Java", Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_TITLE));

        List<WebElement> references = driver.findElements(By.id("Przypisy"));

        List<WebElement> bibliography = driver.findElements(By.id("Bibliografia"));

        assertThat(references.size() + bibliography.size()).isGreaterThan(0);
    }


}
