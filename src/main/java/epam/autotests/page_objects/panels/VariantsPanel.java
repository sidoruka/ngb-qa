package epam.autotests.page_objects.panels;

import com.epam.commons.Timer;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.CheckBox;
import com.epam.web.matcher.junit.Assert;

import epam.autotests.page_objects.enums.ExternalDataFiles;
import epam.autotests.page_objects.enums.VarTableColumns;
import epam.autotests.page_objects.general.Panel;
import epam.autotests.page_objects.general.PropertyVCF;
import epam.autotests.page_objects.sections.CustomTable;
import epam.autotests.page_objects.sections.GridPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.allure.annotations.Step;

import static epam.autotests.page_objects.site.NGB_Site.*;
import static epam.autotests.utils.TestBase.intListToIntArray;
import static epam.autotests.utils.TestBase.*;
import static epam.autotests.utils.MyFileReader.*;

/**
 * Created by Vsevolod_Adrianov on 8/8/2016.
 * <br>
 * Refactored by Aksenov Oleg. October, 2016
 */

public class VariantsPanel extends Panel {
	@FindBy(css = ".md-transition-in")
	private PropertyVCF VCFPanel;

	@FindBy(xpath = ".//ngb-columns//button")
	private Button addColumns;

	//	@FindBy(xpath =".//div[@class='jqx-grid jqx-reset jqx-rc-all jqx-widget jqx-widget-content jqx-disableselect']")
	@FindBy(xpath =".//div[@role='grid']")
	public GridPanel gridPanel;

	//	@FindBy(xpath = ".//div[@class = 'jqx-clear jqx-overflow-hidden jqx-position-absolute jqx-border-reset jqx-background-reset jqx-reset jqx-disableselect']")
	@FindBy(css = ".ui-grid-contents-wrapper>.ui-grid-render-container-body")
	public CustomTable variantsTable;

	public int getNumRows() {
		return gridPanel.getNumRows();
	}

	public void clickCell(int r, int c) {
		gridPanel.cell(r, c).click();
	}

	public String valueCell(int r, int c) {
		return gridPanel.cell(r, c).getText();
	}

	@Step
	public void sortColumn(String ColName) {
		gridPanel.sorting(ColName);
	}

	@Step
	public void visualizerVCF(int row) {
		gridPanel.cell(row, 4).click(); // try popup info window
		// sendKeys(Keys.ENTER);
		VCFPanel.SelectGeneFile(2); // select GRCh38.83.sorted.gtf
		Timer.sleep(2000);
		VCFPanel.WaitPict();
		WebElement pWnd = getDriver().findElement(By.cssSelector(".md-transition-in"));
		pWnd.sendKeys(Keys.ESCAPE);
	}

	public void scanVCFPanels() {
		int N = gridPanel.getNumRows();
		int M = gridPanel.getNumCols();
		String[] ss = new String[M];
		for (int i = 0; i < N; i = i + M) {
			gridPanel.cell(i, 2).click();
			visualizerVCF(i);
		}
	}
	@Step
	public void checkSetOfColumns(String...columns){
		Assert.isTrue(compareTwoStringLists(gridPanel.columnsList.getTextList(), Arrays.asList(columns)), "Wrong set of columns");
	}

	@Step
	public void checkDataWithFile(ExternalDataFiles fileName) {
		List<String[]> rowData = variantsTable.collectAllRowData(0,1,3,4);
		List<String[]> dataFromFile = readFile(loadExternalFile(fileName.value), "SVTYPE", "CHROM", "POS", "ID");
		SoftAssert soft_assert = new SoftAssert();
		soft_assert.assertTrue(compareListOfArrays(rowData, dataFromFile), "Two lists of arrays are equal");
		for (int i = 0; i < rowData.size(); i++) {
			soft_assert.assertTrue(compareStringArrays(rowData.get(i), dataFromFile.get(i)), "Row Data from app:  " + rowData.get(i).toString() + "; Row Data from file: " + dataFromFile.get(i).toString() + "\n");
		}
		soft_assert.assertAll();
	}

	@Step
	public void checkVarQuality(String...rangeValues) {
		SoftAssert soft_assert = new SoftAssert();
		Assert.isFalse(variantsTable.tableRows.size() == 0, "There are no records in the 'Variants' table");
		for (int i = 0; i < variantsTable.tableRows.size(); i++) {
			variantsTable.tableRows.get(i).clickInSpeacialCell();
			soft_assert.assertTrue(variationInfoWindow.isQualityWithinRange(rangeValues), "In "+(i+1)+" row is incorrect quality.");
			variationInfoWindow.closeWindow();
		}
		soft_assert.assertAll();
	}

	@Step
	public void checkVarGenes(String...genes) {
		List<String> valuesFromTable;
		List<String> expectedValues = Arrays.asList(genes);
		List<Boolean> boolList = new ArrayList<>();
		for (int i = 0; i < variantsTable.tableRows.size(); i++) {
			valuesFromTable = Arrays.asList(variantsTable.tableRows.get(i).getRowValue(variantsTable.getColumnIndex("Gene")).replaceAll("\\s", "").split(","));
			for (int j = 0; j < genes.length; j++) {
				boolList.add(valuesFromTable.contains(genes[j]));
			}
			Assert.isTrue(boolList.contains(true), "There is no required gene among values from table: " + valuesFromTable.toString());
			boolList.clear();
		}
	}

	@Step
	public void checkPictWithFile(String ProjectDir) {
		try {
			variantsTable.collectAllPictData(ProjectDir);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}