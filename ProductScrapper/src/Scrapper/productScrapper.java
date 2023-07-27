package Scrapper;
//Import Statements
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class productScrapper {
  public void getProductData() throws InterruptedException {   //method initiates the web scraping process
      WebDriver driver = new ChromeDriver();  //creates instance of webdriver

      String url = "https://shopping.google.ca/";  //URL of the website to be scraped

      driver.get(url);  // Navigate to the website

      // List of search terms to be used
      String[] searchTerms = {
      	    "iphone",
      	    "laptop",
      	    "pendrive",
      	    "hard disk",
      	    "SSD",
      	    "smartphone",
//      	    "tablet",
//      	    "smartwatch",
//      	    "Bluetooth headphones",
//      	    "gaming mouse",
//      	    "LED TV",
//      	    "wireless earbuds",
//      	    "fitness tracker",
//      	    "external monitor",
//      	    "wireless router",
//      	    "Bluetooth speaker",
//      	    "gaming keyboard",
//      	    "action camera",
//      	    "portable charger",
//      	    "noise-cancelling headphones",
//      	    "digital camera",
//      	    "power bank",
//      	    "gaming headset",
//      	    "smart home devices",
//      	    "e-book reader"
      	};
     
      try {
          try (FileWriter writer = new FileWriter("product_data.txt")) {  //Object to write the scraped data into a txt file
              for (String searchTerm : searchTerms) {  //loop that iterates over each search term
//              	Inside this loop, it performs a search for each term, waits for the page to load, processes the
//              	page to get product data, clicks on the next page link, and processes the next page too.
                  WebElement searchBox = driver.findElement(By.name("q"));
                  searchBox.clear();
                  searchBox.sendKeys(searchTerm);
                  searchBox.sendKeys(Keys.ENTER);

                  Thread.sleep(5000);

                  processPage(driver, writer);

                  WebElement nextPageLink = driver.findElement(By.xpath("//*[@id='xjs']/table/tbody/tr/td[3]/a"));
                  nextPageLink.click();
                  
                  processPage(driver, writer);
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
      } catch (InterruptedException e) {
          e.printStackTrace();
      } finally {
          driver.quit();
      }
  
  }

  private static void processPage(WebDriver driver, FileWriter writer) throws InterruptedException, IOException {
      List<WebElement> productCards = driver.findElements(By.className("i0X6df"));  //It finds all the product cards on the webpage by searching for elements with the class name "i0X6df" and stores them in a list.

      if (productCards.isEmpty()) {
          System.out.println("No product cards found on the page.");
      } else {
          for (WebElement productCard : productCards) {  //It loops over each product card in the list.
              WebElement productNameElement = productCard.findElement(By.className("tAxDx"));
              String productName = productNameElement.getText();  //It extracts the text (i.e., the product name) from the product name element.

              WebElement productPriceElement = productCard.findElement(By.cssSelector("span.a8Pemb.OFFNJ"));
              String productPrice = productPriceElement.getText();

              WebElement productSiteElement = productCard.findElement(By.cssSelector("div.aULzUe.IuHnof"));
              String productSite = productSiteElement.getText();

              if (productName.isEmpty() || productPrice.isEmpty() || productSite.isEmpty()) {
                  // Skip writing to the CSV file if any of the data fields is empty
                  continue;
              }

              String escapedProductName = productName.replace("\"", "\"\"");  //It sanitizes the product name by replacing any double quotes with two double quotes and removing any commas.
              escapedProductName = escapedProductName.replace(",", "");

              String escapedProductPrice = productPrice.replace("$", "");  //It sanitizes the product price by removing the dollar sign and any commas.
              escapedProductPrice = escapedProductPrice.replace(",", "");
              //This is done to convert the price into a numeric format that can be used in computations.

              writer.write(escapedProductName + "," + escapedProductPrice + "," + productSite + "\n");  //it writes the product name, product price, and product site to the file. These values are separated by commas.
          }
      }
  }
}

