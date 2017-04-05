package epam.autotests;

import com.epam.web.matcher.testng.Assert;
import epam.autotests.page_objects.enums.VarTableColumns;
import epam.autotests.utils.TestBase;
import junit.framework.AssertionFailedError;
import org.aopalliance.aop.AspectException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static epam.autotests.page_objects.enums.ExternalDataFiles.*;
import static epam.autotests.page_objects.enums.FiltersGroups.ACTIVE_VCF;
import static epam.autotests.page_objects.enums.ProjectPagePreconditions.*;
import static epam.autotests.page_objects.enums.SortingTypes.ASC;
import static epam.autotests.page_objects.enums.SortingTypes.DESC;
import static epam.autotests.page_objects.enums.VarTableColumns.*;
import static epam.autotests.page_objects.enums.Views.FILTER;
import static epam.autotests.page_objects.enums.Views.RESTORE_DEFAULT;
import static epam.autotests.page_objects.site.NGB_Site.*;


@Features("Check variation info picture")
public class CheckingVariations extends TestBase {
    @BeforeClass
    public void preparations(){
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.select("/SV_Sample1/GRCh38_Genes");
        System.out.println("=== CheckingBamTracksSorting.preparations(); @BeforeClass");

    }
    @Stories("Variation Info sample_1-lumpy.vcf")
    @Test(priority = 0)
    public void checkingVariantssample1Lumpy() {
        mainPage.datasetsPanel.select("/SV_Sample1/sample_1-lumpy.vcf");
        isInState(OPEN_VARIANTS_PANEL);
        try {
        projectPage.variantsPanel.checkPictWithFile(CurrentDir());
        }
        catch (AssertionError fail) {
            isInState(OPEN_DATASETS_PANEL);
            mainPage.datasetsPanel.unSelect("/SV_Sample1/sample_1-lumpy.vcf");
        }
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.unSelect("/SV_Sample1/sample_1-lumpy.vcf");
        System.out.println("=== CheckingBamTracksSorting.checkingVariantssample1Lumpy(); @Test(priority=0)");
    }

    @Stories("Variation Info sample_2-lumpy.vcf")
    @Test(priority = 1)
    public void checkingVariantssample2Lumpy() {
        mainPage.datasetsPanel.select("/SV_Sample2/sample_2-lumpy.vcf");
        isInState(OPEN_VARIANTS_PANEL);
        try {
            projectPage.variantsPanel.checkPictWithFile(CurrentDir());
        }
        catch (AssertionError fail)
        {
            isInState(OPEN_DATASETS_PANEL);
            mainPage.datasetsPanel.unSelect("/SV_Sample2/sample_2-lumpy.vcf");
        }
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.unSelect("/SV_Sample2/sample_2-lumpy.vcf");
        System.out.println("=== CheckingBamTracksSorting.checkingVariantssample2Lumpy(); @Test(priority=1)");
    }


    @Stories("Variation Info PIK3CA-E545K.vcf")
    @Test(priority = 2)
    public void checkingVariantsPIK3CAE545K() {
        mainPage.datasetsPanel.select("/PIK3CA-E545K-Sample/PIK3CA-E545K.vcf");
        isInState(OPEN_VARIANTS_PANEL);
        try {
        projectPage.variantsPanel.checkPictWithFile(CurrentDir());
        }
        catch (AssertionError fail) {
            isInState(OPEN_DATASETS_PANEL);
            mainPage.datasetsPanel.unSelect("/PIK3CA-E545K-Sample/PIK3CA-E545K.vcf");
        }
        isInState(OPEN_DATASETS_PANEL);
        mainPage.datasetsPanel.unSelect("/PIK3CA-E545K-Sample/PIK3CA-E545K.vcf");
        System.out.println("=== CheckingBamTracksSorting.checkingVariantsPIK3CAE545K(); @Test(priority=2)");
    }


    @AfterMethod(alwaysRun=true)
    public void resetBrowser(){
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        System.out.println("=== CheckingBamTracksSorting.resetBrowser(); @AfterClass(alwaysRun=true)");
    }
}