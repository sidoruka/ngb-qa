package epam.autotests.page_objects.general;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.complex.CheckList;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;


public class SetOfProperties extends Section{

	@FindBy(xpath = ".//button")
	public Element groupHeader;
	
	@FindBy(xpath = ".//button/following-sibling::div//md-checkbox")
    public CheckList<?> parametersChlst;

	public String getGroupName(){
		return groupHeader.getAttribute("aria-label");
	}
	
	public boolean isCheckListWithoutSelection(){
		List<String> options = parametersChlst.getOptions();
		for (String chLstItem : options) {
			if(parametersChlst.isSelected(chLstItem))
				return false;
		}
		return true;
	}

	public void checkOptions(String...parameters){
		parametersChlst.check(parameters);
	}
	
	public void unCheckAllOptions(){
		parametersChlst.uncheckAll();
	}
	
	public boolean isOptionSelected(String option){
		return parametersChlst.isSelected(option);
	}

	public void checkAll() {
		parametersChlst.checkAll();
	}

	public int getNumberChecks() {
		List<String> options = parametersChlst.getOptions();
		int N = 0;
		for (String chLstItem : options) N++;
		return N;
	}

	public List<String> getListOfSelectedFilters(){
		List<String> options = parametersChlst.getOptions();
//		for (String chLstItem : options) {
//			options.add(chLstItem);
//		}

		return options;
	}
}
