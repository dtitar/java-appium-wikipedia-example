import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;

import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;

public class RotationTest extends BaseTest {

    private static final By SEARCH_BOX = xpath("//*[contains(@text,'Search Wikipedia')]");
    private static final By SEARCH_BOX_INPUT = By.id("org.wikipedia:id/search_src_text");

    @Test
    public void testReturnCorrectRotation() {
        String searchText = "Java";

        driver.rotate(ScreenOrientation.LANDSCAPE);
        actions.waitAndClick(
                SEARCH_BOX,
                "Cannot find search block",
                5);
        actions.waitAndSendKeys(
                SEARCH_BOX_INPUT,
                searchText,
                "Cannot find Search Input",
                5);
    }

    //Дополнительный тест для проверки восстановления поворота экрана при последовательном запуске
   @Test
    public void testArticleHasTitle() {
        String searchText = "Java";
        String articleName = "Java (programming language)";
        By searchResult = By.xpath(format("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='%s']", articleName));

        actions.waitAndClick(
                SEARCH_BOX,
                "Cannot find search block",
                5);
        actions.waitAndSendKeys(
                SEARCH_BOX_INPUT,
                searchText,
                "Cannot find Search Input",
                5);
        actions.waitAndClick(
                searchResult,
                "Cannot find article with '" + articleName + "' in search",
                15);
        assertElementPresent(By.id("org.wikipedia:id/view_page_title_text"), "Title is not present on page");
    }

    @After
    public void returnDefaultOrientation() {
        if (driver.getOrientation() != ScreenOrientation.PORTRAIT) {
            driver.rotate(ScreenOrientation.PORTRAIT);
        }
    }

    private void assertElementPresent(By by, String errorMessage) {
        if (actions.getAmountOfElements(by) == 0) {
            String defaultMessage = format("An element '%s' supposed to be present.", by.toString());
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }
}
