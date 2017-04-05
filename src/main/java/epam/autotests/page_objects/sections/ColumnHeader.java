package epam.autotests.page_objects.sections;

import org.openqa.selenium.support.FindBy;

import static epam.autotests.page_objects.site.NGB_Site.*;

import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.common.Text;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;

public class ColumnHeader extends Section{

//	@FindBy(xpath = ".//span")
	@FindBy(css=".ui-grid-header-cell-label")
	private Text columnName;
	
//	@FindBy(xpath = ".//div[contains(@class,'jqx-grid-column-sortascbutton')]")
	@FindBy(xpath = ".//div[@aria-sort='ascending']")
	private Element ascendingSortSign;

//	@FindBy(xpath = ".//div[contains(@class,'jqx-grid-column-sortdescbutton')]")
	@FindBy(xpath = ".//div[@aria-sort='descending']")
	private Element descendingSortSign;

	public String getColumnName(){
		return columnName.getText();
	}
	
	public String getSortingType(){
		String typeOfSort;
		typeOfSort = this.getAttribute("aria-sort").toLowerCase();
		switch(this.getAttribute("aria-sort")) {
			case "none":
				typeOfSort = "None Sorting";
			break;
			case "ascending":
				typeOfSort = "Sort Ascending";
			break;
			case "descending":
				typeOfSort = "Sort Descending";
			break;
		}
//		if(ascendingSortSign.isDisplayed())
//			return "Sort Ascending";
//		else if(descendingSortSign.isDisplayed())
//			return "Sort Descending";
//		else
//			return "None Sorting";
		return typeOfSort;
	}
	
	public boolean isSorted(){
		return ascendingSortSign.isDisplayed() || descendingSortSign.isDisplayed();
	}

//	public String getStateSort() {
//		return this.getAttribute("aria-sort");
//	}
	
	public void click(){
		columnName.clickCenter();
	}
}
