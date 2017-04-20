package epam.autotests;

import com.epam.web.matcher.testng.Assert;
import epam.autotests.utils.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static epam.autotests.page_objects.enums.FiltersGroups.GENE;
import static epam.autotests.page_objects.enums.FiltersGroups.TYPE_VARIANT;
import static epam.autotests.page_objects.enums.Views.RESTORE_DEFAULT;
import static epam.autotests.page_objects.enums.Views.VARIANTS;
import static epam.autotests.page_objects.site.NGB_Site.mainPage;
import static epam.autotests.page_objects.site.NGB_Site.projectPage;


public class CheckingFilterPanelTest extends TestBase {

    @BeforeClass
    public void preparations() {
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        mainPage.datasetsPanel.select("/SV_Sample1/sample_1-lumpy.vcf", true);
        mainPage.datasetsPanel.select("/SV_Sample2/sample_2-lumpy.vcf", true);
//        mainPage.datasetsPanel.select("/SV_Sample1/GRCh38_Genes", true);
        projectPage.openPanel(VARIANTS);
        System.out.println("=== CheckingFilterPanelTest.preparation(); @BeforeClass");
    }

    @Test(priority = 1)
    public void checkingTypeOfVarGroup() {
        //#10.5
        projectPage.openFilter();
        projectPage.filterPanel.selectFilter(TYPE_VARIANT, "INV");
        Assert.isTrue(projectPage.variantsPanel.variantsTable.isColumnContainOnlyOneValue("INV"));
        projectPage.openFilter();
        projectPage.filterPanel.selectFilter(TYPE_VARIANT, "DEL");
        Assert.isTrue(projectPage.variantsPanel.variantsTable.isColumnContainOnlyOneValue("DEL"));
        projectPage.filterPanel.selectFilter(TYPE_VARIANT, "BND");
        Assert.isTrue(projectPage.variantsPanel.variantsTable.isColumnContainOnlyOneValue("BND"));
        projectPage.filterPanel.selectFilter(TYPE_VARIANT, "DUP");
        Assert.isTrue(projectPage.variantsPanel.variantsTable.isColumnContainOnlyOneValue("DUP"));
        projectPage.filterPanel.clearFilterPanel();
        System.out.println("=== CheckingFilterPanelTest.checkingTypeOfVarGroup(); @Test(priority=1)");
    }

    @Test(priority = 3)
    public void checkGeneGroup() {
        projectPage.openFilter();
        projectPage.filterPanel.selectFilter(GENE, "EML4");
        projectPage.variantsPanel.checkVarGenes("EML4");
        projectPage.filterPanel.deleteAllAddedGenes();
        projectPage.openFilter();
        projectPage.filterPanel.selectFilter(GENE, "BCOR");
        projectPage.variantsPanel.checkVarGenes("BCOR");
        projectPage.filterPanel.deleteAllAddedGenes();
        projectPage.openFilter();
        projectPage.filterPanel.selectFilter(GENE, "BCOR", "EML4");
        projectPage.variantsPanel.checkVarGenes("BCOR", "EML4");
        System.out.println("=== CheckingFilterPanelTest.checkGeneGroup(); @Test(priority=3)");
    }

    @AfterClass
    public void unSelectAllDatasets() {
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        System.out.println("=== CheckingFilterPanelTest.unSelectAllDatasets(); @AfterMethod");
    }
}