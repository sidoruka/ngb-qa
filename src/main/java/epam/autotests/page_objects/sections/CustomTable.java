package epam.autotests.page_objects.sections;

import com.epam.commons.Timer;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.complex.Elements;
import com.epam.jdi.uitests.web.selenium.elements.complex.TextList;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import epam.autotests.page_objects.enums.SortingTypes;
import epam.autotests.page_objects.enums.VarTableColumns;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static epam.autotests.page_objects.site.NGB_Site.confirmWindow;
import static epam.autotests.page_objects.site.NGB_Site.isListSorted;
import static epam.autotests.utils.TestBase.compareListOfArrays;

public class CustomTable extends Section {

    @FindBy(css = ".ui-grid-header-cell-label")
    public TextList<?> columnsList;

    @FindBy(css = "[ui-grid-row='row']")
    public Elements<TableRow> tableRows;

    @FindBy(xpath = "//div[@role='columnheader']")
    public Elements<ColumnHeader> columns;



    public boolean checkColumns(String[] expColList) {
        List<String> actualList = columnsList.getTextList();
        System.out.println("Actual column list:   " + actualList + "\n"
                + "Expected column list: " + Arrays.toString(expColList));
        return (actualList.equals(Arrays.asList(expColList)));
    }

    private ColumnHeader getSortedColumn() {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).isSorted())
                return columns.get(i);
        }
        return null;
    }

    public Boolean checkSortedColumn(String string) {
        ColumnHeader sortedCol = getSortedColumn();
        if (sortedCol == null)
            return false;
        else
            return (sortedCol.getColumnName().equals(string)) ? true : false;
    }

    public Boolean checkTypeOfSorting(String string) {
        return (getSortedColumn().getSortingType().equals(string)) ? true : false;
    }

    private String getSortedColumnName() {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).isSorted())
                return columns.get(i).getColumnName();
        }
        return "";
    }

    public int getColumnIndex(String columnName) {
        int counter = 0;
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getColumnName().equals(columnName))
                break;
            counter++;
        }
        return counter;
    }

    public boolean isColumnPresent(String colName) {
        return columnsList.getTextList().contains(colName);
    }

    public List<?> collectColumnValues(String columnName, boolean isNumeric) {
        List columnsValues;
        if (isNumeric)
            columnsValues = new ArrayList<Integer>();
        else
            columnsValues = new ArrayList<String>();

        if (isColumnPresent(columnName)) {
            int index = getColumnIndex(columnName);
            int visibleRowsCount = getCountOfVisibleRows();
            for (int i = 0; i < visibleRowsCount; i++) {
                while (!tableRows.get(i).isDisplayed())
                    Timer.sleep(1000);
                if (isNumeric) {
                    columnsValues.add(Integer.parseInt(tableRows.get(i).getRowValue(index).replaceAll("[^0-9]", "")));
                } else {
                    String sValue = tableRows.get(i).getRowValue(index);
                    if (!sValue.isEmpty())
                        columnsValues.add(sValue);
                }
            }
        }
        return columnsValues;
    }

    public List<Integer> collectColumnsIndex(String... colNames) {
        List<Integer> colIndexes = new ArrayList<>();
        for (String column : colNames) {
            colIndexes.add(getColumnIndex(column));
        }
        return colIndexes;
    }

    public Boolean isColumnSorted(VarTableColumns colName, boolean isNumeric, boolean ascending) {
        return isListSorted(collectColumnValues(colName.value, isNumeric), ascending);
    }

    public void scrollTo(int rowNumber) {
        Actions action = new Actions(getDriver());

    }

    private int getCountOfVisibleRows() {
        int counter = 0;
        for (TableRow row : tableRows) {
            if (row.isDisplayed())
                counter++;
        }
        return counter;
    }

    public TableRow findRow(String columnName, String value) {

        for (int i = 0; i < tableRows.size(); i++) {
            if (tableRows.get(i).getRowValue(getColumnIndex(columnName)).equals(value))
                return tableRows.get(i);
        }
        return null;
    }

    /**
     * "Sort None", "Sort Descending", "Sort Ascending"
     */
    public void setSorting(VarTableColumns colName, SortingTypes type) {
        if (!isColumnPresent(colName.value))
            throw new NoSuchElementException("[ColumnSorting]:Column '" + colName.value + "' not found.");
        else {
            for (int i = 0; i < columns.size(); i++) {
                if (columns.get(i).getColumnName().equals(colName.value)) {
                    for (int j = 0; j < 3; j++) {
                        if (!columns.get(i).getSortingType().equals(type.value)) {
                            columns.get(i).click();
                        } else
                            break;
                    }
                    break;
                }
            }
        }
    }

    public void resetTableSorting() {
        ColumnHeader column = getSortedColumn();
        if (column != null) {
            for (int j = 0; j < 3; j++) {
                if (!column.getSortingType().equals("None Sorting")) {
                    column.click();
                } else
                    break;
            }
        }
    }

    public List<String[]> collectAllRowData(int... colIndexes) {
        List<String[]> rows = new ArrayList<>();
        for (int i = 0; i < tableRows.size(); i++) {
            rows.add(tableRows.get(i).collectRowData2(colIndexes));
        }
        return rows;
    }

    public void collectAllPictData(String pPath) {
        String tP = pPath + "\\target\\";
        for (int i = 0; i < tableRows.size(); i++) {
            try {
                tableRows.get(i).collectPictData(tP);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public List<String[]> getAllRowsDataFromTable() {
        List<String[]> itemsFromChart = new ArrayList<>();
        Actions action = new Actions(getDriver());
        List<String[]> list2 = new ArrayList<>();
        List<String[]> currentList = collectAllRowData(0, 1, 3, 4);
        int countOfVisibleRows = tableRows.size();
        while (!compareListOfArrays(list2, currentList)) {
            list2 = currentList;
            for (String[] item : currentList) {
                if (!itemsFromChart.contains(item))
                    itemsFromChart.add(item);
            }
            Timer.sleep(1000);
            currentList = collectAllRowData(0, 1, 3, 4);
        }
        return itemsFromChart;
    }

    public int getCountOfRowsOverall() {
        int count = 0;
        List<String> rowData = new ArrayList<>();


        return count;
    }

    public boolean isColumnContainOnlyOneValue(String value) {
        for (int i = 0; i < tableRows.size(); i++) {
            if (!tableRows.get(i).getRowValue(0).equals(value))
                return false;
        }
        return true;
    }

    public Element getRow(int iX) {
        return tableRows.get(iX);
    }

    public void deleteRecord(String bookmark, boolean confirmed) {
        TableRow row = findRow("Name", bookmark);
        if (row != null) {
            row.clickInSpeacialCell(); //delete bookmark
            if (confirmed)
                confirmWindow.pressOK();
            else
                confirmWindow.pressCancel();
        }
    }
}
