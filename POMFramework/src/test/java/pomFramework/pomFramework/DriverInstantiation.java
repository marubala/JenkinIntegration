package pomFramework.pomFramework;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverInstantiation {

	private static WebDriver driver = null;
	//private static HtmlUnitDriver driver = null;
	  private static boolean firstThread = true;
	  private static DriverInstantiation driverInstance;
	  private static final String PlatformType = null;
		private static final Exception Exception = null;
		//private static final String TIMEOUT = null;
		static WebElement element;// to store an element/object
		static String strDirectory;
		static Capabilities caps;
		static DesiredCapabilities capability;
		static Properties pro;
		static int rndmnum;
		//Retrieve working directory
		//static String strDir1 = System.getProperty("user.dir");
		public static String strDir;
	  
		//Creating Driver instance and using it across all the Test classes*************************************************************************************************************
		
	  protected DriverInstantiation() throws IOException {
		  
		  strDir = BaseClass.getDir();
		  String Globalsheet = strDir+"\\Datasheets\\GlobalParameters.xls";
		  System.out.println(strDir);
			FileInputStream fis = new FileInputStream(Globalsheet);
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			HSSFSheet Wsheet = wb.getSheet("GlobalParameters");
			String strcellvalue = Wsheet.getRow(1).getCell(0).getStringCellValue();
			switch(strcellvalue)
			{
				case "Firefox":
					
					/*FirefoxProfile fp = new FirefoxProfile();
					fp.setAcceptUntrustedCertificates(true);
					fp.setAssumeUntrustedCertificateIssuer(false);*/
					
					//driver = new HtmlUnitDriver();
					System.setProperty("webdriver.gecko.driver", strDir+"\\Selenium_Drivers\\geckodriver.exe");
					driver = new FirefoxDriver();
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					System.out.println("The system is running");
					 Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
					 String browserVersion = caps.getVersion();					 
					 break;
					
				case "IE":
					String browserName="Internet Explorer";
				    String WebDriverProperty="webdriver.ie.driver";
				    String WebDriverLocation=strDir+"\\Selenium_Drivers\\IEDriverServer.exe";
				    String WebDriver = "InternetExplorerDriver";	
				    System.setProperty(WebDriverProperty,WebDriverLocation);
				    capability = new DesiredCapabilities("internet explorer", WebDriver, null);
				    capability.setCapability("ignoreProtectedModeSettings", true);
				    driver= new InternetExplorerDriver();
				    driver.manage().window().maximize();
				    driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
				    caps = ((RemoteWebDriver) driver).getCapabilities();
					browserVersion = caps.getVersion();
					break;
					
				case "Chrome":
					browserName="Chrome";
					WebDriverProperty="webdriver.chrome.driver";
				 	WebDriverLocation=strDir+"\\Selenium_Drivers\\chromedriver.exe";
				 	WebDriver="ChromeDriver";
				 	System.setProperty(WebDriverProperty,WebDriverLocation);
				 	driver= new ChromeDriver();
				 	driver.manage().window().maximize();
				 	driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
					caps = ((RemoteWebDriver) driver).getCapabilities();
					browserVersion = caps.getVersion();
					break;
			}		  
	  }
	  

	  public static DriverInstantiation getInstance() throws IOException {
	     if(driverInstance == null) {
	        simulateRandomActivity();
	        driverInstance = new DriverInstantiation();
	     }
	     return driverInstance;
	  }
	  
	  private static void simulateRandomActivity() {
	     try {
	        if(firstThread) {
	           firstThread = false;	           
	             Thread.sleep(20);
	       }
	     }
	     catch(InterruptedException ex) {
	    	 ex.printStackTrace();
	     }
	  }
	  
	  public WebDriver getDriver(){		  
		  return this.driver;
	  }
	  
	
	  
	}
