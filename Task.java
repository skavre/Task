package com.test;


	

	import java.util.List;
	import java.util.concurrent.TimeUnit;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;

	public class Task {

	    static WebDriver driver;
	    private String SliderTextValue1 = "560";
	    private String SliderTextValue2 = "820";

	    // Wait object for waiting until elements are ready
	    private WebDriverWait wait;

	    // Constructor to initialize WebDriver and WebDriverWait
	    public Task(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, 20);
	    }

	    // Method to interact with the slider element
	    private void setSlider() throws InterruptedException {
	        WebElement slider = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[1]/div[1]/div[2]/div/div/span[1]/span[3]")));
	        Actions action = new Actions(driver);
	        action.clickAndHold(slider)
	              .moveByOffset(93, 0)
	              .release()
	              .perform();
	        Thread.sleep(2000);
	        
	        // simulate keyboard arrow keys movement
	        action.sendKeys(Keys.ARROW_RIGHT)
	              .sendKeys(Keys.ARROW_RIGHT)
	              .sendKeys(Keys.ARROW_RIGHT)
	              .perform();
	    }

	    // Method to set slider input field text
	    private void setSliderTextField(String value) throws InterruptedException {
	        WebElement sliderTextValue = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='number']")));
	        sliderTextValue.click();
	        Thread.sleep(1000);
	        
	        // Clear any previous values
	        sliderTextValue.sendKeys(Keys.BACK_SPACE);
	        sliderTextValue.sendKeys(Keys.BACK_SPACE);
	        sliderTextValue.sendKeys(Keys.BACK_SPACE);
	        Thread.sleep(1000);
	        
	        // Set the new value
	        sliderTextValue.sendKeys(value);
	        Thread.sleep(1000);
	    }

	    // Main method to perform all actions
	    public static void main(String[] args) throws InterruptedException {
	        System.setProperty("webdriver.chrome.driver", "D:\\VS\\chromedriver-win64\\chromedriver.exe");
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	        driver.manage().deleteAllCookies();

	        driver.get("https://www.fitpeo.com/");
	        WebDriverWait wait = new WebDriverWait(driver, 20);

	        // Wait for page to load and click the link
	        WebElement revenueCalculator = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/revenue-calculator']")));
	        revenueCalculator.click();
	        Thread.sleep(2000);
	        
	        // Scroll down
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("window.scrollBy(0,300)", "");
	        Thread.sleep(2000);

	        // Create Test object and interact with the page elements
	        Task test = new Task(driver);
	        test.setSlider();
	        test.setSliderTextField(test.SliderTextValue1);  // Set the first value as 560
	        // Validate slider value 
	        WebElement Textfield=driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div[2]/div/div/span[1]/span[3]/input"));
	        String GetValue=Textfield.getAttribute("value");
	        System.out.println("Slider Position Cuurent Value = "+GetValue);
	        test.setSliderTextField(test.SliderTextValue2);  // Set the second value as 820

	        // Scroll further down
	        js.executeScript("window.scrollBy(0,150)", "");
	        Thread.sleep(1000);

	        // Interact with checkboxes
	        List<WebElement> allCheckBox = driver.findElements(By.xpath("//input[@type='checkbox']"));
	        if (allCheckBox.size() > 10) {
	            allCheckBox.get(0).click();
	            allCheckBox.get(1).click();
	            allCheckBox.get(2).click();
	            allCheckBox.get(7).click();
	        } else {
	            System.out.println("Checkboxes not available.");
	        }

	        // Get total reimbursement value and verify
	        WebElement totalReimbursement = driver.findElement(By.xpath("/html/body/div[2]/div[1]/header/div/p[4]/p"));
	        String totalAmount = totalReimbursement.getText();
	        System.out.println("Total Amount = " + totalAmount);

	        // Verify the amount matches
	        boolean verifyAmount = totalAmount.equals("$110700");
	        System.out.println("Total Amount Is Match = " + verifyAmount);

	        // Close the browser
	        driver.quit();
	    }
	


}
