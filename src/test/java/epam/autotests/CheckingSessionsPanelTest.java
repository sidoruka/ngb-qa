package epam.autotests;

import com.epam.commons.Timer;
import com.epam.web.matcher.testng.Assert;
import epam.autotests.utils.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static epam.autotests.page_objects.enums.ProjectPagePreconditions.*;
import static epam.autotests.page_objects.enums.Views.RESTORE_DEFAULT;
import static epam.autotests.page_objects.site.NGB_Site.mainPage;
import static epam.autotests.page_objects.site.NGB_Site.projectPage;


public class CheckingSessionsPanelTest extends TestBase {

    private final String bookmark1 = "Bkmrk1";

    @BeforeClass
    public void preparation() {
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        mainPage.datasetsPanel.select("/SV_Sample1/sample_1-lumpy.vcf");
        isInState(OPEN_BROWSER_PANEL);
        isInState(OPEN_SESSIONS_PANEL);
        isInState(CHROMOSOME_ISNT_CHOSEN);
        System.out.println("=== CheckingSessionsPanelTest.preparation(); @BeforeClass");
    }

    @Test(priority = 0)
    public void TestBookmarkCreation() {
        isInState(CHROMOSOME_CHOSEN);
        projectPage.browserPanel.decreaseZoom(3);
        projectPage.addBookmark(bookmark1);
        projectPage.checkViewAfterAddition();
        projectPage.sessionsPanel.isThereRequiredBookmark(bookmark1);
        projectPage.checkingViewOfBookmarksAndBrowser(bookmark1, "2");
        System.out.println("=== CheckingSessionsPanelTest.TestBookmarkCreation(); @Test(priority=0)");
    }

    @Test(priority = 1)
    public void TestUsingCreatedBookmark() {
        isInState(CHROMOSOME_ISNT_CHOSEN);
        projectPage.openPanel(RESTORE_DEFAULT);
        projectPage.closeAllTracks();
        isInState(OPEN_SESSIONS_PANEL);
        projectPage.sessionsPanel.chooseBookmark(bookmark1);
        Timer.sleep(1000);
        projectPage.checkingViewOfBookmarksAndBrowser(bookmark1, "2");
        projectPage.sessionsPanel.deleteBookmarkWithoutConfirmation(bookmark1);
        projectPage.sessionsPanel.isThereRequiredBookmark(bookmark1);
        projectPage.sessionsPanel.deleteBookmarks(bookmark1);
        Assert.isFalse(projectPage.sessionsPanel.isThereAddedBookmarks(bookmark1), "There are remained bookmarks added during the test in the table");
        System.out.println("=== CheckingSessionsPanelTest.TestUsingCreatedBookmark(); @Test(priority=1)");
    }

    @AfterClass(alwaysRun = true)
    public void deleteCreatedBookmarks() {
//        projectPage.sessionsPanel.deleteBookmarks(bookmark1);
//        Assert.isFalse(projectPage.sessionsPanel.isThereAddedBookmarks(bookmark1), "There are remained bookmarks added during the test in the table");
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        System.out.println("=== CheckingSessionsPanelTest.deleteCreatedBookmarks(); @AfterClass(alwaysRun=true)");
    }
}
