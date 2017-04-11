package epam.autotests.page_objects.panels;

import com.epam.jdi.uitests.web.selenium.elements.common.CheckBox;
import com.epam.jdi.uitests.web.selenium.elements.common.TextField;
import epam.autotests.page_objects.general.Node;
import epam.autotests.page_objects.general.Panel;
import epam.autotests.page_objects.general.Tree;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Created by Vsevolod_Adrianov on 16-Jan-17.
 */
public class DatasetsPanel extends Panel {
    @FindBy(xpath = ".//md-input-container/input")
    private TextField searchTextField;

    @FindBy(xpath = ".//md-virtual-repeat-container") //.md-virtual-repeat-offsetter") //done
    private Tree dsTree;

    @FindBy(xpath = ".//md-virtual-repeat-container//input")
    private CheckBox checkBox;

    public boolean checkBoxIsSelected() {
        return dsTree.isSelected();
    }


    public void searchProject(String nameProj) {
        searchTextField.clickCenter();
        searchTextField.sendKeys(nameProj + Keys.ENTER);
        searchTextField.sendKeys(nameProj);
    }

    public void select(String DataName) {
        Path p = Paths.get(DataName);
        String file = p.getFileName().toString();
        String pp = p.toString();
        System.out.println("path> <" + pp + ">");
        Node node = null;
        int i = 0;
        for (String retval : DataName.split("/")) {
            if (file.equals(retval) && node != null) {
                // Check file
                node = dsTree.getBy(retval);
                node.Check();
            } else {
                if (!retval.isEmpty()) {
                    if (i < 2) {
                        node = dsTree.getBy(retval);
                    } else {
                        node = dsTree.getBy(retval);
                    }
                    node.OpenToggle();
                }
            }
            System.out.println("[" + i + "] " + retval);
            i++;
        }
    }

    public void loadDatasets(String DataName) {
        Path p = Paths.get(DataName);
        String file = p.getFileName().toString();
        String pp = p.toString();
        System.out.println("path> <" + pp + ">");
        Node node = null;
        int i = 0;
        boolean Checked = false;
        for (String retval : DataName.split("/")) {
            if (file.equals(retval) && node != null) {
                node = node.inTree.getBy(retval);
                node.Check();
                Checked = true;
            } else {
                if (!retval.isEmpty()) {
                    if (i < 2) {
                        node = dsTree.getBy(retval);
                    } else {
                        node = dsTree.getBy(retval);
                    }
                    node.OpenToggle();
                }
            }
            System.out.println("[" + i + "] " + retval);
            i++;
        }
        if (!Checked && node != null) {
            node.Check();
        }
    }

    public void unloadDatasets(String DataName) {
        Path p = Paths.get(DataName);
        String file = p.getFileName().toString();
        String pp = p.toString();
        System.out.println("path> <" + pp + ">");
        Node node = null;
        int i = 0;
        boolean Checked = false;
        for (String retval : DataName.split("/")) {
            if (file.equals(retval) && node != null) {
                node = node.inTree.getBy(retval);
                node.unCheck();
                Checked = true;
            } else {
                if (!retval.isEmpty()) {
                    if (i < 2) {
                        node = dsTree.getBy(retval);
                    } else {
                        node = dsTree.getBy(retval);
                    }
                    node.OpenToggle();
                }
            }
            System.out.println("[" + i + "] " + retval);
            i++;
        }
        if (!Checked && node != null) {
            node.unCheck();
        }
    }

    public void unSelect(String DataName) {
        Path p = Paths.get(DataName);
        String file = p.getFileName().toString();
        String pp = p.toString();
        System.out.println("path> <" + pp + ">");
        Node node = null;
        int i = 0;
        for (String retval : DataName.split("/")) {
            if (file.equals(retval) && node != null) {
                node = dsTree.getBy(retval);
                node.unCheck();
            } else {
                if (!retval.isEmpty()) {
                    if (i < 2) {
                        node = dsTree.getBy(retval);
                    } else {
                        node = dsTree.getBy(retval);
                    }
                    node.OpenToggle();
                }
            }
            System.out.println("[" + i + "] " + retval);
            i++;
        }
    }
}
