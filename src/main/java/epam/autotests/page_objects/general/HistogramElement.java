package epam.autotests.page_objects.general;

import org.openqa.selenium.support.FindBy;

import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;


public class HistogramElement extends Section{
	
	@FindBy(xpath = ".//parent::*[local-name()='g']/preceding-sibling::*[local-name()='text']")
	private Label columnName;
	
	@FindBy(xpath = "./ancestor::*[local-name()='svg']/*[local-name()='text']")
	private Label columnValue;
	
	public String getElementName(){
		return columnName.getText();
	}

	public String getElementValue(){
		return columnValue.getText();
	}
}
