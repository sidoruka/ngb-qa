package epam.autotests;

import com.epam.jdi.uitests.web.settings.WebSettings;
import epam.autotests.page_objects.sections.VariationInfoModalWindow;
import epam.autotests.page_objects.site.NGB_Site;
import epam.autotests.utils.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

import java.util.ArrayList;

import static com.epam.jdi.uitests.core.preconditions.PreconditionsState.isInState;
import static com.epam.jdi.uitests.web.settings.WebSettings.getJSExecutor;
import static epam.autotests.page_objects.enums.ProjectPagePreconditions.OPEN_SETTING;
import static epam.autotests.page_objects.enums.SettingTabs.ALIGNMENTS;
import static epam.autotests.page_objects.enums.Views.RESTORE_DEFAULT;
import static epam.autotests.page_objects.site.NGB_Site.*;


@Title("Checking URls")
@Features("Checking Bam views opened by URL")
public class CheckingURls extends TestBase{
    String refPath = CurrentDir()+"\\target\\";
    String xPath="'/html/body/ui-view/div/div[2]/ngb-golden-layout/div/div/div/div[1]/div[2]/div/div/ngb-browser/ngb-tracks-view/div/div/div[2]/ngb-track[2]/div/div[3]/div/canvas'";
	@BeforeClass
	public void preparations(){
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
        isInState(OPEN_SETTING);
        if(settingWindow.isDisplayed()) {
            settingWindow.openTab(ALIGNMENTS);
            com.epam.commons.Timer.sleep(1000);
            settingWindow.uncheckDownsample();
            settingWindow.changeMaximumRange("50000");
            settingWindow.saveSettings();
            }
        else
        {
            Assert.fail("No settings menu on display");
        }
	System.out.println("=== CheckingBamReadsView.preparation(); @BeforeClass");
	}

    @Test(priority=0)
    @Stories("BAM track insert size and pair orientation, view as pair, no coverage by URL")
	public void checkingOfBAM_col_inssize_pairorient_aspair_by_URl() {
        ArrayList<String> tabsMainHandler = new ArrayList<String>(WebSettings.getDriver().getWindowHandles());
        String openedURL= "GRCh38/6/51294395/51295837?tracks=[{\"b\":\"GRCh38\",\"p\":\"sv_sample1\",\"h\":20,\"s\":{}},{\"b\":\"sample_1.bam\",\"p\":\"sv_sample1\",\"h\":500,\"s\":" +
                "{\"a\":true,\"c\":\"insertSizeAndPairOrientation\",\"c1\":false,\"d\":true,\"g1\":\"default\",\"i\":true,\"m\":true,\"r\":1,\"s1\":false,\"s2\":true,\"s3\":false,\"v1\":true}}]";
        NGB_Site.openPageByUrl(openedURL);
        String location = "6:51294395-51295837";
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        String name = location_for_name + "_col_inssize_pairorient_aspair";
        try{
            VariationInfoModalWindow.waitVisualizer(xPath);
            variationInfoWindow.savePicture(refPath, name, xPath);}
        catch (AssertionError fail) {
            getJSExecutor().executeScript("open(location, '_self').close();");
            WebSettings.getDriver().switchTo().window(tabsMainHandler.get(tabsMainHandler.size()-1));
            Assert.fail(fail.toString());
        }
        getJSExecutor().executeScript("open(location, '_self').close();");
        WebSettings.getDriver().switchTo().window(tabsMainHandler.get(tabsMainHandler.size()-1));
        System.out.println("=== CheckingBamReadsView.checkingOfBAM_col_inssize_pairorient_aspair_by_URl(); @Test(priority=0)");
    }

    @Test(priority=1)
    @Stories("BAM track collapsed URl")
    public void checkingOfBAM_collapsed_URl() {
        ArrayList<String> tabsMainHandler = new ArrayList<String>(WebSettings.getDriver().getWindowHandles());
        String openedURL= "GRCh38/2/29224759/29224804?tracks=[{\"b\":\"GRCh38\",\"p\":\"sv_sample1\",\"h\":20,\"s\":{}},{\"b\":\"sample_1.bam\",\"p\":\"sv_sample1\",\"h\":500,\"s\":" +
                "{\"a\":true,\"c\":\"noColor\",\"c1\":true,\"d\":true,\"g1\":\"default\",\"i\":true,\"m\":true,\"r\":0,\"s1\":false,\"s2\":true,\"s3\":false,\"v1\":false}}]";
        NGB_Site.openPageByUrl(openedURL);
        String location = "2:29224759 - 29224804";
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        String name = location_for_name + "_collapsed";
        VariationInfoModalWindow.waitVisualizer(xPath);
        try{
            VariationInfoModalWindow.waitVisualizer(xPath);
            variationInfoWindow.savePicture(refPath, name, xPath);}
        catch (AssertionError fail) {
            getJSExecutor().executeScript("open(location, '_self').close();");
            WebSettings.getDriver().switchTo().window(tabsMainHandler.get(tabsMainHandler.size()-1));
            Assert.fail(fail.toString());
        }
        getJSExecutor().executeScript("open(location, '_self').close();");
        WebSettings.getDriver().switchTo().window(tabsMainHandler.get(tabsMainHandler.size()-1));
        System.out.println("=== CheckingBamReadsView.checkingOfBAM_collapsed_URl(); @Test(priority=1)");
    }

    @Test(priority=2)
    @Stories("BAM track color - first in pair strand, Show mismatched bases disabled by URl")
    public void checkingOfBAM_color_and_group_by_first_in_pair_no_missbase_URl() {
        ArrayList<String> tabsMainHandler = new ArrayList<String>(WebSettings.getDriver().getWindowHandles());
        String openedURL= "GRCh38/6/60544758/60544803?tracks=[{\"b\":\"GRCh38\",\"p\":\"sv_sample1\",\"h\":20,\"s\":{}},{\"b\":\"sample_1.bam\",\"p\":\"sv_sample1\",\"h\":500,\"s\":" +
                "{\"a\":true,\"c\":\"firstInPairStrand\",\"c1\":false,\"d\":true,\"g1\":\"firstInPair\",\"i\":true,\"m\":false,\"r\":0,\"s1\":false,\"s2\":true,\"s3\":false,\"v1\":false}}]";
        NGB_Site.openPageByUrl(openedURL);
        String location = "6:60544758-60544803";
        String location_for_name = location.replaceAll(":", "_").replaceAll("\\s", "");
        String name = location_for_name + "_color_and_group_by_first_in_pair_no_missbase";
        VariationInfoModalWindow.waitVisualizer(xPath);
        try{
            VariationInfoModalWindow.waitVisualizer(xPath);
            variationInfoWindow.savePicture(refPath, name, xPath);}
        catch (AssertionError fail) {
            getJSExecutor().executeScript("open(location, '_self').close();");
            WebSettings.getDriver().switchTo().window(tabsMainHandler.get(tabsMainHandler.size()-1));
            Assert.fail(fail.toString());
        }
        getJSExecutor().executeScript("open(location, '_self').close();");
        WebSettings.getDriver().switchTo().window(tabsMainHandler.get(tabsMainHandler.size()-1));
        System.out.println("=== CheckingBamReadsView.checkingOfBAM_color_and_group_by_first_in_pair_no_missbase_URl(); @Test(priority=2)");
    }

    @Test(priority=3)
    @Stories("BAM track color - By pair orientation, Show mismatched bases disabled by URL")
    public void checkingOfBAM_col_and_group_pair_orient_missbase_URl() {
        ArrayList<String> tabsMainHandler = new ArrayList<String>(WebSettings.getDriver().getWindowHandles());
        String openedURL= "GRCh38/6/51294990/51295090?tracks=[{\"b\":\"GRCh38\",\"p\":\"sv_sample1\",\"h\":20,\"s\":{}},{\"b\":\"sample_1.bam\",\"p\":\"sv_sample1\",\"h\":500,\"s\":" +
                "{\"a\":true,\"c\":\"pairOrientation\",\"c1\":false,\"d\":true,\"g1\":\"pairOrientation\",\"i\":true,\"m\":false,\"r\":1,\"s1\":false,\"s2\":true,\"s3\":false,\"v1\":false}}]";
        NGB_Site.openPageByUrl(openedURL);
        String location = "6:51294990-51295090";
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        String name = location_for_name + "_col_and_group_pair_orient_missbase";
        VariationInfoModalWindow.waitVisualizer(xPath);
        try{
            VariationInfoModalWindow.waitVisualizer(xPath);
            variationInfoWindow.savePicture(refPath, name, xPath);}
        catch (AssertionError fail) {
            getJSExecutor().executeScript("open(location, '_self').close();");
            WebSettings.getDriver().switchTo().window(tabsMainHandler.get(tabsMainHandler.size()-1));
            Assert.fail(fail.toString());
        }
        getJSExecutor().executeScript("open(location, '_self').close();");
        WebSettings.getDriver().switchTo().window(tabsMainHandler.get(tabsMainHandler.size()-1));
        System.out.println("=== CheckingBamReadsView.checkingOfBAM_col_and_group_pair_orient_missbase(); @Test(priority=3)");
    }

    @Test(priority=4)
    @Stories("BAM track by read strand by URL")
    public void checkingOfBAM_color_and_group_by_read_strand_no_missbase_URl() {
        ArrayList<String> tabsMainHandler = new ArrayList<String>(WebSettings.getDriver().getWindowHandles());
        String openedURL= "GRCh38/6/60544758/60544803?tracks=[{\"b\":\"GRCh38\",\"p\":\"sv_sample1\",\"h\":20,\"s\":{}},{\"b\":\"sample_1.bam\",\"p\":\"sv_sample1\",\"h\":500,\"s\":" +
                "{\"a\":true,\"c\":\"readStrand\",\"c1\":false,\"d\":true,\"g1\":\"readStrand\",\"i\":true,\"m\":false,\"r\":0,\"s1\":false,\"s2\":true,\"s3\":false,\"v1\":false}}]";
        NGB_Site.openPageByUrl(openedURL);
        String location = "6:60544758-60544803";
        String location_for_name = location.replaceAll(":","_").replaceAll("\\s","");
        String name = location_for_name + "_color_and_group_by_read_strand_no_missbase";
        VariationInfoModalWindow.waitVisualizer(xPath);
        try{
            VariationInfoModalWindow.waitVisualizer(xPath);
            variationInfoWindow.savePicture(refPath, name, xPath);}
        catch (AssertionError fail) {
            getJSExecutor().executeScript("open(location, '_self').close();");
            WebSettings.getDriver().switchTo().window(tabsMainHandler.get(tabsMainHandler.size()-1));
            Assert.fail(fail.toString());
        }
        getJSExecutor().executeScript("open(location, '_self').close();");
        WebSettings.getDriver().switchTo().window(tabsMainHandler.get(tabsMainHandler.size()-1));
        System.out.println("=== CheckingBamReadsView.checkingOfBAM_color_and_group_by_read_strand_no_missbase(); @Test(priority=4)");
    }



	@AfterClass(alwaysRun=true)
	public void resetBrowser(){
        ArrayList<String> tabs = new ArrayList<String>(WebSettings.getDriver().getWindowHandles());
        WebSettings.getDriver().switchTo().window(tabs.get(tabs.size()-1));
        projectPage.closeAllTracks();
        projectPage.openPanel(RESTORE_DEFAULT);
		System.out.println("=== CheckingBamReadsView.resetBrowser(); @AfterClass(alwaysRun=true)");
	}
}
