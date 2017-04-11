package epam.autotests.page_objects.general;

import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.common.CheckBox;
import com.epam.jdi.uitests.web.selenium.elements.common.Text;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Vsevolod_Adrianov on 17-Jan-17.
 */
public class Node extends Section {
    @FindBy(xpath = ".//md-virtual-repeat-container") //done??
    public Tree inTree;
    @FindBy(css = ".ivh-treeview-checkbox") //no changes

    CheckBox cBox;
    @FindBy(css = ".md-virtual-repeat-offsetter ng-md-icon") // done
    private Element Toggle;
    @FindBy(css = ".md-virtual-repeat-offsetter ng-md-icon.opened") // done
    private Element Opened;
    @FindBy(css = ".ivh-treeview-node-label") //done
    private Text Name;
    @FindBy(css = ".dataset-genome.ng-binding.ng-scope")
    private Text Reference;

    public String getToggleName() {
        String[] temp;
        temp = Name.getText().trim().split(" ");
        return temp[0];
    }

    public Text getToggleLabel() {
        return Name;
    }

    public void OpenToggle() {
        if (!Opened.isDisplayed()) {
            Toggle.clickCenter();
        }
    }

    public void Check() {
        cBox.check();
    }

    public void unCheck() {
        cBox.uncheck();
    }
}

