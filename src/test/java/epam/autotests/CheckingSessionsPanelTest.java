package epam.autotests;

import com.epam.commons.Timer;
import com.epam.web.matcher.testng.Assert;
import epam.autotests.page_objects.sections.Header;
import epam.autotests.page_objects.sections.VariantDensity;
import epam.autotests.page_objects.sections.VariationInfoModalWindow;
import epam.autotests.utils.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static epam.autotests.page_objects.enums.ProjectPagePreconditions.*;
import static epam.autotests.page_objects.enums.Views.RESTORE_DEFAULT;
import static epam.autotests.page_objects.enums.Views.SESSIONS;
import static epam.autotests.page_objects.site.NGB_Site.header;
import static epam.autotests.page_objects.site.NGB_Site.mainPage;
import static epam.autotests.page_objects.site.NGB_Site.projectPage;


public class CheckingSessionsPanelTest extends TestBase {

    private final String bookmark1 = "Bkmrk1";

    @BeforeClass
    public void preparation() {
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        mainPage.datasetsPanel.select("/SV_Sample1", true);
        projectPage.openPanel(SESSIONS);
        System.out.println("=== CheckingSessionsPanelTest.preparation(); @BeforeClass");
    }

    @Test(priority = 0)
    public void TestBookmarkCreation() {
        header.chooseChromosome("17");
        projectPage.browserPanel.decreaseZoom(4);
        projectPage.addBookmark(bookmark1);
        projectPage.checkViewAfterAddition();
        projectPage.sessionsPanel.isThereRequiredBookmark(bookmark1);
        projectPage.checkingViewOfBookmarksAndBrowser(bookmark1, "17");
        System.out.println("=== CheckingSessionsPanelTest.TestBookmarkCreation(); @Test(priority=0)");
    }

    @Test(priority = 1)
    public void TestUsingCreatedBookmark() {
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        projectPage.openPanel(SESSIONS);
        projectPage.sessionsPanel.chooseBookmark(bookmark1);
        Timer.sleep(1000);
        projectPage.checkingViewOfBookmarksAndBrowser(bookmark1, "17");
        projectPage.sessionsPanel.deleteBookmarkWithoutConfirmation(bookmark1);
        projectPage.sessionsPanel.isThereRequiredBookmark(bookmark1);
        projectPage.sessionsPanel.deleteBookmarks(bookmark1);
        Timer.sleep(1000);
        Assert.isFalse(projectPage.sessionsPanel.isThereAddedBookmarks(bookmark1), "There are remained bookmarks added during the test in the table");
        System.out.println("=== CheckingSessionsPanelTest.TestUsingCreatedBookmark(); @Test(priority=1)");
    }

    @AfterClass(alwaysRun = true)
    public void deleteCreatedBookmarks() {
        projectPage.sessionsPanel.deleteBookmarks(bookmark1);
        Assert.isFalse(projectPage.sessionsPanel.isThereAddedBookmarks(bookmark1), "There are remained bookmarks added during the test in the table");
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        System.out.println("=== CheckingSessionsPanelTest.deleteCreatedBookmarks(); @AfterClass(alwaysRun=true)");
    }
}
