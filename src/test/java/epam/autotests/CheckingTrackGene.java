package epam.autotests;

import com.epam.commons.Timer;
import epam.autotests.page_objects.sections.VariationInfoModalWindow;
import epam.autotests.utils.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static epam.autotests.page_objects.enums.ProjectPagePreconditions.OPEN_DATASETS_PANEL;
import static epam.autotests.page_objects.enums.Views.RESTORE_DEFAULT;
import static epam.autotests.page_objects.site.NGB_Site.*;


@Features("Checking gene tracks")
public class CheckingTrackGene extends TestBase{
    String refPath = CurrentDir()+"\\target\\";
    String xPath="'/html/body/ui-view/div/div[2]/ngb-golden-layout/div/div/div/div[1]/div[2]/div/div/ngb-browser/ngb-tracks-view/div/div/div[2]/ngb-track[2]/div/div[3]/div/canvas'";
	@BeforeClass
	public void preparations(){
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
		isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.select("/SV_Sample1/GRCh38_Genes");
        VariationInfoModalWindow.waitVisualizer(xPath);
		System.out.println("=== CheckingBamTracksSorting.preparation(); @BeforeClass");
	}
	@Test(priority=0)
    @Stories("Reference check")
	public void checkingOfReferenceLetters(){
		header.chooseCoordinates("2:29224764 - 29224799");
        String xPathRef="'/html/body/ui-view/div/div[2]/ngb-golden-layout/div/div/div/div[1]/div[2]/div/div/ngb-browser/ngb-tracks-view/div/div/div[2]/ngb-track[1]/div/div[3]/div/canvas'";
        Timer.sleep(1000);
        variationInfoWindow.savePicture(refPath, "reference", xPathRef);
		System.out.println("=== CheckingBamTracksSorting.checkingOfReferenceLetters(); @Test(priority=0)");
	}

    @Test(priority=1)
    @Stories("Reference check GC")
    public void checkingOfReferenceGC(){
        header.chooseCoordinates("1: 1 - 248956422");
        String xPathRef="'/html/body/ui-view/div/div[2]/ngb-golden-layout/div/div/div/div[1]/div[2]/div/div/ngb-browser/ngb-tracks-view/div/div/div[2]/ngb-track[1]/div/div[3]/div/canvas'";
        Timer.sleep(1000);
        variationInfoWindow.savePicture(refPath, "reference_gc", xPathRef);
        System.out.println("=== CheckingBamTracksSorting.checkingOfReferenceGC(); @Test(priority=1)");
    }

    @Test(priority=2)
    @Stories("Reference check GC high zoom")
    public void checkingOfReferenceGCHigh(){
        header.chooseCoordinates("1: 7253119 - 7261744");
        String xPathRef="'/html/body/ui-view/div/div[2]/ngb-golden-layout/div/div/div/div[1]/div[2]/div/div/ngb-browser/ngb-tracks-view/div/div/div[2]/ngb-track[1]/div/div[3]/div/canvas'";
        Timer.sleep(1000);
        variationInfoWindow.savePicture(refPath, "reference_gc_high", xPathRef);
        System.out.println("=== CheckingBamTracksSorting.checkingOfReferenceGCHigh(); @Test(priority=2)");
    }

	@Test(priority=3)
    @Stories("Search BGLAP")
	public void checkingGeneSearchByNameBGLAP(){
		header.chooseBookmark("BGLAP");
        String coordOfKRAS = projectPage.browserPanel.CoordMenu().getText().toString();
        Assert.assertEquals(coordOfKRAS, "1: 156241962 - 156243321");
		System.out.println("=== CheckingBamTracksSorting.checkingGeneSearchByNameBGLAP(); @Test(priority=3)");
	}

    @Test(priority=4)
    @Stories("Search ALK")
    public void checkingGeneSearchByNameALK(){
        header.chooseBookmark("ALK");
        String coordOfKRAS = projectPage.browserPanel.CoordMenu().getText().toString();
        Assert.assertEquals(coordOfKRAS, "2: 29192774 - 29921566");
        System.out.println("=== CheckingBamTracksSorting.checkingGeneSearchByNameALK(); @Test(priority=4)");
    }

    @Test(priority=5)
    @Stories("Search FOXP1")
    public void checkingGeneSearchByNameFOXP1(){
        header.chooseBookmark("FOXP1");
        String coordOfKRAS = projectPage.browserPanel.CoordMenu().getText().toString();
        Assert.assertEquals(coordOfKRAS, "3: 70954693 - 71583989");
        System.out.println("=== CheckingBamTracksSorting.checkingGeneSearchByNameFOXP1(); @Test(priority=5)");
    }


    @Test(priority=6)
    @Stories("Search ELN")
    public void checkingGeneSearchByNameELN(){
        header.chooseBookmark("ELN");
        String coordOfKRAS = projectPage.browserPanel.CoordMenu().getText().toString();
        Assert.assertEquals(coordOfKRAS, "7: 74027789 - 74069907");
        System.out.println("=== CheckingBamTracksSorting.checkingGeneSearchByNameELN(); @Test(priority=6)");
    }
    @Test(priority=7)
    @Stories("Search H1F0")
    public void checkingGeneSearchByNameH1F0(){
        header.chooseBookmark("H1F0");
        String coordOfKRAS = projectPage.browserPanel.CoordMenu().getText().toString();
        Assert.assertEquals(coordOfKRAS, "22: 37805093 - 37807436");
        System.out.println("=== CheckingBamTracksSorting.checkingGeneSearchByNameH1F0(); @Test(priority=7)");
    }

    @Test(priority=8)
    @Stories("gene collapsed")
    public void checkingGeneCheckCollapsed(){
        header.chooseBookmark("KRAS");
        String location_for_name= projectPage.browserPanel.CoordMenu().getText().toString();
        location_for_name = location_for_name.replaceAll(":","_").replaceAll("\\s","");
        if (projectPage.browserPanel.checkTrackMenuItem().toString().contains("Expanded"))
            projectPage.browserPanel.selectTrackMenuItem("GENE","Transcript view","Collapsed");
        String name = location_for_name + "_collapsed_gene";
        Timer.sleep(1000);
        variationInfoWindow.savePicture(refPath, name, xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingGeneCheckCollapsed(); @Test(priority=8)");
    }
    @Test(priority=9)
    @Stories("gene expanded")
    public void checkingGeneCheckExpanded(){
        header.chooseBookmark("KRAS");
        String location_for_name= projectPage.browserPanel.CoordMenu().getText().toString();
        location_for_name = location_for_name.replaceAll(":","_").replaceAll("\\s","");
        if (projectPage.browserPanel.checkTrackMenuItem().toString().contains("Collapsed"))
            projectPage.browserPanel.selectTrackMenuItem("GENE", "Transcript view", "Expanded");
        String name = location_for_name + "_expanded_gene";
        Timer.sleep(1000);
        variationInfoWindow.savePicture(refPath, name, xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingGeneCheckExpanded(); @Test(priority=9)");
    }

    @Test(priority=10)
    @Stories("gene histogram full chromosome")
    public void checkingGeneCheckHistogram(){
        header.chooseCoordinates("1: 1 - 248956422");
        String name = "gene_full_chromosome";
        Timer.sleep(1000);
        variationInfoWindow.savePicture(refPath, name, xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingGeneCheckHistogram(); @Test(priority=10)");
    }
    @Test(priority=11)
    @Stories("gene histogram 37 Mb")
    public void checkingGeneCheckHistogramHighZoom(){
        header.chooseCoordinates("1: 133034130 - 170319045");
        String name = "gene_37Mb_histogram";
        Timer.sleep(1000);
        variationInfoWindow.savePicture(refPath, name, xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingGeneCheckHistogramHighZoom(); @Test(priority=11)");
    }

    @Test(priority=12)
    @Stories("gene feature view")
    public void checkingGeneCheckFeature(){
        header.chooseCoordinates("1: 153140046 - 159232417");
        String name = "gene_6Mb_features";
        Timer.sleep(1000);
        variationInfoWindow.savePicture(refPath, name, xPath);
        System.out.println("=== CheckingBamTracksSorting.checkingGeneCheckFeature(); @Test(priority=12)");
    }

    @AfterClass(alwaysRun=true)
	public void resetFiltration(){
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
		System.out.println("=== CheckingBamTracksSorting.checkingTracksSearchUpperCase(); @AfterClass(alwaysRun=true)");
	}
}
