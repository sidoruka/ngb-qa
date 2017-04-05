package epam.autotests.page_objects.sections;

import com.epam.commons.Timer;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.complex.Elements;
import com.epam.jdi.uitests.web.selenium.elements.complex.TextList;
import com.epam.jdi.uitests.web.selenium.elements.complex.table.Table;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vsevolod_Adrianov on 7/18/2016.
 */
public class GridPanel extends Section {

	@FindBy(css = ".lm_item_container[style*='block'] .jqx-widget-header.jqx-grid-header [role='columnheader']")
	private Elements<Button> columnButton;

//	@FindBy(xpath = ".//div[@class='jqx-widget-header jqx-grid-header']//span")
	@FindBy(css =".ui-grid-header-cell-row .ui-grid-header-cell-label")
	public TextList<?> columnsList;

	@FindBy(css = ".lm_item_container[style*='block'] [role='row'] .jqx-grid-cell.jqx-item")
	private Elements<Button> vRows;

	/*
	 * @FindBy(css=".md-transition-in") private PropertyVCF VCFPanel;
	 */
	//
	// ?? public int getGridColumns() {
	// ?? return columnButton.size();
	// ?? }

	public int getNumCols() {
		return columnButton.size();
	}

	public int getNumRows() {
		return vRows.size() / this.getNumCols();
	}

	public Button cell(int row, int col) {
		return vRows.get(row * this.getNumCols() + col);
	}

	public Button columnHeader(int i) {
		return columnButton.get(i);
	}

	/*
	 * public void ViewTableHeader() { // int N = columnButton.size(); int N =
	 * getNumCols(); String[] ColNames = new String[N];
	 * System.out.println(N+" columns"); for(int i=0; i<N; i++) { //
	 * columnButton.get(i).getText() ColNames[i] = Title(i).getText(); //
	 * System.out.print("<"+columnButton.get(i).getText()+"> "); } for(int i=0;
	 * i<N; i++) { System.out.print(" <" + ColNames[i] + ">"); // try sort out
	 * //?? columnButton.get(i).click(); } System.out.println(); }
	 */
	//
/*	public void vcfTableSort() {
		// String
		// Title(ColNum).click();
		String[] Headers = { "Type", "Chr", "Gene", "Position" };
		// String[] Headers = {"Recent projects", "Tracks", "Reference",
		// "Owner", "Created"};
		int size = Headers.length;

		for (int i = 0; i < size; i++) {
			sectionTemplatesTable.header(Headers[i]).click();
			System.out.println("-- (" + Headers[i] + ").Click ---> OK");
		}
	}
*/
	/*public boolean isColumnSorted(String ColName) {
		List<String> sortedCol = sectionTemplatesTable.columnValue(sectionTemplatesTable.headers().indexOf(ColName) + 1);
		// ?? List<String> columnsValues =
		// sectionTemplatesTable.columnValue(index);
		List<String> expectedColumnsValues = new ArrayList<>(sortedCol);
		Collections.sort(expectedColumnsValues, (String a, String b) -> a.toUpperCase().compareTo(b.toUpperCase()));
		// Collections.sort(expectedColumnsValues, String::compareTo);
		return sortedCol.equals(expectedColumnsValues);
	}*/

	public void sorting(String colName) {
		System.out.println("Sort out column: " + colName);
		for (int i = 0; i < getNumCols(); i++) {
			if (colName.equals(columnHeader(i).getText())) {
				columnHeader(i).click();
				System.out.println("Sort out column: " + colName + "[" + i + "] .... Success");
				return;
			}
		}
	}
}
