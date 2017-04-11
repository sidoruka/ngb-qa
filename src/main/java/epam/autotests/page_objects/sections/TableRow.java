package epam.autotests.page_objects.sections;

import com.epam.commons.Timer;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.complex.Elements;
import com.epam.jdi.uitests.web.selenium.elements.complex.TextList;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static epam.autotests.page_objects.site.NGB_Site.variationInfoWindow;

public class TableRow extends Section {


    public static String variationName;
    @FindBy(css = "[role='gridcell']")
    private TextList<?> rowValues;
    @FindBy(css = "[role='gridcell']")

    private Elements<Element> rowCells;

    public String getRowValue(int columnIndex) {
        return this.rowValues.getTextList().get(columnIndex);
    }

    public boolean isRowContainsValue(String requiredValue) {
        return this.rowValues.getTextList().contains(requiredValue);
    }

    public List<String> collectRowData(int... columnsIndex) {
        List<String> values = new ArrayList<>();
        List<String> rowValues = this.rowValues.getTextList();
        for (int index : columnsIndex) {
            values.add(rowValues.get(index));
        }
        return values;
    }

    public String[] collectRowData2(int... columnsIndex) {
        String[] values = new String[columnsIndex.length];
        int counter = 0;
        for (int i : columnsIndex) {
            if (!this.rowCells.get(i).getWebElement().findElements(By.cssSelector("[aria-label='info']")).isEmpty()) {
                this.clickCell(i);
                values[counter++] = variationInfoWindow.getId();
                variationInfoWindow.closeWindow();
            } else {
                values[counter++] = this.rowCells.get(i).get(By.cssSelector(".ui-grid-cell-contents,.md-label")).getText();
            }
        }
        return values;
    }

    public String collectPictData(String toPath) {
        if (!this.rowCells.get(4).getWebElement().findElements(By.cssSelector("[aria-label='info']")).isEmpty()) {
            String svtype = this.rowCells.get(0).getWebElement().getText();
            String location = this.rowCells.get(3).getWebElement().getText();
            variationName = svtype + "_" + location;
            Timer.sleep(6000);
            this.clickCell(4);
            Timer.sleep(6000);
            try {
                variationInfoWindow.savePictureVCF(toPath);
            } catch (AssertionError fail) {

                variationInfoWindow.closeWindow();
            }
            variationInfoWindow.closeWindow();
        }
        return variationName;
    }

    private Element getSpecialCell() {
        for (Element cell : this.rowCells) {

            if (!cell.getWebElement().findElements(By.cssSelector("[aria-label='Table View']")).isEmpty()) //rowValues.getElements().size()
            {
                return cell;
            }
        }
        return null;
    }

    /**
     * Open Annotation window or delete bookmark
     */
    public void clickInSpeacialCell() {
        this.rowCells.get(4).clickCenter();
    }

    private void clickCell(int columnIndex) {
        this.rowCells.get(columnIndex).clickCenter();
    }
}
