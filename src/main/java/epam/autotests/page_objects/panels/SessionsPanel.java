package epam.autotests.page_objects.panels;

import com.epam.commons.Timer;
import com.epam.web.matcher.junit.Assert;
import epam.autotests.page_objects.general.Panel;
import epam.autotests.page_objects.sections.CustomTable;
import epam.autotests.page_objects.site.NGB_Site;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;

/**
 * @author Oleg Aksenov
 *         <br>Created in October, 2016</br>
 */

public class SessionsPanel extends Panel {

    @FindBy(css = ".ui-grid-contents-wrapper>.ui-grid-render-container-body")
    public CustomTable bookmarksTable;

    public void deleteBookmarks(String... bookmark1) {
        for (String bookmark : bookmark1) {
            bookmarksTable.deleteRecord(bookmark, true);
        }
        Timer.sleep(1000);
    }

    public void deleteBookmarkWithoutConfirmation(String bookmark) {
        bookmarksTable.deleteRecord(bookmark, false);
    }

    public boolean isThereAddedBookmarks(String... bookmarks) {
        if (bookmarksTable.tableRows.isEmpty()) {
            return false;
        } else {
            return bookmarksTable.collectColumnValues("Name", false).contains(Arrays.asList(bookmarks));
        }
    }

    public void isThereRequiredBookmark(String bookmark) {
        if (this.bookmarksTable.findRow(bookmark) == null) {
            NGB_Site.projectPage.refresh();
        } else {
            Assert.isFalse(this.bookmarksTable.findRow(bookmark) == null, "Bookmark '" + bookmark + "' wasn't found");
        }
    }

    public void chooseBookmark(String bookmark1) {
        bookmarksTable.findRow(bookmark1).clickCenter();
    }

}
