package epam.autotests;

import epam.autotests.page_objects.sections.VariationInfoModalWindow;
import org.junit.Ignore;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.epam.web.matcher.testng.Assert;

import epam.autotests.page_objects.enums.VarTableColumns;
import epam.autotests.utils.TestBase;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static epam.autotests.page_objects.enums.ProjectPagePreconditions.*;
import static epam.autotests.page_objects.site.NGB_Site.*;

import static epam.autotests.page_objects.enums.Views.*;
import static epam.autotests.page_objects.enums.VarTableColumns.*;
import static epam.autotests.page_objects.enums.SortingTypes.*;
import static epam.autotests.page_objects.enums.ExternalDataFiles.*;


@Features("Data from variation table comparing")
public class CheckingProjectPageTest extends TestBase {

    private final String projectName = "VCF_9.1";

    @Test(priority = 2)
    @Stories("Check column")
    public void variationPanel() {
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        isInState(OPEN_DATASETS_PANEL);
        //NGB_Site.openPageByUrl("GRCh38/?tracks=[{\"b\":\"GRCh38\",\"p\":\"VCF_9.1\"},{\"b\":\"Homo_sapiens.gtf\",\"p\":\"VCF_9.1\"},{\"b\":\"sample_1-lumpy.vcf\",\"p\":\"VCF_9.1\"},{\"b\":\"sample_1-manta.vcf\",\"p\":\"VCF_9.1\"},{\"b\":\"sample_2-lumpy.vcf\",\"p\":\"VCF_9.1\"},{\"b\":\"sample_2-manta.vcf\",\"p\":\"VCF_9.1\"}]");
        VariationInfoModalWindow.waitVisualizer("//*[@id=\"1489086710371-grid-container\"]");
        mainPage.datasetsPanel.select("/SV_Sample1/sample_1-lumpy.vcf");
        mainPage.datasetsPanel.select("/SV_Sample2/sample_2-lumpy.vcf");
        isInState(OPEN_VARIANTS_PANEL);
        projectPage.variantsPanel.checkSetOfColumns("Type", "Chr", "Gene", "Position", "Info");
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.unSelect("/SV_Sample1/sample_1-lumpy.vcf");
        mainPage.datasetsPanel.unSelect("/SV_Sample2/sample_2-lumpy.vcf");
        System.out.println("=== CheckingProjectPageTest.variationPanel(); @Test(priority=2)");
    }
    @Stories("Check sample_2-lumpy.vcf")
    @Test(priority = 3)
    public void comparisonDataFromFileAndTableSample2lumpy() {
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.select("/SV_Sample2/sample_2-lumpy.vcf");
        isInState(OPEN_VARIANTS_PANEL);
        projectPage.variantsPanel.variantsTable.setSorting(POSITION, ASC);
        projectPage.variantsPanel.checkDataWithFile(SMPL2_LUMPY);
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.unSelect("/SV_Sample2/sample_2-lumpy.vcf");
        System.out.println("=== CheckingProjectPageTest.comparisonDataFromFileAndTableSample2lumpy(); @Test(priority=3)");
    }
    @Ignore
    @Stories("Check sample_2-manta.vcf")
    @Test(priority = 4)
    public void comparisonDataFromFileAndTableSample2manta() {
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.select("/VCF_9.1/sample_2-manta.vcf");
        isInState(OPEN_VARIANTS_PANEL);
        projectPage.variantsPanel.variantsTable.setSorting(POSITION, ASC);
        projectPage.variantsPanel.checkDataWithFile(SMPL2_MANTA);
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.unSelect("/VCF_9.1/sample_2-manta.vcf");
        System.out.println("=== CheckingProjectPageTest.comparisonDataFromFileAndTableSample2manta(); @Test(priority=4)");
    }
    @Ignore
    @Stories("Check sample_1-manta.vcf")
    @Test(priority = 5)
    public void comparisonDataFromFileAndTableSample1manta() {
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.select("/VCF_9.1/sample_1-manta.vcf");
        isInState(OPEN_VARIANTS_PANEL);
        projectPage.variantsPanel.variantsTable.setSorting(POSITION, ASC);
        projectPage.variantsPanel.checkDataWithFile(SMPL1_MANTA);
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.unSelect("/VCF_9.1/sample_1-manta.vcf");
        System.out.println("=== CheckingProjectPageTest.comparisonDataFromFileAndTableSample1manta(); @Test(priority=5)");
    }
    @Stories("Check sample_1-lumpy.vcf")
    @Test(priority = 6)
    public void comparisonDataFromFileAndTableSample1lumpy() {
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.select("/SV_Sample1/sample_1-lumpy.vcf");
        isInState(OPEN_VARIANTS_PANEL);
        projectPage.variantsPanel.variantsTable.setSorting(POSITION, ASC);
        projectPage.variantsPanel.checkDataWithFile(SMPL1_LUMPY);
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.unSelect("/SV_Sample1/sample_1-lumpy.vcf");
        System.out.println("=== CheckingProjectPageTest.comparisonDataFromFileAndTableSample1lumpy(); @Test(priority=6)");
    }
    @Stories("Check sorting")
    @Test(priority = 7)
    public void checkingVariantsSorting() {
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.select("/SV_Sample1/sample_1-lumpy.vcf");
        mainPage.datasetsPanel.select("/SV_Sample2/sample_2-lumpy.vcf");
        isInState(OPEN_VARIANTS_PANEL);
        projectPage.variantsPanel.variantsTable.setSorting(TYPE, ASC);
        Assert.isTrue(projectPage.variantsPanel.variantsTable.isColumnSorted(TYPE, false, true));
        projectPage.variantsPanel.variantsTable.setSorting(TYPE, DESC);
        Assert.isTrue(projectPage.variantsPanel.variantsTable.isColumnSorted(TYPE, false, false));

        projectPage.variantsPanel.variantsTable.setSorting(POSITION, ASC);
        Assert.isTrue(projectPage.variantsPanel.variantsTable.isColumnSorted(POSITION, true, true));
        projectPage.variantsPanel.variantsTable.setSorting(POSITION, DESC);
        Assert.isTrue(projectPage.variantsPanel.variantsTable.isColumnSorted(POSITION, true, false));

        projectPage.variantsPanel.variantsTable.setSorting(VarTableColumns.GENE, ASC);
        Assert.isTrue(projectPage.variantsPanel.variantsTable.isColumnSorted(VarTableColumns.GENE, false, true));
        projectPage.variantsPanel.variantsTable.setSorting(VarTableColumns.GENE, DESC);
        Assert.isTrue(projectPage.variantsPanel.variantsTable.isColumnSorted(VarTableColumns.GENE, false, false));

        projectPage.variantsPanel.variantsTable.setSorting(CHR, ASC);
        Assert.isTrue(projectPage.variantsPanel.variantsTable.isColumnSorted(CHR, false, true));
        projectPage.variantsPanel.variantsTable.setSorting(CHR, DESC);
        Assert.isTrue(projectPage.variantsPanel.variantsTable.isColumnSorted(CHR, false, false));
        isInState(OPEN_DATASETS_PANEL);
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        System.out.println("=== CheckingProjectPageTest.checkingVariantsSorting(); @Test(priority=7)");
    }

    @AfterClass(alwaysRun=true)
    public void resetBrowser(){
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        System.out.println("=== CheckingBamTracksSorting.resetBrowser(); @AfterClass(alwaysRun=true)");;
    }
}