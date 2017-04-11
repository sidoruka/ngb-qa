package epam.autotests;

import com.epam.commons.Timer;
import epam.autotests.utils.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static epam.autotests.page_objects.enums.ProjectPagePreconditions.OPEN_SETTING;
import static epam.autotests.page_objects.enums.SettingTabs.*;
import static epam.autotests.page_objects.enums.Views.RESTORE_DEFAULT;
import static epam.autotests.page_objects.site.NGB_Site.projectPage;
import static epam.autotests.page_objects.site.NGB_Site.settingWindow;


public class CheckingMainPageTest extends TestBase {

    @Test(priority = 0)
    public void checkingSettingsContent() {
        isInState(OPEN_SETTING);
        if (settingWindow.isDisplayed()) {
            settingWindow.openTab(ALIGNMENTS);
            Timer.sleep(5000);
            settingWindow.openTab(GFF_GTF);
            Timer.sleep(5000);
            settingWindow.openTab(CUSTOMIZE);
            Timer.sleep(5000);
            settingWindow.openTab(GENERAL);
            Timer.sleep(5000);
            settingWindow.close();
        }
        System.out.println("=== CheckingMainPageTest.checkingDataPresence_Tiles(); @Test(priority=0)");
    }

    @AfterClass(alwaysRun = true)
    public void resetBrowser() {
        projectPage.closeAllTracks();
        Timer.sleep(1000);
        projectPage.openPanel(RESTORE_DEFAULT);
        System.out.println("=== CheckingMainPageTest.resetBrowser(); @AfterClass(alwaysRun=true)");
    }
}
