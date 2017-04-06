package epam.autotests.page_objects.pages;

import com.epam.commons.Timer;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.CheckBox;
import com.epam.jdi.uitests.web.selenium.elements.common.TextField;
import com.epam.jdi.uitests.web.selenium.elements.complex.CheckList;
import com.epam.jdi.uitests.web.selenium.elements.complex.Elements;
import com.epam.jdi.uitests.web.selenium.elements.complex.TextList;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.web.matcher.testng.Assert;
import epam.autotests.page_objects.enums.Views;
import epam.autotests.page_objects.general.Panel;
import epam.autotests.page_objects.panels.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static epam.autotests.page_objects.enums.ProjectPagePreconditions.OPEN_DATASETS_PANEL;
import static epam.autotests.page_objects.enums.ProjectPagePreconditions.OPEN_VARIANTS_PANEL;
import static epam.autotests.page_objects.enums.Views.*;
import static epam.autotests.page_objects.site.NGB_Site.*;
import static org.testng.Assert.assertEquals;

/**
 * Created by Vsevolod_Adrianov on 7/8/2016.
 * 
 *<p/>
 * <b>Refactored</b> by Aksenov Oleg in September-October 2016
 */

public class ProjectPage extends WebPage {

    @FindBy(xpath = ".//ngb-bookmark/md-menu/button")//.//ngb-tracks-view//ngb-bookmark/button")
    private Button sessionBtn;

    @FindBy(xpath = ".//md-menu-content/input")//.//ngb-tracks-view//ngb-bookmark/input")
    private TextField sessionTextField;

    @FindBy(css = "[md-svg-icon='md-close']")
    private Element chromosomeClose;

    @FindBy(css = ".ngb-tool-menu md-menu-item")
    private Elements<ViewsItemMenu> menuItem;

    @FindBy(xpath = "//div[@class='lm_header']/ul/li/span")
    private TextList<?> listTabs;
    
    @FindBy(xpath = "//div[@class='lm_header']/ul[@class='lm_tabs']/li")
    private Elements<Element> panelsTabs;
    
    @FindBy(xpath = "//ngb-variants-table-panel")
    public VariantsPanel variantsPanel;
    
//    @FindBy(xpath = "//ngb-filter-panel")
    public FiltersPanel filterPanel;
    
	@FindBy(xpath = "//ngb-browser")
	public BrowserPanel browserPanel;

//	@FindBy(xpath = "//ngb-data-sets")
//	public DatasetsPanel datasetsPanel;

//	@FindBy(xpath = "//ngb-track-list")
	@FindBy(xpath = "//ngb-track")
    public TrackListPanel trackListPanel;
    
//    @FindBy(xpath = "//ul//li[@title='Bookmarks']//ancestor::div[@class='lm_header']/following-sibling::div//ngb-bookmarks-table")
    @FindBy(xpath = "//ngb-bookmarks-table")
    public SessionsPanel sessionsPanel;
    
    @FindBy(xpath = "//ngb-molecular-viewer")
    public MolecularViewerPanel molViewerPanel;
    
    @FindBy(xpath = "//div/md-menu-content//md-checkbox")
    public CheckList<?> columns;
    
    @FindBy(xpath = "//div/md-menu-content//md-checkbox")
    public Elements<CheckBox> chbxColumns;

    @FindBy(xpath = "//ngb-close-all-tracks//ng-md-icon")
    private Element closeAllTracksButton;

    @FindBy(xpath = ".//md-dialog-actions/button[2]")
    public Button popUpOKButton;

    @FindBy(xpath = ".//md-dialog-actions/button[1]")
    public Button popUpCANCELButton;

//    @FindBy(xpath = ".//ngb-track//div[@class='ng-hide']")
//    public Element loaderIndicator;

    public void closeAllTracks(){
        if (closeAllTracksButton.isDisplayed()) {
            closeAllTracksButton.clickCenter();
            popUpOKButton.click();
            isInState(OPEN_DATASETS_PANEL);
            assertEquals(true,mainPage.datasetsPanel.checkBoxIsSelected(),"dataset or file stil selected");
        }

    }
    
    public Panel getPanel(Views panelName){
    	switch (panelName) {
			case BROWSER: return browserPanel;
			case VARIANTS: return variantsPanel;
			case FILTER: return filterPanel;
			case TRACK_LIST: return trackListPanel;
			case SESSIONS: return sessionsPanel;
			case MOLECULAR_VIEWER: return molViewerPanel;
			default: return null;
		}
    }
    public void openFilter()	{
        isInState(OPEN_VARIANTS_PANEL);
        filterPanel.openFilter();
    }



    public void setBookmark(String bkmrk) {
        sessionBtn.clickCenter();
        sessionTextField.sendKeys(bkmrk + Keys.ENTER);
    }
    public void addBookmark(String bookmarkName) {
        sessionBtn.click();
        Timer.sleep(1000);
        sessionTextField.focus(); //.clickCenter();
        sessionTextField.sendKeys(bookmarkName + Keys.ENTER);
    }

    public void checkViewAfterAddition() {
        Timer.sleep(1000);
        com.epam.web.matcher.junit.Assert.isTrue(sessionBtn.getAttribute("class").contains("success-save"), "Bookmark sign isn't green");
        com.epam.web.matcher.junit.Assert.isFalse(sessionTextField.isDisplayed(), "Text field for bookmark's name is still displayed");
    }

  /*  public void onViewPanel(String Item) {
        if(!menuItem.get(Item).isChecked()) {
            menuItem.get(Item).selectView();
        }
        else {
            // Click out of menu for close it
            selectTab(Item);
        }
    }*/

    public void closePanel(String Item) {
        Timer.sleep(500);
        if(menuItem.get(Item).isChecked()) {
            menuItem.get(Item).selectView();
        }
    }
/*
    public String getTabTitle(int index) {
        if (index < viewsPanel.length)
            return viewsPanel[index].toUpperCase();
        else return "";
    }
*/
    /*
    public void selectTab(String TabTitle) {
        int N = listTabs.size();
//        System.out.println("  ...........SelectTab");
        for (int i = 0; i < N; i++) {
            if (TabTitle.equals(listTabs.get(i).getTitle())) {
                System.out.println("Try Select Tab     <<<  " + listTabs.get(i).getTitle()+" ["+ i+"]  >>>");
                listTabs.get(i).clickCenter();
                return;
            }
        }
    }
*/
    //
  /*  public void closeAllTabs() {
        System.out.println("  ...........Close All Panels");
        int N = listTabs.size();
        System.out.println("  Views Panels on page = " + N);
        for (int i = 0; i < N; i++) {
            //   System.out.println("i="+i+" <------------------>");
                System.out.println("Try Close Tab      <<<  " + listTabs.get(0).getTitle()+" ["+ i+"]  >>>");
            Timer.sleep(1000);
            listTabs.get(0).clickCenter();
//??            listTabs.get(0).CloseClick();
            listTabs.get(0).closeClick();
//            getDriver().findElement(By.cssSelector(".lm_close")).click();
            // Maximise
//            listTabs.get(i).Minimize();
            // Minimise
//            Timer.sleep(1000);
//            listTabs.get(i).Minimize();
//            Timer.sleep(1000);
//??            listTabs.get(i).CloseTab();
        }
        
        System.out.println("Try Close Tab(0) <"+listTabs.get(0).getText()+">");
        Timer.sleep(1000);
        listTabs.get(0).clickCenter();
        
        //return lmTabs.size();
    }*/

    /*
    public void ViewAllPanels() {
        int N = viewsPanel.length - 1;
        System.out.println("<<<<<<<<<<    ViewAllPanels  "+N+"  >>>>>>>>>>");
        for (int i = 0; i < N; i++) {
            onViewPanel(viewsPanel[i]);
        }
    }
*/
    
// вынести панель в отдельное окно
    public void PopUp() {
        // .lm_controls
        // .lm_popout
        //	this.getWebElement().findElement(By.cssSelector(".lm_controls")).click();
//        lmPanels
//        getWebElement().findElement(By.cssSelector(".lm_popout")).click();
    }

    /*
    public void SelectVisiblePanel(String Panel) {
        System.out.println(Panel);
        Panel.toUpperCase();
        System.out.println(Panel);
        lmPanels.get(Panel).click();
    }
    */
/*
    public void MaxMinimise() {
        int N = lmPanels.size();
        int j = 0;
        System.out.println("Visible Panels =" + N);
        for (int i = 0; i < N; i++) {
            j = i;
            WebElement tab = lmPanels.get(j).getWebElement().findElement(By.cssSelector(".lm_tab"));
//           WebElement popout = lmPanels.get(i).getWebElement().findElement(By.cssSelector(".lm_popout"));
//           WebElement maximise = lmPanels.get(i).getWebElement().findElement(By.cssSelector(".lm_maximise"));
//??           WebElement PClose = lmPanels.get(0).getWebElement().findElement(By.cssSelector(".lm_close"));
            String ss = tab.getAttribute("innerText");
            System.out.println("Maximise ........<" + ss + ">");
            lmPanels.get(j).getWebElement().findElement(By.cssSelector(".lm_maximise")).click();
            Timer.sleep(1000);
            System.out.println("Minimise it");
            lmPanels.get(0).getWebElement().findElement(By.cssSelector(".lm_maximise")).click();
//            lmPanels.get(0).getWebElement().findElement(By.cssSelector(".lm_popout")).click();
//            System.out.println("title = <"+ss+">  Try Close it");
//            lmPanels.get(0).getWebElement().findElement(By.cssSelector(".lm_close")).click();
        }
//        Restore_Default_Layout()
    }

    public void ClosePanels() {
        int N = lmPanels.size();
        int j = 0;
        System.out.println("Visible Panels =" + N);
        for (int i = 0; i < N; i++) {
            WebElement tab = lmPanels.get(j).getWebElement().findElement(By.cssSelector(".lm_tab"));

//           WebElement popout = lmPanels.get(i).getWebElement().findElement(By.cssSelector(".lm_popout"));
//           WebElement maximise = lmPanels.get(i).getWebElement().findElement(By.cssSelector(".lm_maximise"));
//??           WebElement PClose = lmPanels.get(0).getWebElement().findElement(By.cssSelector(".lm_close"));
            String ss = tab.getAttribute("innerText");
//            System.out.println("Panel = <"+ss+">  Try maximise it");
//            lmPanels.get(j).getWebElement().findElement(By.cssSelector(".lm_maximise")).click();
//            Timer.sleep(1000);
//            System.out.println("Try minimise it");
//            lmPanels.get(0).getWebElement().findElement(By.cssSelector(".lm_maximise")).click();
            System.out.println("Try Close .....<" + ss + ">");
            lmPanels.get(0).getWebElement().findElement(By.cssSelector(".lm_close")).click();
        }
//        Restore_Default_Layout()
    }
*/
   /* public void restore_Default_Layout() {
        Timer.sleep(1000);
//   Restore all views
        onViewPanel(viewsPanel[viewsPanel.length-1]);
    }*/

    /*
        @FindBy(css=".jqx-grid-cell")
        private Elements<Element> vRows;
    */
    public void ViewTableHeader() {
        /*
        int N = columnButton.size();
        String[] ColNames = new String[N];
        System.out.println(N+" columns");
        for(int i=0; i<N; i++) {
//            columnButton.get(i).getText()
            ColNames[i] = columnButton.get(i).getText();
//          System.out.print("<"+columnButton.get(i).getText()+"> ");
        }
        for(int i=0; i<N; i++) {
            System.out.print(" <" + ColNames[i] + ">");
            // try sort out
            columnButton.get(i).click();
        }
        System.out.println();
        */
        System.out.println("...............................>ViewTableHeader");
    }
/*
    public void SortOut(String ColName) {
        int N = columnButton.size();
        System.out.println("Sort out column: "+ColName);
        for(int i=0; i<N; i++) {
//            columnButton.get(i).getText()
            if(ColName.equals(columnButton.get(i).getText())) {
//          System.out.print("<"+columnButton.get(i).getText()+"> ");
                columnButton.get(i).click();
                System.out.println("Sort out column: "+ColName+".... Success");
                return;
            }
        }
    }
    */
//
    /*
    @FindBy(css=".lm_item_container[style*='block'] [role='row']")
    private Elements<Element> vRows;
    */
// try find Tab by name:
// WebElement userName = driver.findElement(By.cssSelector(".lm_tab[title='+ss+']"));
//
//??        System.out.println("...............................>ScanRow");
//??     }

    public void onMainPage() {
        header.goToMainPage();
    }
//
    /*public void scanDensityDiagram() {
//??        Restore_Default_Layout();
//        int N = DensityDiagram.getNumberChromosome();
//??        OnViewPanel(ViewsPanel[0]);  // "Variants"
        selectTab(getTabTitle(2)); // Browser
        int N = browserPanel.CountsChromosomes();
//--            System.out.println("N("+N+ ")= tBrowser.CountsChromosomes();");
        int nWeight, genRow, RR;
        String dd;
        for(int i=0; i<N; i++) {
//            nWeight=DensityDiagram.getWeight(i);
            nWeight=browserPanel.Weight(i);
            System.out.println("nWeight("+i+ ")= "+nWeight+";");
//            DensityDiagram.selectChromosome(i);
//***            System.out.println("Wait while density diagram on view = 5 sec");
//***            Timer.sleep(5000);
            browserPanel.OnViewOfChromosome(i);
//***            System.out.println("Wait <ChromosomeClose> Button= 5 sec");
            chromosomeClose.waitDisplayed();
//            genRow=gridPanel.getNumRows();  // get number of rows in Grid
            genRow=variantsPanel.getNumRows();  // get number of rows in Grid
            dd = browserPanel.getChromosomeCoordinates();
//            System.out.println("<Diapason in Browser["+getTabTitle(2)+"] >");
            System.out.println("<Diapason in Browser["+dd+"] >");
            RR=variantsPanel.getNumRows();
            for(int r=0; r<RR; r++) {
//??                gridPanel.Cell(r,2).click();
                variantsPanel.ClickCell(r,2);
//??                assertTrue(tBrowser.IsIn(gridPanel.Cell(r, 3).getText()));
                assertTrue(browserPanel.IsIn(variantsPanel.ValueCell(r, 3)));
//++                VariantsTab.VisualizerVCF(r);
            }
//
            assertEquals(nWeight, genRow);
//??            softAssert.assertEquals(nWeight, genRow);
            chromosomeClose.clickCenter();
            System.out.println("<Wait closing current chromosome index["+i+"] = "+browserPanel.NameOfChromosome(i)+">");
            Timer.sleep(500);
            selectTab(getTabTitle(2)); // Browser
//??        try .sendKeys(Keys.ESCAPE);
        }
    }
//
    public void TryExperiment() {
        System.out.println("...............................>TryExperiment");
        closeAllTabs();
//??        ViewAllPanels();
//++        MaxMinimise();
//++        ClosePanels();
        restore_Default_Layout();
//		    projectPage.PanelsOnPage();
        
		String ss;
        for(int i=0; i<5; i++) {
//			ss = projectPage.getTabTitle(i);
            SelectTab(getTabTitle(i));
//			System.out.println("Try Select Tab("+i+") <"+ss+">");
//			projectPage.SelectTab(ss);
        }
        
//  Select Variants
        selectTab(getTabTitle(0)); // Variants
//??        ViewTableHeader();
//??        gridPanel.ViewTableHeader();
//++        VariantsTab.ScanVCFPanels();
//  Select Bookmarks
//++        SelectTab(getTabTitle(4));
//??        gridPanel.ViewTableHeader();
//??        gridPanel.ScanRow();
    }
//*/
    public void pressChromosome(int N) {
        if(N<2) {
            /*header.chromosomecontrol.get(N).click();
            WebElement inChr = header.chromosomecontrol.get(0).getDriver().findElement(By.cssSelector(".ng-pristine"));
            inChr.sendKeys("X");*/
        }
    }
//
/*??
    public void TestSortVCF() {
        gridPanel.VCFTableSort();
    }
*/
    public void sortingByCol(String ColName) {
        variantsPanel.sortColumn(ColName);
    }
//
    public void chromosome() {
        System.out.println("        > Chromosome Diagram <");
//        int N= DensityDiagram.getNumberChromosome();
        int N=browserPanel.countsChromosomes();
            for(int i=0; i<N; i++) {
//            System.out.println("Chromosome["+i+"]="+DensityDiagram.NameChromosome(i)+" weight="+DensityDiagram.getWeight(i));
              System.out.println("Chromosome["+i+"]="+browserPanel.nameOfChromosome(i)+" weight="+browserPanel.weight(i));
        }
        System.out.println("        > Variants types <");
        int M=browserPanel.countsOfTypes();
        for(int i=0; i<M; i++) {
            System.out.println("Name of Type["+i+"]="+browserPanel.nameOfType(i)+" weight="+browserPanel.weightOfType(i));
        }
    }


	public void checkDefaultView() {
		SoftAssert soft_assert = new SoftAssert();
		List<String> defaultPanelsSet = new ArrayList<String>(
			    Arrays.asList(BROWSER.value.toUpperCase(), VARIANTS.value.toUpperCase(), TRACK_LIST.value.toUpperCase()));
		soft_assert.assertTrue(header.checkView(this), "Some elements in Header are displayed or not displayed");
		soft_assert.assertTrue(compareTwoStringLists(getNamesOfOpenedPanels(), defaultPanelsSet), "Set of default opened panels is not as expected");
		soft_assert.assertAll();
	}

	private List<String> getNamesOfOpenedPanels() {
		List<String> actualPanelsSet = new ArrayList<>();
		for (int i = 0; i < listTabs.count(); i++) {
			actualPanelsSet.add(listTabs.getText(i));
		}
		return actualPanelsSet;
	}
	
	private Element getPanelTab(Views panelName){
		String browser = "BROWSER";
		if(browser.equals(panelName.value.toUpperCase())) {
			for (Element panelTab : panelsTabs) {
				String ss1 = panelTab.get(By.cssSelector(".lm_title .ng-hide")).getAttribute("textContent").trim();
				System.out.println("Tab name> <"+ss1+"> ["+panelName.value.toUpperCase()+"]");
				if (panelTab.get(By.cssSelector(".lm_title .ng-hide")).getAttribute("textContent").trim().equals(panelName.value))
					return panelTab;
			}
			return null;
		}
			for (Element panelTab : panelsTabs) {
				String ss1 = panelTab.get(By.xpath("./span")).getText();
				System.out.println("Tab name> <"+ss1+"> ["+panelName.value.toUpperCase()+"]");
//			String locator;
//			if (panelTab.get(By.cssSelector(".lm_title")).getText().equals(panelName.value.toUpperCase()) || panelTab.get(By.cssSelector(".lm_title .ng-hide")).getAttribute("textContent").trim().equals(panelName.value))
				if (panelTab.get(By.xpath("./span")).getText().equals(panelName.value.toUpperCase()))
					return panelTab;
			}
		return null;
	}
	
	public boolean isPanelActive(Views panelName){
		/*if(!getNamesOfOpenedPanels().contains(panelName.value.toUpperCase()))
			return false;*/
		Element tab = getPanelTab(panelName);
		if(tab == null)
			return false;
		if(tab.getAttribute("class").contains("lm_active"))
			return true;
		return false;
	}

	public void openPanel(Views panelName) {
		if(!getNamesOfOpenedPanels().contains(panelName.value.toUpperCase())){
			header.selectView(panelName);
		} else if(!isPanelActive(panelName)){
			getPanelTab(panelName).clickCenter();
		}
	}

	public void checkViewingTrackList() {
//		parametersChlst
//		Assert.isTrue(browserPanel.getCountOfOpenedTracks() == trackListPanel.getCountOfSelectedFilters(),
		Assert.isTrue(browserPanel.getCountOfOpenedTracks()-2 == filterPanel.getSizeActiveVCFFile(),
				"Number of displayed track and number of selected filters is not the same");
//		Assert.isTrue(compareTwoStringLists(browserPanel.getTracksTitle(), trackListPanel.getListOfSelectedFilters()), "Displayed tracks and selected filters don't correspond with each other.");
		Assert.isTrue(compareTwoStringLists(browserPanel.getTracksTitle("VCF"), filterPanel.getListOfSelectedFilters()), "Displayed tracks and selected filters don't correspond with each other.");
	}

	public void checkLastTrack(String trackTitle) {
		List<String> tracksList = browserPanel.getTracksTitle("VCF");
		Assert.isTrue(tracksList.get(tracksList.size()-1).equals(trackTitle), "Wrong last track");
	}

	public void checkingViewOfBookmarksAndBrowser(String bookmarkName, String chromosome) {
		SoftAssert soft_assert = new SoftAssert();
		
		String[] bmParams = sessionsPanel.bookmarksTable.findRow("Name", bookmarkName).collectRowData2(1, 2, 3);
		String[] tabParams = new String[3];
		tabParams[0] = browserPanel.getTabTitle().replaceAll("(CHR:)|(\\n\\d+)","");
		tabParams[1] = browserPanel.CoordMenu().getText().replaceAll("(\\w+:\\s)|(\\s-\\s\\d+)", "");
		tabParams[2] = browserPanel.CoordMenu().getText().replaceAll("\\d+\\:\\s\\d+\\s-\\s", "");
		
		soft_assert.assertTrue(bmParams[0].equals(tabParams[0]), "Wrong chromosome");
		soft_assert.assertTrue(bmParams[1].equals(tabParams[1]), "Wrong left coordinate");
		soft_assert.assertTrue(bmParams[2].equals(tabParams[2]), "Wrong right coordinate");
		soft_assert.assertAll();
	}
}
