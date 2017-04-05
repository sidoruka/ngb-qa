package epam.autotests.page_objects.panels;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.epam.commons.Timer;
import com.epam.jdi.uitests.core.logger.JDILogger;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;

public class PanelReferenceChromosome extends Element{

	//private WebElement MainBoard = null;
	private WebElement RefSingleTrack;
	private WebDriver driver = getDriver();
	
	public PanelReferenceChromosome(){
		super();
	}
	
	public PanelReferenceChromosome(By byLocator){
		super(byLocator);
	}
	
	public PanelReferenceChromosome SelectChromosome(String Chrm_value){
		//int index = NGB_Site.getChromosome().findElements(By.xpath("//option[@label='" +Chrm_value+ "']/preceding-sibling::*")).size();
		//new Select(NGB_Site.chromosomeCh).selectByIndex(index);
		return this;
	}
	
	public PanelReferenceChromosome SetCoordinates(String Crd_value){
		/*WebElement LocationField = getDriver().findElement(By.xpath("//div[@class='main-board']//div[@class='col-sm-3 track-title panel panel-left']//input[@title='location']"));
	    LocationField.click();
	    LocationField.sendKeys(Keys.CONTROL + "a");
	    //LocationField.sendKeys(Keys.CONTROL + "v");
	    LocationField.sendKeys(Crd_value);
	    LocationField.sendKeys(Keys.ENTER);*/
	    
	    return this;
	}
	
	public boolean isChromosomeSelected(){
		return false;
	}
	
	public PanelReferenceChromosome GetRefPanel(){
		RefSingleTrack = driver.findElement(By.xpath("//div[@class='main-board']"
												   + "/div[@class='ng-pristine ng-untouched ng-valid ng-scope']"
												   + "/list-of-tracks//div[@class='col-sm-9 panel panel-main ng-pristine ng-untouched ng-valid ng-scope']"
												   + "//div/*[local-name() = 'svg']/*[local-name() = 'g']/*[local-name() = 'g']/*[local-name() = 'g'][@class='data draggable']"));
		return this;
	}

	
	public void MoveRefAndCheck(int xDistance){

		GetRefPanel();
		//Get starting locations
//		List<WebElement> nucleotides = RefSingleTrack.findElements(By.xpath("/*[local-name() = 'g']"));
		List<WebElement> nucleotides = driver.findElements(By.xpath("//div/*[local-name() = 'svg']/*[local-name() = 'g']/*[local-name() = 'g']/*[local-name() = 'g'][@class='data draggable']/*[local-name() = 'g']/*[local-name()='text']"));
		
		float[] coords = new float[nucleotides.size()];
		String[] letters = new String[nucleotides.size()];
		
		for (int i=0; i<nucleotides.size(); i++) {
			coords[i] = Float.parseFloat(nucleotides.get(i).getAttribute("x"));
			letters[i] = nucleotides.get(i).getText();
		}
		
		//Movement
		dragAndDropBy(xDistance, 0);
		Timer.sleep(10000);
		
		//Get new locations
		List<WebElement> nucleotides_new = driver.findElements(By.xpath("//div/*[local-name() = 'svg']/*[local-name() = 'g']/*[local-name() = 'g']/*[local-name() = 'g'][@class='data draggable']/*[local-name() = 'g']/*[local-name()='text']"));
		float[] coords_new = new float[nucleotides_new.size()];
		String[] letters_new = new String[nucleotides_new.size()];
		
		for (int i=0; i<nucleotides_new.size(); i++) {
			coords_new[i] = Float.parseFloat(nucleotides_new.get(i).getAttribute("x"));
			letters_new[i] = nucleotides_new.get(i).getText();
		}
		
		System.out.println("Length of 1 list: " + nucleotides.size());
		System.out.println("Length of 2 list: " + nucleotides_new.size());
		//Checking
		for (int i=0; i<nucleotides_new.size(); i++) {
			
			System.out.println(String.valueOf(i)+") Before: " + letters[i] + ": " + String.valueOf(coords[i]) + " | "
												 + "After: " + letters_new[i] + ": " + String.valueOf(coords_new[i]) + " | "
												 + "Diff: " + String.valueOf(Math.abs(coords[i]-coords_new[i])));
		}
		System.out.println("---------------------------------------------------------------------------------");
	}
	
	public void MoveRefAndCheck2(int xDistance){
		WebElement referenceTrack = driver.findElement(By.xpath("//div[@class='main-board']"
												   + "/div[@class='ng-pristine ng-untouched ng-valid ng-scope']"
												   + "/list-of-tracks//div[@class='col-sm-9 panel panel-main ng-pristine ng-untouched ng-valid ng-scope']"
												   + "//div/*[local-name() = 'svg']/*[local-name() = 'g']/*[local-name() = 'g']/*[local-name() = 'rect']"));
		
		int trackWidth = referenceTrack.getSize().width;
	}
	
	
	/*public WebElement getMainBoard() {
		return MainBoard;
	}

	public void setMainBoard() {
		MainBoard = getDriver().findElement(By.xpath("//div[@class='main-board']"));
	}
	*/
}
