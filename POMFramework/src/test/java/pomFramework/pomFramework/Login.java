package pomFramework.pomFramework;

import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login {
	
	public static WebDriver driver;
	public static WebElement element;
	
	public static WebElement edtuserName(WebDriver driver)
	{
		WebElement element = driver.findElement(By.id("username"));
		return element;
	}
	
	public static WebElement edtpassword(WebDriver driver)
	{
		WebElement element = driver.findElement(By.id("password"));
		return element;
	}
	
	public static WebElement btnlogin(WebDriver driver)
	{
		WebElement element = driver.findElement(By.id("btnPrimaryLogin"));
		return element;
	}

}
