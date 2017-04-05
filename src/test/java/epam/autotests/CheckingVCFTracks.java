package epam.autotests;

import com.epam.commons.Timer;
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


@Features("Checking VCF tracks")
public class CheckingVCFTracks extends TestBase{
    String refPath = CurrentDir()+"\\target\\";
    String xPath="'/html/body/ui-view/div/div[2]/ngb-golden-layout/div/div/div/div[1]/div[2]/div/div/ngb-browser/ngb-tracks-view/div/div/div[2]/ngb-track[2]/div/div[3]/div/canvas'";
	@BeforeClass
	public void preparations(){
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
		isInState(OPEN_DATASETS_PANEL);
		mainPage.datasetsPanel.select("/SV_Sample1/sample_1-lumpy.vcf");
        header.chooseChromosome("17");
		System.out.println("=== CheckingBamTracksSorting.preparation(); @BeforeClass");
	}
    @Test(priority=0)
    @Stories("VCF expanded check")
    public void checkingVCFExpanded(){
        Timer.sleep(1000);
        if (projectPage.browserPanel.checkTrackMenuItem().toString().contains("Collapsed"))
            projectPage.browserPanel.selectTrackMenuItem("VCF","Variants view","Expanded");
        variationInfoWindow.savePicture(refPath, "vcf_expanded_chr17", xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingVCFExpanded(); @Test(priority=0)");
    }

    @Test(priority=1)
    @Stories("VCF bubbles check")
    public void checkingVCFBubble(){
        Timer.sleep(1000);
        if (projectPage.browserPanel.checkTrackMenuItem().toString().contains("Expanded"))
            projectPage.browserPanel.selectTrackMenuItem("VCF","Variants view","Collapsed");
        variationInfoWindow.savePicture(refPath, "vcf_bubbles_chr17", xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingVCFBubble(); @Test(priority=1)");
    }

    @Test(priority=2)
    @Stories("VCF BND_collapsed check")
    public void checkingVCFBND(){
        header.chooseCoordinates("17: 31312989 - 31348231");
        Timer.sleep(1000);
        if (projectPage.browserPanel.checkTrackMenuItem().toString().contains("Expanded"))
            projectPage.browserPanel.selectTrackMenuItem("VCF","Variants view","Collapsed");
        variationInfoWindow.savePicture(refPath, "vcf_bnd_collapsed", xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingVCFBND(); @Test(priority=2)");
    }

    @Test(priority=3)
    @Stories("VCF DEL_collapsed check")
    public void checkingVCFDel(){
        header.chooseCoordinates("17: 31351763 - 31353153");
        Timer.sleep(1000);
        if (projectPage.browserPanel.checkTrackMenuItem().toString().contains("Expanded"))
            projectPage.browserPanel.selectTrackMenuItem("VCF","Variants view","Collapsed");
        variationInfoWindow.savePicture(refPath, "vcf_del_collapsed", xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingVCFDel(); @Test(priority=3)");
    }

    @Test(priority=4)
    @Stories("VCF INV_collapsed check")
    public void checkingVCFInv(){
        header.chooseCoordinates("2: 23148176 - 45513256");
        Timer.sleep(1000);
        if (projectPage.browserPanel.checkTrackMenuItem().toString().contains("Expanded"))
            projectPage.browserPanel.selectTrackMenuItem("VCF","Variants view","Collapsed");
        variationInfoWindow.savePicture(refPath, "vcf_inv_collapsed", xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingVCFDel(); @Test(priority=4)");
    }

    @Test(priority=2)
    @Stories("VCF BND_Expanded check")
    public void checkingVCFBND_expand(){
        header.chooseCoordinates("17: 31312989 - 31348231");
        Timer.sleep(1000);
        if (projectPage.browserPanel.checkTrackMenuItem().toString().contains("Collapsed"))
            projectPage.browserPanel.selectTrackMenuItem("VCF","Variants view","Expanded");
        variationInfoWindow.savePicture(refPath, "vcf_bnd_Expanded", xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingVCFBND_expand(); @Test(priority=5)");
    }

    @Test(priority=3)
    @Stories("VCF DEL_Expanded check")
    public void checkingVCFDel_expand(){
        header.chooseCoordinates("17: 31351763 - 31353153");
        Timer.sleep(1000);
        if (projectPage.browserPanel.checkTrackMenuItem().toString().contains("Collapsed"))
            projectPage.browserPanel.selectTrackMenuItem("VCF","Variants view","Expanded");
        variationInfoWindow.savePicture(refPath, "vcf_del_Expanded", xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingVCFDel_expand(); @Test(priority=6)");
    }

    @Test(priority=4)
    @Stories("VCF INV_Expanded check")
    public void checkingVCFInv_expand(){
        header.chooseCoordinates("2: 23148176 - 45513256");
        Timer.sleep(1000);
        if (projectPage.browserPanel.checkTrackMenuItem().toString().contains("Collapsed"))
            projectPage.browserPanel.selectTrackMenuItem("VCF","Variants view","Expanded");
        variationInfoWindow.savePicture(refPath, "vcf_inv_Expanded", xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingVCFInv_expand(); @Test(priority=7)");
    }

    @AfterClass(alwaysRun=true)
	public void resetFiltration(){
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
		System.out.println("=== CheckingBamTracksSorting.checkingTracksSearchUpperCase(); @AfterClass(alwaysRun=true)");
	}
}
