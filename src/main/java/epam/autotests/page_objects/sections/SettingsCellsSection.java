package epam.autotests.page_objects.sections;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

class SettingsCellsSection extends Section {

    @FindBy(xpath = ".//button")
    private Button hotkey;
    /*
	@FindBy(xpath = ".//table//tr")
	public Elements<Section> options;
	
	public String getOptionName(){
		for (int i = 0; i < options.size(); i++) {
			options.get(i).get(By.xpath(""));
		}
	}*/

    public void setNewHotKey(Keys funcKey, String charKey) {
        hotkey.clickCenter();
//		hotkey.get(By.xpath("./parent::div")).sendKeys(Keys.chord(funcKey, charKey));
        new Actions(getDriver()).keyDown(funcKey).sendKeys(charKey).keyUp(funcKey).build().perform();
//		new Actions(getDriver()).sendKeys(value).build().perform();
    }

    public String getCurrentHotKey() {
        return hotkey.getText();
    }
}
