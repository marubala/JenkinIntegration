
package pomFramework;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import pomFramework.Login;
public class Logout {
	
	static WebDriver driver;

	
	@Test(priority=1)
	public static void umplogout() throws IOException, Exception {
		// TODO Auto-generated method stub

		System.out.println("Hello World");
		System.out.println(System.getProperty("user.dir"));
		
		driver = BaseClass.getDriver();
		driver.findElement(By.xpath(".//span[contains(text(),'Bala M')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.linkText("Sign Out")).click();
		
	}

}
