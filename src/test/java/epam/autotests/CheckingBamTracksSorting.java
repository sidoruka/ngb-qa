package epam.autotests;

import epam.autotests.page_objects.sections.VariationInfoModalWindow;
import epam.autotests.utils.TestBase;
import org.junit.Ignore;
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


@Title("Checking Bam Tracks Sorting")
@Features("Checking Bam Tracks Sorting")
public class CheckingBamTracksSorting extends TestBase{
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
		System.out.println("=== CheckingBamTracksSorting.preparation(); @BeforeClass");
	}
    @Ignore
    @Test(priority=0)
    @Stories("BAM track default sorting validation")
	public void checkingOfBAMSortingDefault() {
        String location = "2:29224735-29224821";
        header.chooseCoordinates(location);
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Sort", "Default");
        String name = location_for_name + "_default_sorting";
        variationInfoWindow.savePicture(refPath, name, xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingOfBAMSortingDefault(); @Test(priority=0)");
    }

    @Test(priority=1)
    @Stories("BAM track sorting By start location validation")
    public void checkingOfBAMSortingByStartLocation() {
        String location = "3:179220354-179220691";
        header.chooseCoordinates(location);
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Sort", "By start location");
        String name = location_for_name + "_By_start_location_sorting";
        variationInfoWindow.savePicture(refPath, name, xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingOfBAMSortingByStartLocation(); @Test(priority=1)");
    }

    @Test(priority=2)
    @Stories("BAM track sorting By base validation")
    public void checkingOfBAMSortingByBase() {
        String location = "2:29224746-29224816";
        header.chooseCoordinates(location);
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Sort", "By base");
        String name = location_for_name + "_By_base_sorting";
        variationInfoWindow.savePicture(refPath, name, xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingOfBAMSortingByBase(); @Test(priority=2)");
    }

    @Test(priority=3)
    @Stories("BAM track sorting By strand validation")
    public void checkingOfBAMSortingByStrand() {
        String location = "2:29209838-29210059";
        header.chooseCoordinates(location);
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Sort", "By strand");
        String name = location_for_name + "_By_strand_sorting";
        variationInfoWindow.savePicture(refPath, name, xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingOfBAMSortingByStrand(); @Test(priority=3)");
    }

    @Test(priority=4)
    @Stories("BAM track sorting By mapping quality validation")
    public void checkingOfBAMSortingByMappingQuality() {
        String location = "3:179219986-179220030";
        header.chooseCoordinates(location);
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Sort", "By mapping quality");
        String name = location_for_name + "_By_mapping_quality_sorting";
        variationInfoWindow.savePicture(refPath, name, xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingOfBAMSortingByMappingQuality(); @Test(priority=4)");
    }

    @Test(priority=5)
    @Stories("BAM track sorting By insert size validation")
    public void checkingOfBAMSortingByInsertSize() {
        String location = "2:29224750-29224810";
        header.chooseCoordinates(location);
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        projectPage.browserPanel.selectTrackMenuItem("BAM", "Sort", "By insert size");
        String name = location_for_name + "_by_insert_size_sorting";
        variationInfoWindow.savePicture(refPath, name, xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingOfBAMSortingByInsertSize(); @Test(priority=5)");
        }

	@AfterClass(alwaysRun=true)
	public void resetBrowser(){
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
		System.out.println("=== CheckingBamTracksSorting.resetBrowser(); @AfterClass(alwaysRun=true)");
	}
}
