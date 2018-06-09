
package pomFramework;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import pomFramework.Login;
public class Testing {
	
	static WebDriver driver;

	/**
	 * @param args
	 * @throws IOException 
	 */
	@Test(priority=1)
	public static void umplogin() throws IOException {
		// TODO Auto-generated method stub

		System.out.println("Hello World");
		System.out.println(System.getProperty("user.dir"));
		
		driver = BaseClass.getDriver();
		driver.navigate().to("https://ump.verifone.com");
		
		Login.edtuserName(driver).sendKeys("vfibala@yopmail.com");
		Login.edtpassword(driver).sendKeys("Veri1234");
		Login.btnlogin(driver).click();
		
	}

}
