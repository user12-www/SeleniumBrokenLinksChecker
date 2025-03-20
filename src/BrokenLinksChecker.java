import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BrokenLinksChecker {
    public static void main(String[] args) {
        // Set the path to your ChromeDriver
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        // Initialize ChromeDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode
        WebDriver driver = new ChromeDriver(options);

        String testUrl = "https://example.com";
        driver.get(testUrl);

        // Get all links on the page
        List<WebElement> links = driver.findElements(By.tagName("a"));
        
        System.out.println("Total links found: " + links.size());

        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url != null && !url.isEmpty()) {
                checkBrokenLink(url);
            }
        }

        driver.quit();
    }

    public static void checkBrokenLink(String linkUrl) {
        try {
            URL url = new URL(linkUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("HEAD");
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            
            if (responseCode >= 400) {
                System.out.println("Broken link: " + linkUrl + " | Response Code: " + responseCode);
            } else {
                System.out.println("Valid link: " + linkUrl);
            }
        } catch (IOException e) {
            System.out.println("Error checking link: " + linkUrl + " | " + e.getMessage());
        }
    }
}
