package epam.autotests.page_objects.sections;

import com.epam.commons.Timer;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.complex.Elements;
import com.epam.jdi.uitests.web.selenium.elements.complex.Tabs;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.jdi.uitests.web.settings.WebSettings;
import com.epam.web.matcher.testng.Assert;
import epam.autotests.utils.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static com.epam.jdi.uitests.web.settings.WebSettings.getJSExecutor;
import static epam.autotests.page_objects.site.NGB_Site.variationInfoWindow;
import static org.testng.Assert.assertTrue;


public class VariationInfoModalWindow extends Section {

    @FindBy(xpath = ".//div[@layout-align = 'start stretch']/div[@class='ngb-variant-identifier layout-padding ng-binding']")
    private Label id;
    @FindBy(xpath = ".//md-pagination-wrapper/md-tab-item")
    private Tabs<VariationInfoModalWindow.AnnotationsTabs> annotationsTabs;
    @FindBy(xpath = ".//ngb-variant-info//div[contains(@class, 'md-variant-property-title')]")
    private Elements<Label> infoPropertiesLabels;

    public static void waitVisualizer(String xPath) {
        WebDriverWait wait = new WebDriverWait(WebSettings.getDriver(), 30);
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xPath)));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.isFalse(e.toString().isEmpty(), "Couldn't load visualizer tooltip");
        }
    }

    public String getId() {
        return id.getText();
    }

    private Label getProperties(String propTitle) {
        for (Label infoPropertiesLabel : infoPropertiesLabels) {
            if (infoPropertiesLabel.getText().equals(propTitle)) {
                return infoPropertiesLabel;
            }
        }
        return null;
    }

    public String getPropertySingleValue(String Label) {
        return getProperties(Label).get(By.xpath("..//*[contains(@class,'md-variant-property-single-value')]")).getText();
    }

    private void waitTrackLoading() {
        WebDriverWait wait = new WebDriverWait(WebSettings.getDriver(), 60);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//md-progress-circular")));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.isFalse(e.toString().isEmpty(), "Couldn't load loader");
        }
    }

    public void savePictureVCF(String PathName) {
        String name = TableRow.variationName;
        variationInfoWindow.selectTab(VariationInfoModalWindow.AnnotationsTabs.VISUALIZER);
        String xPath = "'//*[@id=\"cnv\"]/canvas'";
        try {
            savePicture(PathName, name, xPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savePicture(String PathName, String name, String xPath) {
        waitTrackLoading();
        Timer.sleep(1000);
        File[] goldenimagearray = new File(TestBase.CurrentDir() + "\\src\\main\\resources\\golden_images").listFiles();
        String goldenimagestring = Arrays.toString(goldenimagearray).replace(TestBase.CurrentDir() + "\\src\\main\\resources\\golden_images\\", "").trim().toUpperCase();
        if (goldenimagestring.contains(name.toUpperCase() + ".PNG")) {
            getDriver().manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
            String imgstring = null;
            try {
                imgstring = getJSExecutor().executeScript("function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}; " +
                        "return (getElementByXpath( " + xPath + " )).toDataURL('image/png').substring(22);").toString();
            } catch (Exception e) {
                assertTrue(e.toString().isEmpty(), "JS execution error");
            }
            byte[] imageBytes = DatatypeConverter.parseBase64Binary(imgstring);
            try {
                FileOutputStream fos = new FileOutputStream(new File(PathName + name + ".png"));
                fos.write(imageBytes);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            comparisonData(TestBase.CurrentDir(), name);
        }
    }

    private void comparisonData(String PathName, String name) {
        try {
            String test = PathName + "\\src\\main\\resources\\diff\\perceptualdiff.exe "
                    + PathName + "\\target\\" + name + ".png "
                    + PathName + "\\src\\main\\resources\\golden_images\\" + name + ".png " +
                    "-output " + PathName + "\\.logs\\images\\" + name + "_different.png";
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", test);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String message = r.readLine();
                assertTrue(message.isEmpty(), message);
            } catch (Exception e) {
                AssertionError fail;
            }
        } catch (IOException e) {
            AssertionError fail;
        }
    }

    public void closeWindow() {
        int counter = 0;
        while (isDisplayed() && counter < 10) {
            id.clickCenter();
            getWebElement().sendKeys(Keys.ESCAPE);
            Timer.sleep(1000);
            counter++;
        }
        if (counter >= 10) {
            Assert.isFalse(isDisplayed(), "Couldn't close modal window");
        }
    }

    private void selectTab(VariationInfoModalWindow.AnnotationsTabs tabName) {
        annotationsTabs.select(tabName);
    }

    private String getQualityValue() {
        return getProperties("Quality").get(By.xpath(".//parent::div/following-sibling::div/div")).getText();
    }

    public boolean isQualityWithinRange(String[] range) {
        selectTab(VariationInfoModalWindow.AnnotationsTabs.INFO);
        float fQualityValue = Float.parseFloat(getQualityValue());
        switch (range.length) {
            case 1:
                return fQualityValue >= Float.parseFloat(range[0]);
            case 2:
                if (range[0] == null) {
                    return fQualityValue <= Float.parseFloat(range[1]);
                } else {
                    return fQualityValue >= Float.parseFloat(range[0]) && fQualityValue <= Float.parseFloat(range[1]);
                }
            default:
                throw new IllegalArgumentException("Wrong count of parameters");
        }
    }

    public enum AnnotationsTabs {
        VISUALIZER, INFO
    }
}
