package epam.autotests;

import epam.autotests.page_objects.sections.VariationInfoModalWindow;
import epam.autotests.utils.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static epam.autotests.page_objects.enums.ProjectPagePreconditions.OPEN_DATASETS_PANEL;
import static epam.autotests.page_objects.enums.ProjectPagePreconditions.OPEN_SETTING;
import static epam.autotests.page_objects.enums.SettingTabs.ALIGNMENTS;
import static epam.autotests.page_objects.enums.Views.RESTORE_DEFAULT;
import static epam.autotests.page_objects.site.NGB_Site.*;


@Title("Checking Bam Tracks")
@Features("Checking Bam Tracks Sorting")
public class CheckingBamReadsView extends TestBase{
    String refPath = CurrentDir()+"\\target\\";
    String xPath="'/html/body/ui-view/div/div[2]/ngb-golden-layout/div/div/div/div[1]/div[2]/div/div/ngb-browser/ngb-tracks-view/div/div/div[2]/ngb-track[2]/div/div[3]/div/canvas'";
	@BeforeClass
	public void preparations(){
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
		isInState(OPEN_DATASETS_PANEL);
        isInState(OPEN_SETTING);
        if(settingWindow.isDisplayed()) {
            settingWindow.openTab(ALIGNMENTS);
            com.epam.commons.Timer.sleep(1000);
            settingWindow.uncheckDownsample();
            settingWindow.saveSettings();
        }
        else
        {
            Assert.fail("No settings menu on display");
        }
        mainPage.datasetsPanel.select("/SV_Sample1/sv_sample_1.bam");
        VariationInfoModalWindow.waitVisualizer(xPath);
		System.out.println("=== CheckingBamReadsView.preparation(); @BeforeClass");
	}

    @Test(priority=0)
    @Stories("BAM track insert size and pair orientation, view as pair, no coverage")
	public void checkingOfBAM_col_inssize_pairorient_aspair() {
        String location = "6:51294395-51295837";
        header.chooseCoordinates(location);
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show coverage");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Color mode", "By insert size and pair orientation");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "View as pairs");
        String name = location_for_name + "_col_inssize_pairorient_aspair";
        try{
        variationInfoWindow.savePicture(refPath, name, xPath);}
        catch (AssertionError fail) {
            projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show coverage");
            projectPage.browserPanel.selectTrackMenuItem("BAM", "Color mode", "No color");
            projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "View as pairs");
            Assert.fail(fail.toString());
        }
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show coverage");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Color mode", "No color");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "View as pairs");
        System.out.println("=== CheckingBamReadsView.checkingOfBAM_col_inssize_pairorient_aspair(); @Test(priority=0)");
    }

    @Test(priority=1)
    @Stories("BAM track collapsed")
    public void checkingOfBAM_collapsed() {
        String location = "2:29224759 - 29224804";
        header.chooseCoordinates(location);
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Sort", "Default");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "Collapsed");
        String name = location_for_name + "_collapsed";
        try{
            variationInfoWindow.savePicture(refPath, name, xPath);}
        catch (AssertionError fail) {
            projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "Expanded");
            Assert.fail(fail.toString());
        }
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "Expanded");
        System.out.println("=== CheckingBamReadsView.checkingOfBAM_collapsed(); @Test(priority=0)");
    }

    @Test(priority=2)
    @Stories("BAM track color - first in pair strand, Show mismatched bases disabled")
    public void checkingOfBAM_color_and_group_by_first_in_pair_no_missbase() {
        String location =  "6:60544758-60544803";
        header.chooseCoordinates(location);
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Sort", "Default");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "Collapsed");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show coverage");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show mismatched bases");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Color mode", "By first in pair strand");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Group", "By first in pair strand");
        String name = location_for_name + "_color_and_group_by_first_in_pair_no_missbase";
        try{
            variationInfoWindow.savePicture(refPath, name, xPath);}
        catch (AssertionError fail) {
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show coverage");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show mismatched bases");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Color mode", "No color");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "Expanded");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Group", "Default");
        Assert.fail(fail.toString());
    }
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show coverage");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show mismatched bases");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Color mode", "No color");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "Expanded");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Group", "Default");
        System.out.println("=== CheckingBamReadsView.checkingOfBAM_color_and_group_by_first_in_pair_no_missbase(); @Test(priority=2)");
    }

    @Test(priority=3)
    @Stories("BAM track color - By pair orientation, Show mismatched bases disabled")
    public void checkingOfBAM_col_and_group_pair_orient_missbase() {
        String location = "6:51294990-51295090";
        header.chooseCoordinates(location);
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show coverage");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show mismatched bases");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Sort", "Default");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Color mode", "By pair orientation");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Group", "By pair orientation");
        String name = location_for_name + "_col_and_group_pair_orient_missbase";
        try{
            variationInfoWindow.savePicture(refPath, name, xPath);}
        catch (AssertionError fail) {
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show coverage");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show mismatched bases");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Color mode", "No color");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "Expanded");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Group", "Default");
            Assert.fail(fail.toString());
        }
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show coverage");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show mismatched bases");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Color mode", "No color");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "Expanded");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Group", "Default");
        System.out.println("=== CheckingBamReadsView.checkingOfBAM_col_and_group_pair_orient_missbase(); @Test(priority=3)");
    }

    @Test(priority=4)
    @Stories("BAM track by read strand")
    public void checkingOfBAM_color_and_group_by_read_strand_no_missbase() {
        String location = "6:60544758-60544803";
        header.chooseCoordinates(location);
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Sort", "Default");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show coverage");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show mismatched bases");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "Collapsed");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Group", "By read strand");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Color mode", "By read strand");
        String name = location_for_name + "_color_and_group_by_read_strand_no_missbase";
        try{
            variationInfoWindow.savePicture(refPath, name, xPath);}
        catch (AssertionError fail) {
            projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show coverage");
            projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show mismatched bases");
            projectPage.browserPanel.selectTrackMenuItem("BAM", "Color mode", "No color");
            projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "Expanded");
            projectPage.browserPanel.selectTrackMenuItem("BAM", "Group", "Default");
            Assert.fail(fail.toString());
        }
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show coverage");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "General", "Show mismatched bases");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Color mode", "No color");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Reads view", "Expanded");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Group", "Default");
            System.out.println("=== CheckingBamReadsView.checkingOfBAM_color_and_group_by_read_strand_no_missbase(); @Test(priority=4)");
    }

	@AfterClass(alwaysRun=true)
	public void resetBrowser(){
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
		System.out.println("=== CheckingBamReadsView.resetBrowser(); @AfterClass(alwaysRun=true)");
	}
}
