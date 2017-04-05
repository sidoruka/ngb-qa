package epam.autotests;

import com.epam.commons.Timer;
import epam.autotests.page_objects.sections.VariationInfoModalWindow;
import epam.autotests.utils.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static epam.autotests.page_objects.enums.ProjectPagePreconditions.OPEN_DATASETS_PANEL;
import static epam.autotests.page_objects.enums.Views.RESTORE_DEFAULT;
import static epam.autotests.page_objects.site.NGB_Site.*;


@Features("Checking BED tracks")
public class CheckingTrackBed extends TestBase{
    String refPath = CurrentDir()+"\\target\\";
    String xPath="'/html/body/ui-view/div/div[2]/ngb-golden-layout/div/div/div/div[1]/div[2]/div/div/ngb-browser/ngb-tracks-view/div/div/div[2]/ngb-track[2]/div/div[3]/div/canvas'";
	@BeforeClass
	public void preparations(){
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
		isInState(OPEN_DATASETS_PANEL);
 		mainPage.datasetsPanel.select("/BED/UP000005640_9606_domain.bed");
        VariationInfoModalWindow.waitVisualizer(xPath);
		System.out.println("=== CheckingBamTracksSorting.preparation(); @BeforeClass");
	}

    @Test(priority=0)
    @Stories("Full chromosme histogram check")
    public void checkingOfBed_full_chr()
    {
        header.chooseCoordinates("1: 1 - 248956422");
        Timer.sleep(1000);
        variationInfoWindow.savePicture(refPath, "bed_full_chr1", xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingOfBed_full_chr(); @Test(priority=0)");
    }

	@Test(priority=1)
    @Stories("Bed features check")
	public void checkingOfBed_features()
    {
		header.chooseCoordinates("1: 943314 - 993875");
        Timer.sleep(1000);
        variationInfoWindow.savePicture(refPath, "bed_features", xPath);
		System.out.println("=== CheckingBamTracksSorting.checkingOfBed_features(); @Test(priority=1)");
	}



    @Test(priority=2)
    @Stories("Histogram check")
    public void checkingOfBed_Histogram()
    {
        header.chooseCoordinates("1: 1 - 11566414");
        Timer.sleep(1000);
        variationInfoWindow.savePicture(refPath, "bed_histogram", xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingOfBed_Histogram(); @Test(priority=0)");
    }

    @Test(priority=2)
    @Stories("Features Low Zoom check")
    public void checkingOfFeatures_Low_Zoom()
    {
        header.chooseCoordinates("1: 353094 - 10762866");
        Timer.sleep(1000);
        variationInfoWindow.savePicture(refPath, "Features_Low_Zoom", xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingOfFeatures_Low_Zoom(); @Test(priority=0)");
    }

    @AfterClass(alwaysRun=true)
	public void resetDatasets(){
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
		System.out.println("=== CheckingBamTracksSorting.resetDatasets(); @AfterClass(alwaysRun=true)");
	}
}
