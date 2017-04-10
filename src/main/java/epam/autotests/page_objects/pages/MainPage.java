package epam.autotests.page_objects.pages;

import com.epam.jdi.uitests.web.selenium.driver.SauceLabRunner;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import epam.autotests.page_objects.panels.DatasetsPanel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

public class MainPage extends WebPage{

	@FindBy(xpath = "//ngb-data-sets")
	public DatasetsPanel datasetsPanel;

	public void Datasets() {
		datasetsPanel.clickCenter();
	}

	public static WebDriver remoteDriver(){
		SauceLabRunner.authentication.setUsername("lifescience");
		SauceLabRunner.authentication.setAccessKey(System.getenv("SAUCE_ACCESS_KEY"));
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("platform", "Windows 10");
		caps.setCapability("version","57.0");
		caps.setCapability("screenResolution", "1280x1024");
        RemoteWebDriver driver = new RemoteWebDriver(SauceLabRunner.getSauceUrl(), caps);
		driver.executeScript("sauce:job-name= Autotest from travis job #"+System.getenv("TRAVIS_JOB_NUMBER")+"\"");
		return driver;
	}


}
