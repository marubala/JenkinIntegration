package pomFramework.pomFramework;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import android.content.Context;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
public class BaseClass {
	
	
	private static final String PlatformType = null;
	private static final Exception Exception = null;
	//private static final String TIMEOUT = null;
	@SuppressWarnings("rawtypes")
	static AppiumDriver appiumdriver = null;
	static WebDriver driver = null;
	static WebElement element;// to store an element/object
	static String strDirectory;
	static Capabilities caps;
	static List<WebElement> listofObjects;//To store list of objects
	static Properties pro;
	static String rndmnum;
	static String timeStamp;
	static DesiredCapabilities desiredCaps;
	public static Boolean bln2;
	//Retrieve working directory
	public static String strDir1;
	public static String strDir;
	public static String strpwd;
	static HSSFCell Cell;
	static HSSFRow Row;
	public static Robot rbt;
	static AppiumDriverLocalService service;
	private static AppiumServiceBuilder builder;
	static String service_url;
	static Context context;
	static String deviceID;
	static String deviceModel;
	static WebDriverWait wait;
	public static Boolean vFlag;

		
	static{
		try{
			int i;
			strDir1 = System.getProperty("user.dir");
			String[] arr1 = strDir1.split("\\\\");
	        for(i=0;i<=arr1.length-2;i++)
	        {
	        	if(i==0){
	        		strDir = arr1[i];
	        	}
	        	else
	        	{
	        		strDir = strDir+"\\"+arr1[i];
	        	}
	        }
	       // strDir = strDir1;  					
			//Load Object Repository
			String OR = strDir+"\\ObjectRepository\\ObjectRepository.properties";
			File ObjRepo = new File (OR);
			FileInputStream fis1 = new FileInputStream(ObjRepo);
			pro = new Properties();
			pro.load(fis1);
			
		}catch(Exception e){
			System.out.println("There is some exception");
			}
	}
	
	
	
	public static Properties LoadOR() throws IOException
	{
		return pro;
		
	}
	
	public static String getDir() throws IOException
	{
		return strDir;
		
	}

	public static WebDriver getDriver() throws IOException
	{
		driver = DriverInstantiation.getInstance().getDriver();
		return driver;
		
	}
	 
	  public static String timeStamp() throws InterruptedException, IOException
	    {
			  DateFormat df = new SimpleDateFormat("MMddyyHHmmss");
		     Date dateobj = new Date();
		     String timestamp = df.format(dateobj);
		     return timestamp;
	    }
	  

	  
	  public static void captureScreenShot(String ipTestCaseName) throws Exception {
		  	        
	        File sourceFile=((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
	        DateFormat df = new SimpleDateFormat("MM_dd_yyyy_HHmmss");
	        String DestfileName=ipTestCaseName+df.format(new Date())+".png";
	        //create sub folder name as current Date
	        DateFormat df1 = new SimpleDateFormat("MM_dd_yyyy");
	        String subfolderNameString=df1.format(new Date());
	        File subfolderName = new File(strDir+"\\Screenshots\\"+subfolderNameString);
	        if(!subfolderName.exists())
	        {
	        	subfolderName.mkdirs();
	        }
	        	        
	        File destFile = new File(subfolderName+"\\"+DestfileName);
	        
	               
	        FileInputStream ipStream = new FileInputStream(sourceFile);
	        FileOutputStream opStream = new FileOutputStream(destFile);
	        
	        byte[] buffer = new byte[1024];

    	    int length;
    	    //copy the file content in bytes
    	    while ((length = ipStream.read(buffer)) > 0){

    	    	opStream.write(buffer, 0, length);

    	    }

    	    ipStream.close();
    	    opStream.close();

    	    //delete the original file
    	    sourceFile.delete();

    	    System.out.println("File is copied successful!");
    	    
    	    throw Exception;
	    }
	  
	
		 public static String fetchInputValue(String ipWorkBookName, String ipSheetName,String ipTestCaseName,String ipColumnName) throws Exception{
			 
			String inputvalue = null;
			
			String Globalsheet = strDir+"\\Datasheets\\"+ipWorkBookName+".xls";
			FileInputStream fis = new FileInputStream(Globalsheet);
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			HSSFSheet sheetName = wb.getSheet(ipSheetName);
				
				int row, col;
				int rowcount = sheetName.getLastRowNum();
				int colCount = sheetName.getRow(0).getLastCellNum();
				for(row =0;row<=rowcount;row++)
				{
					String tcID = sheetName.getRow(row).getCell(0).getStringCellValue();
					if(tcID.contains(ipTestCaseName))
					{
						break;
					}
				}
				for(col =0;col<=colCount;col++)
				{
					String tcID = sheetName.getRow(0).getCell(col).getStringCellValue();
					if(tcID.contains(ipColumnName))
					{
						String inputvalue1 = sheetName.getRow(row).getCell(col).getStringCellValue();
						inputvalue = inputvalue1.replaceAll("\"", "");
						break;
					}
				}
				
			
			 return inputvalue;
				
			}
	 
	 
	//********************************************************************************************************************************
		//to implement Explicit wait for all the steps.. we have to call this method before perform any operation on objects
		public static void Wait(WebElement element) throws IOException, InterruptedException {
		
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(element));
				       
		}
		
		public static void getSize(WebElement element) throws IOException, InterruptedException {
			int i;
			for(i=1;i<=60;i++)
			{
				if(element.getSize().getHeight()>0)
				{
					break;
				}
				else
				{
					Thread.sleep(2000);
				}
			}
				       
		}	
	
	//*******************************************************************************************************************************
	//To send key combinations
		
		//Keys.chord(Keys.CONTROL, "a");
		
	public static void SendKey(String keyvalue) throws IOException, AWTException, InterruptedException {
		
		switch(keyvalue.toUpperCase())
		{
		case "ENTER":
			Keys.chord(Keys.ENTER);
			break;
		case "TAB":
			Keys.chord(Keys.TAB);
			break;
		case "DELETE":
			Keys.chord(Keys.DELETE);
			break;
		case "BACK":
			Keys.chord(Keys.BACK_SPACE);
			break;
		
		default:
			break;
			
		}
		Thread.sleep(3000);
	}
	
	
	//*******************************************************************************************************************************
	
		public static void robotSendKey(String keyvalue) throws IOException, AWTException, InterruptedException {
			Robot rbt = new Robot();
			switch(keyvalue.toUpperCase())
			{
			case "ENTER":
				rbt.keyPress(KeyEvent.VK_ENTER);
				break;
			case "TAB":
				rbt.keyPress(KeyEvent.VK_TAB);
				break;
			case "DELETE":
				rbt.keyPress(KeyEvent.VK_DELETE);
				break;
			case "BACKSPACE":
				rbt.keyPress(KeyEvent.VK_BACK_SPACE);
				break;
			case "DOWN":
				rbt.keyPress(KeyEvent.VK_DOWN);
				break;
			case "UP":
				rbt.keyPress(KeyEvent.VK_UP);
				break;
			default:
				break;
				
			}
			Thread.sleep(3000);
		}
	
	public static String getCurrentDate(int ipDate)
	{
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, ipDate);	
		// Create object of SimpleDateFormat class and decide the format
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 String date1=dateFormat.format(cal.getTime());
		 //String date1=
		 return date1;
	 	 
	}
	
	
	public static void Scroll(String ipUpDown) throws InterruptedException
	 {
		 JavascriptExecutor jse = (JavascriptExecutor)driver;
		 if(ipUpDown.toUpperCase()=="UP")
		 {
		 jse.executeScript("scroll(0,-250);");
		 }
		 else
		 {
		 jse.executeScript("scroll(0,250);");
		 }
		 Thread.sleep(3000);
	 }
	
	 
	 public static void Encode(String ipValue) throws IOException, InterruptedException {
		 
		 	String encoded = Base64.getEncoder().encodeToString(ipValue.getBytes(StandardCharsets.UTF_8));
	        System.out.println("The encoded value is "+encoded);
	 }
	 
	 public static String Decode(String ipValue) throws IOException, InterruptedException {
		 
		 	byte[] decodedbyte = Base64.getDecoder().decode(ipValue);
	        String decoded = new String(decodedbyte,StandardCharsets.UTF_8);
	        return decoded;
	 }
	 

	// This function will handle stalelement reference exception
	public static void handleStaleElementException(String elementName) throws InterruptedException { 
		for(int i=0; i<=5;i++)
			 
		{
		  try{
		     driver.findElement(By.xpath(elementName)).click();
		     Thread.sleep(2000);
		     break;	 
		  }
		 
		catch(StaleElementReferenceException e)
		  {		 
			System.out.println("The error message is "+(e.getMessage()));
		 
			}
		 
		} 
	}
	
	
	//*********************************************************************************************************************************************************************************************
	
	//*****************************************************************************************************************************KEYWORDS********************************************************

	//find out the identifier based on string passed
	public static WebElement findIdentifier(String element)
	{
		WebElement object;
		String property = pro.getProperty(element);
		if(property.contains("//"))
		{
			object=driver.findElement(By.xpath(pro.getProperty(element)));
		}
		else if(!property.contains("//") && !property.contains(":id/"))
		{
			object=driver.findElement(By.className(pro.getProperty(element)));
		}
		else
		{
			object=driver.findElement(By.id(pro.getProperty(element)));
		}
		return object;
	}
	
	
	//wait until element is visible
	public static void WaitUntilElementVisible(String element) throws IOException, InterruptedException {
		
		WebElement ele = BaseClass.findIdentifier(element);	
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
	
	//wait until element is enabled
		public static void WaitUntilElementEnabled(String element) throws IOException, InterruptedException {
			
			WebElement ele = BaseClass.findIdentifier(element);	
			for(int e=1;e<=30;e++)
			{				
				if(ele.isEnabled())
				{
					break;
				}
				else
				{
					Thread.sleep(2000);
				}
			}
		}
	
	//wait until element is displayed
		public static void WaitUntilElementDisplayed(String element) throws IOException, InterruptedException {
					
			WebElement ele = BaseClass.findIdentifier(element);	
			for(int d=1;d<=30;d++)
			{				
				if(ele.isDisplayed())
				{
					break;
				}
				else
				{
					Thread.sleep(2000);
				}
			}
		}
	//Check whether element is displayed on screen
		public static void checkElementDisplayed(String element) throws Exception {
					
			WebElement ele = BaseClass.findIdentifier(element);
			if(ele.isDisplayed())
				{
					Reporter.log("The expected element "+element+" is displayed as expected");
				}
			else
			{
				Reporter.log("The expected element "+element+" is not displayed");
				throw Exception;
			}
		}
		
		//Check whether element is enabled/disabled on screen
		public static void checkElementEnabled(String element, Boolean ipExpValue) throws java.lang.Exception {
					
			WebElement ele = BaseClass.findIdentifier(element);
			if(ele.isDisplayed()==ipExpValue)
				{
					Reporter.log("The expected element "+element+" is enabled/not anabled as expected");
				}
			else
			{
				Reporter.log("The expected element "+element+" is enabled/not anabled");
				throw Exception;
			}
		}		

		//Check whether element is selected or not selected on screen
		public static void checkElementSelected(String element, Boolean ipExpValue) throws Exception {
					
			WebElement ele = BaseClass.findIdentifier(element);
			if(ele.isDisplayed()==ipExpValue)
				{
					Reporter.log("The expected element "+element+" is selected/Not Selected as expected");
				}
			else
			{
				Reporter.log("The expected element "+element+" is selected/Not Selected");
				throw Exception;
			}
			
		}
	
		//Check whether element is not displayed on screen
		public static void checkElementNOTDisplayed(String element) throws Exception {
							
			WebElement ele = BaseClass.findIdentifier(element);
			if(ele.getSize().getWidth()==0)
				{
					Reporter.log("The expected element "+element+" is not displayed as expected");
				}
			else
			{
				Reporter.log("The expected element "+element+" is displayed");
				throw Exception;
			}
		}

		//get text from element and compare the value
		public static void verifyText(String element,String ipExpText) throws Exception {
							
			WebElement ele = BaseClass.findIdentifier(element);
			if(ele.getText().toUpperCase().contains(ipExpText.toUpperCase()))
				{
					Reporter.log("The expected text "+ipExpText+" is displayed as expected");
				}
			else
			{
				Reporter.log("The expected text "+ipExpText+" is not displayed");
				throw Exception;
			}
		}

	
		//Collect elements and return element based on Text
	public static WebElement CollectElements(String element,String inputText) throws IOException, InterruptedException
	{
		List<WebElement> noofeles1;
		WebElement returnele=null;
		Boolean vFlag1=null;
		OuterLoop:
		for(int k=1;k<=10;k++)
		{
			
			String property = pro.getProperty(element);
			if(property.contains("//"))
			{
				noofeles1 = driver.findElements(By.xpath(pro.getProperty(element)));
			}
			else if(!property.contains("//") && !property.contains(":id/"))
			{
				noofeles1 = driver.findElements(By.className(pro.getProperty(element)));
			}
			else
			{
				noofeles1 = driver.findElements(By.id(pro.getProperty(element)));
			}
			
			//innerloop:
			for(WebElement ele1 : noofeles1)
			{
				String eleText1 = ele1.getText();
				if(eleText1.toUpperCase().contains(inputText.toUpperCase()))
				{
					returnele = ele1;
					break OuterLoop;
				}
									
			}
			
		}
		return returnele;
	}


}













	
/*
//IE 11 Configurations
1. The IEDriverServer executable must be downloaded and placed in your PATH.
2. On IE 7 or higher on Windows Vista or Windows 7, you must set the Protected Mode settings for each zone to be the same value. 
The value can be on or off, as long as it is the same for every zone. To set the Protected Mode settings, choose "Internet Options..." from the Tools menu, and click on the Security tab. For each zone, there will be a check box at the bottom of the tab labeled "Enable Protected Mode".
3. Additionally, "Enhanced Protected Mode" must be disabled for IE 10 and higher. This option is found in the Advanced tab of the Internet Options dialog.
4. The browser zoom level must be set to 100% so that the native mouse events can be set to the correct coordinates.
5. For IE 11 only, you will need to set a registry entry on the target computer so that the driver can maintain a connection to the instance of Internet Explorer it creates. 
For 32-bit Windows installations, the key you must examine in the registry editor is HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_BFCACHE. For 64-bit Windows installations, the key is HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_BFCACHE. 
Please note that the FEATURE_BFCACHE subkey may or may not be present, and should be created if it is not present. Important: Inside this key, create a DWORD value named iexplore.exe with the value of 0.
*/



/*
 Virtual Machine setup:
 =======================
 1. Download windows image from https://dev.windows.com/en-us/microsoft-edge/tools/vms/windows/
 2. Download Oracle Virtualbox
 3. Double click on Image and define all the parameters
 4. If you would like to get some files from physical machine, go to share folder and share the folders
 */


/* Switch off Auto Lock
 * HKEY_CURRENT_USER\Software\Policies\Microsoft\Windows\Control Panel\Desktop
 * )I use another method:
1. Press Win+R and type "Regedit.exe"
2. Go to the path below
HKEY_CURRENT_USER\Software\Policies\Microsoft\Windows\Control Panel\Desktop
3. DELETE "ScreenSaverIsSecure"

Go to the "Control Panel".
Click on "Appearance and Personalization".
Click on "Change Screen Saver".
Uncheck "On resume, display logon screen
 * 
 * 
 */

/* Major challenges faced in Selenium Automation
 * 1. UnreachableBrowserException - The page is died. It could be due to previously opened browser did not close properly. To Resolve this we can set the driver = null and use the driver.quit method instead of driver.close. Also we can kill the driver process in task manager
 * 2. StaleElementReferenceException - This was happening due to page has changed the reference. Increased wait time and captured the exception and cleared it. Put the recovery mechanism
 * 
 * 
 * 
 * 
 * 
 * 
 */


/* Upload Selenium Automations scripts into Stash
 *
 * 1. Place all the folders under C:/Bala/Stash/UMS
 * 2. Open SourceTree
 * 3. Make sure all the files comes under "Unstaged" files
 * 4. Check "Staged" files checkbox and make sure now Unstaged files are empty
 * 5. Click on Commit and make sure Push toolbar has count as 1
 * 6. Click on Settings icon on the right top corner
 * 7. Copy the URL and Remove the existing record(in case of authentication failure)
 * 8. Add a new record with above copied URL (Dont add Username)
 * 9. Click on Push button and enter the credentails
 * 
 */


//Settings to Auto download
//===============================================================================================================

/*
//Create object of FirefoxProfile in built class to access Its properties.
FirefoxProfile fprofile = new FirefoxProfile();
//Set Location to store files after downloading.
fprofile.setPreference("browser.download.dir", "D:\\WebDriverdownloads");
fprofile.setPreference("browser.download.folderList", 2);
//Set Preference to not show file download confirmation dialogue using MIME types Of different file extension types.
fprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", 
  "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;"//MIME types Of MS Excel File.
  + "application/pdf;" //MIME types Of PDF File.
  + "application/vnd.openxmlformats-officedocument.wordprocessingml.document;" //MIME types Of MS doc File.
  + "text/plain;" //MIME types Of text File.
  + "text/csv"); //MIME types Of CSV File.
fprofile.setPreference( "browser.download.manager.showWhenStarting", false );
fprofile.setPreference( "pdfjs.disabled", true );

*/



/* Appium Java Client POM
 * 
 
<dependency>
<groupId>com.google.code.gson</groupId>
<artifactId>gson</artifactId>
<version>2.6.2</version>
</dependency>
*/
 

/* To find package name and launch activity of .apk file
 * 
 * 1. C:\Users\T_BalamuruganM1\AppData\Local\Android\sdk\build-tools\24.0.1
 * 2. Launch CMD command
 * 3. get the complete path of .apk file path (C:\Bala\Appium\APK\AppMarketplace-release-1.0.0.218.apk)
 * 4. Run the command "aapt d badging C:\Bala\Appium\APK\AppMarketplace-release-1.0.0.218.apk
 * 
 * 
 * 
 * 
 */
 
//http://www.software-testing-tutorials-automation.com/2015/10/ui-automator-viewer-get-android-app.html
//http://appium.readthedocs.io/en/stable/en/writing-running-appium/appium-bindings/
//https://developer.android.com/ndk/reference/keycodes_8h.html
//


/* No compiler is provided in this environment. Perhaps you are running on a JRE rather than a JDK?
 * 
 * Go to Eclipse Window--Preferences--Java--Installed JRE's
 * If you dont find JDK, then click on Add button
 * Select Standard VM---Click on Next
 * Select the JDK path where it is installed (C://Program files//Java//jdk-1.8.50
 * Click Finish
 * Select the JDK option in the Installed JRE's window
 * Click on Apply
 * 
 * http://learn-automation.com/maven-no-compiler-is-provided-in-this-environment-selenium/
 * */
 
/* Implementation and Extends syntax
 * public class DeviceSetup extends BaseClass {
 * public class DeviceSetup implements Interface {
*/



// ===========================================================Running Java program in a batch file====================================
/*
 * set path="C:\Program Files\Java\jdk1.8.0_51\bin";

javac MyClass.java

java MyClass

pause
 * 
 */






