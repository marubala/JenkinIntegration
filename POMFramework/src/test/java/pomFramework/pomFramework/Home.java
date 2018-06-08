package pomFramework.pomFramework;


import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Home {

	
	public static WebDriver driver;
	public static WebElement element;
	
	public static WebElement tabMediaLib(WebDriver driver)
	{
		WebElement element = driver.findElement(By.id("btnCreate"));
		return element;
	}
}
