package epam.autotests.page_objects.sections;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.CheckBox;
import com.epam.jdi.uitests.web.selenium.elements.common.TextField;
import com.epam.jdi.uitests.web.selenium.elements.complex.Elements;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;

import epam.autotests.page_objects.enums.TrackType;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Vsevolod_Adrianov on 8/19/2016.
 */
public class ProjectCreationModalWindow extends Section {

	private String currentTabName;

	@FindBy(css = "md-tabs-canvas md-tab-item")
	private Elements<Button> lTab; // TODO: change to Tab element

	/*@FindBy(xpath = ".//project-tracks")
	private Tracks tracksTab;*/

	@FindBy(css = "project-general input")
	private TextField projectName;
	//
	@FindBy(xpath = ".//md-checkbox")
	private Elements<CheckBox> lRef;
	//
	@FindBy(css = "md-dialog-actions .md-button[aria-label='Save']")
	private Button bSave;
	//
	@FindBy(css = "md-dialog-actions .md-button[aria-label='Cancel']")
	private Button bCancel;
	//
	@FindBy(css = "md-toolbar .md-icon-button")
	private Button bClose;

	public void save() {
		bSave.click();
	}

	public void close() {
		bClose.click();
	}

	public void cancel() {
		bCancel.click();
	}

	public int refSize() {
		return lRef.size();
	}

	//
	public void inputProjectName(String Name) {
		// ProjectName.clickCenter();
		projectName.setValue(Name);
	}

	//
	public void selectTab(String tabName) {
		lTab.get(tabName).click();
		currentTabName = tabName;
	}

	//
	public String currentTab() {
		return currentTabName;
	}

	public String getRefName(int ix) {
		return lRef.get(ix).getAttribute("aria-label");
	}

	public void clickReference(String refName) {
		lRef.get(refName).click();
	}

	public void clickRefByIndx(int index) {
		lRef.get(index).click();
	}

	//
	public void workWithSelectedTab() {
		System.out.println("Do something On Selected Tab  <" + currentTabName + ">");
		// TracksTab.
	}

	public void fillGeneralTab(String projectName, String reference) {
		this.projectName.sendKeys(projectName);
		lRef.get(reference).check();
	}

	public void fillTracksTab(TrackType trackType, String[] tracks) {
		
	}
}
