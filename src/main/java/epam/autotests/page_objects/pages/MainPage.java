package epam.autotests.page_objects.pages;

import com.epam.jdi.uitests.web.selenium.driver.SauceLabRunner;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.complex.Elements;
import com.epam.jdi.uitests.web.selenium.elements.complex.Menu;
import com.epam.jdi.uitests.web.selenium.elements.complex.TextList;
import com.epam.jdi.uitests.web.selenium.elements.complex.table.Table;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.web.matcher.junit.Assert;
import epam.autotests.page_objects.enums.MainPage_ViewType;
import epam.autotests.page_objects.enums.TrackType;
import epam.autotests.page_objects.panels.DatasetsPanel;
import epam.autotests.page_objects.sections.ProjectTile;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static epam.autotests.page_objects.enums.MainPage_ViewType.TABLE;
import static epam.autotests.page_objects.enums.MainPage_ViewType.TILES;
import static epam.autotests.page_objects.site.NGB_Site.*;
import static epam.autotests.utils.TestBase.getLocalDateFromString;

public class MainPage extends WebPage{
	@FindBy(css = ".u_reset-font-size a") // use for local
    private Button toggleViewButton;

	@FindBy(css = ".logo")
	private Button ngbLogo;

	@FindBy(xpath = "//ngb-reference-filter/button/span")
	private Menu<Enum> referenceTabs;

	@FindBy(xpath = "//div[contains(@class,'md-open-menu-container')]//md-menu-item[not(@class)]//span")
	private Menu<Enum> menuSortTiles;
	
	@FindBy(xpath = "//ngb-sort-projects//button")
	private Button sortTilesBtn;
	
	@FindBy(xpath = "//ngb-project-tiles/div/div")
	public Elements<ProjectTile> projectsTiles;

	@FindBy(xpath = "//ngb-data-sets")
	public DatasetsPanel datasetsPanel;

	@FindBy(css = ".md-button-underlined span")
	private TextList menuItems;

    @FindBy(css=".create-project-btn")
	private Button createProjectBtn;

	@FindBy(xpath = "//table")
	private Table projectsTable;
	
	@FindBy(xpath = "//ngb-project-tiles/div/div")
	private TextList<?> listOfProjects;
	
	@FindBy(xpath = "//md-tooltip/div/div")
	private TextList<?> tooltip;

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

	public void checkAllReferences(MainPage_ViewType modeView) {
		for (int i = 1; i <= menuItems.getTextList().size(); i++) {
			System.out.println("  ...Select > "+menuItems.getText(i-1));
			selectReferenceTab(i);
			
			switch (modeView) {
				case TABLE:
					testSortingTable(true);
					testSortingTable(false);
					break;
				case TILES:
					testSortTiles("Last Created");
					testSortTiles("Last Opend By Me");
					break;
				default:
					throw new IllegalArgumentException("invalid locator");
			}
		}
	}

    public void switchView(String typeOfview){
		if (toggleViewButton.getAttribute("ui-sref").equals("." + typeOfview))
			toggleViewButton.click();
	}

	public void clickLogo() { 
		ngbLogo.click(); 
	}

	public void testSortTiles(String item) {
		sortTilesBtn.click();
		menuSortTiles.select(item);

		if (item.equals("Last Created")) {
			List<String> sortedByCreated = new ArrayList<String>();
			int n = listOfProjects.count();
			String ss;
			String[] parts;

			for (int i = 0; i < n; i++) {
				sortedByCreated.add(projectsTiles.get(i).getCreationDate());
			}
		List<String> expectedTilesValues = new ArrayList<>(sortedByCreated);
		Collections.sort(expectedTilesValues, (String a, String b) -> a.toUpperCase().compareTo(b.toUpperCase()));
		if (sortedByCreated.equals(expectedTilesValues)) {
			System.out.println("Tiles sorting last Created> OK");
		} else {
			for (int i = 0; i < n; i++) {
				System.out.println("ListOfProjects[" + i + "](" + sortedByCreated.get(i) + "   after sort[" + i + "]=" + sortedByCreated.get(i));
			    }
		    }
	    }
	}

	public boolean isSortedColumn(String ColName) {
		List<String> sortedCol = projectsTable.columnValue(projectsTable.headers().indexOf(ColName) + 1);
		List<String> expectedColumnsValues = new ArrayList<>(sortedCol);
		Collections.sort(expectedColumnsValues, (String a, String b) -> a.toUpperCase().compareTo(b.toUpperCase()));
		return sortedCol.equals(expectedColumnsValues);
	}

	public void testSortingTable(boolean ascending) {
		SoftAssert soft_assert = new SoftAssert();
		String[] headers = {"Recent projects", "Reference", "Created"};
		for (int i=0; i<headers.length; i++) {
			sortTableView(headers[i], ascending);
			soft_assert.assertTrue(isSortedColumn(headers[i]), "\nColumn "+headers[i]+" sorted incorrectly");
		}
		soft_assert.assertAll();
	}

	private String getHeaderColumnName(String colName){
		List<String> columns = projectsTable.headers();
		for (int i = 0; i < columns.size(); i++) {
			if(columns.get(i).startsWith(colName))
				return columns.get(i);
		}
		return "";
	}
	
	public void sortTableView(String columnName, boolean ascending){
		if(!isSortedColumn(columnName))
			projectsTable.header(getHeaderColumnName(columnName)).clickCenter();
	}

    public void createNewProject() {
		createProjectBtn.click();
		projectCreationWindow.inputProjectName("XYZ_lumpy");
		int n = projectCreationWindow.refSize();
		for(int i = 0; i < n; i++) {
			System.out.println("select CheckBox["+i+"]   "+projectCreationWindow.getRefName(i));
			projectCreationWindow.clickRefByIndx(i); // GRCh38
			projectCreationWindow.selectTab("TRACKS");
			projectCreationWindow.workWithSelectedTab();
			projectCreationWindow.selectTab("GENERAL");
			projectCreationWindow.workWithSelectedTab();
		}
		projectCreationWindow.cancel();
	}

	public int countOfTiles() {
		return projectsTiles.size();
	}


	public MainPage_ViewType getActiveViewType() {
		if(toggleViewButton.getAttribute("ui-sref").equals("." + TILES.value))
			return TABLE;
		if(toggleViewButton.getAttribute("ui-sref").equals("." + TABLE.value))
			return TILES;
		else return null;
	}


	public boolean checkViewType(MainPage_ViewType viewType) {
		return getActiveViewType().equals(viewType);
	}
	
	public void selectReferenceTab(int index){
		referenceTabs.select(index);
	}


	public boolean isCreateProjectBtnPresent() {
		return createProjectBtn.isDisplayed();
	}


	public void checkAllTiles() {
		for (int i = 0; i < projectsTiles.size(); i++) {
			System.out.println("#"+(i+1)+" tile");
			projectsTiles.get(i).checkVisibilityOfAllElements();
		}
	}


	public boolean checkReferenceTabs() {
		return referenceTabs.getNames().size()>0 ? true : false;
	}
	
	public void checkTable(){
		SoftAssert soft_assert = new SoftAssert();
		String colName = "";
		for (int i = 0; i < projectsTable.rows().count(); i++) {
			for (int j = 0; j < projectsTable.headers().size(); j++) {
				colName = projectsTable.headers().get(j);
				if(!colName.isEmpty())
					soft_assert.assertFalse(projectsTable.cell(colName, i+1).getText().isEmpty(), "\nCell["+colName+"|"+i+"] has no value");
				else {
					soft_assert.assertTrue(isElementPresent(projectsTable.cell(j+1, i+1).get().get(By.xpath(".//ngb-edit-project-button/button"))), "There is no edit button in "+i+" row.");
					soft_assert.assertTrue(isElementPresent(projectsTable.cell(j+1, i+1).get().get(By.xpath(".//ngb-delete-project-button/button"))), "There is no delete button in "+i+" row.");
				}
			}
		}
		soft_assert.assertAll();
	}

	public void selectReferenceTab(String string) {
		List<String> tabs = referenceTabs.getNames();
		for (int i = 0; i < tabs.size(); i++) {
			if(tabs.get(i).startsWith(string)){
				selectReferenceTab(i+1);
				break;
			}
		}
	}

	public int countOfProjects() {
		return Integer.parseInt(referenceTabs.getNames().get(0).replaceAll("[^0-9]", ""));
	}

	public int getDifferenceBetweenDates(String date1, String date2){
		LocalDate dt1, dt2;
		dt1 = getLocalDateFromString(date1, "-");
		dt2 = getLocalDateFromString(date2, "-");
		
		return Days.daysBetween(dt1, dt2).getDays();
	}

	public void checkSearchResult() {
		int counter = 0;
		String searchFieldValue = header.getSearchFieldText();
		List<String> extraProjects = new ArrayList<>();
		switch(getActiveViewType()){
			case TILES : {
				for (int i = 0; i < projectsTiles.size(); i++) {
					if(!projectsTiles.get(i).getTitle().toLowerCase().contains(searchFieldValue.toLowerCase())){
						counter++;
						extraProjects.add(projectsTiles.get(i).getTitle());
					}
				}
				break;
			}
			case TABLE : {
				for (int i = 1; i <= projectsTable.rows().count(); i++) {
					if(!projectsTable.cell("Recent projects", i).getText().toLowerCase().contains(searchFieldValue.toLowerCase())){
						counter++;
						extraProjects.add(projectsTable.cell("Recent projects", i).getText());
					}
				}
				break;
			}
		}
		Assert.isTrue(counter==0, "There are projects which don't contain value from search field in the search result.: \n" + extraProjects.toString());
	}


	public void createNewProject(String projectName, String reference, TrackType trackType, String...tracks) {
		createProjectBtn.clickCenter();
		projectCreationWindow.waitDisplayed();
		projectCreationWindow.fillGeneralTab(projectName, reference);
		projectCreationWindow.fillTracksTab(trackType, tracks);
	}
	
	public void createNewProjectWithRegistrationOfNewTrack(String projectName, String reference, String location, String filePath, String indexFilePath){
		
	}

	public void selectProject(int projectNumber) {
		switch (getActiveViewType()) {
			case TILES: {
				projectsTiles.get(projectNumber-1).clickCenter();
				break;
			}
			case TABLE: {
				projectsTable.cell(1, projectNumber).click();
				break;
			}
		}
	}

	public void openProject(String projectName){
		System.out.println("Open Project: "+projectName);
		datasetsPanel.select(projectName);
	}
}
