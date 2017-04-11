package epam.autotests.page_objects.sections;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.common.Text;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

/**
 * Created by Vsevolod_Adrianov on 6/1/2016.
 * <p>
 * <b>Refactored</b> by Aksenov Oleg in September-October 2016
 */

class ProjectTile extends Section {

    @FindBy(xpath = ".//md-card-title//span[contains(@class, 'project-names')]")
    private Label projectTitle;

    @FindBy(xpath = ".//ngb-project-reference/span")
    private Text reference;

    @FindBy(xpath = ".//md-card-content//ngb-project-tracks/span")
    private Label countOfFiles;

    @FindBy(xpath = ".//ngb-edit-project-button/button")
    private Button editBtn;

    @FindBy(xpath = ".//ngb-delete-project-button/button")
    private Button removeBtn;

    @FindBy(xpath = ".//div[contains(@class, 'md-card-project_info')]/div[@class='ng-binding']")
    private Text creationDate;

    public static void main(String[] args) {
        String str = "Created 2016-06-01";
        System.out.println(str + "\n" + str.replaceAll("[^0-9^-]", ""));
    }

    public String getTitle() {
        return projectTitle.getText();
    }

    public String getReference() {
        return reference.getText();
    }

    public int getCountOfFiles() {
        return Integer.parseInt(countOfFiles.getText());
    }

    public void clickEditBtn() {
        editBtn.clickCenter();
    }

    public void clickRemoveBtn() {
        removeBtn.clickCenter();
    }

    public String getCreationDate() {
        return creationDate.getText().replaceAll("[^0-9^-]", "");
    }

    public void checkVisibilityOfAllElements() {
        SoftAssert soft_assert = new SoftAssert();
        soft_assert.assertTrue(projectTitle.isDisplayed(), "Project title isn't displayed");
        soft_assert.assertTrue(reference.isDisplayed(), "Reference isn't displayed");
        soft_assert.assertTrue(countOfFiles.isDisplayed(), "Count of files isn't displayed");
        soft_assert.assertTrue(editBtn.isDisplayed(), "Edit button isn't displayed");
        soft_assert.assertTrue(removeBtn.isDisplayed(), "Remove button isn't displayed");
        soft_assert.assertTrue(creationDate.isDisplayed(), "Date of creation isn't displauyed");
        soft_assert.assertAll();
    }
}
