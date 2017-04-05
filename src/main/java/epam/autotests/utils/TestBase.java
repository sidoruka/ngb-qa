package epam.autotests.utils;

import com.epam.jdi.uitests.web.settings.WebSettings;
import com.epam.jdi.uitests.web.testng.testRunner.TestNGBase;
import epam.autotests.page_objects.site.NGB_Site;
import org.joda.time.LocalDate;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.jdi.uitests.core.settings.JDISettings.logger;
import static com.epam.jdi.uitests.web.selenium.elements.composite.WebSite.init;
import static epam.autotests.page_objects.site.NGB_Site.mainPage;


public abstract class TestBase extends TestNGBase {

	@BeforeSuite(alwaysRun = true)
	public static void setUp() throws Exception {
		WebSettings.useDriver(() -> mainPage.remoteDriver());
		init(NGB_Site.class);
		mainPage.open();
		moveWindowSizeToCenterScreeen(1280,1024);
		logger.info("Run Tests");
	}

	@BeforeClass
	public static void openViewPage() throws Exception{
		/*if(!NGB_Site.isPageOpened("View"))
			NGB_Site.mainPage.selectLastVersion();*/
	}

	public static boolean isExpressionMatched(String regexp, String string){
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(string);
		return matcher.find();
	}

	@AfterSuite(alwaysRun=true)
	public static void killDriver(){
		WebSettings.getDriver().quit();
	}

	public static int compareDates(String date1, String date2){
		LocalDate dt1, dt2;
		dt1 = getLocalDateFromString(date1, "-");
		dt2 = getLocalDateFromString(date2, "-");
		/*int[] dt1, dt2;
		dt1 = Arrays.asList(date1.split("-")).stream().mapToInt(Integer::parseInt).toArray();
		dt2 = Arrays.asList(date2.split("-")).stream().mapToInt(Integer::parseInt).toArray();
		
		LocalDate dt3 = new LocalDate(dt1[2], dt1[0], dt1[1]);
		LocalDate dt4 = new LocalDate(dt2[2], dt2[0], dt2[1]);*/
		return dt1.compareTo(dt2);
	}

	public static LocalDate getLocalDateFromString(String date, String delimiter){
		int[] dt = Arrays.asList(date.split(delimiter)).stream().mapToInt(Integer::parseInt).toArray();
		return new LocalDate(dt[2], dt[0], dt[1]);
	}



	public static int[] intListToIntArray(List<Integer> list){
		int[] ret = new int[list.size()];
		for(int i = 0;i < ret.length;i++)
			ret[i] = list.get(i);
		return ret;
	}

	public static boolean compareStringArrays(String[] array1, String[] array2){
		if(array1.length != array2.length)
			return false;
		for (int i = 0; i < array2.length; i++) {
			if(!array1[i].toLowerCase().equals(array2[i].toLowerCase()))
				return false;
		}
		return true;
	}

	public static boolean compareListOfArrays(List<String[]> list1, List<String[]> list2){
		if(list1.size() != list2.size())
			return false;
		for (int i = 0; i < list1.size(); i++) {
			if(!compareStringArrays(list1.get(i), list2.get(i)))
				return false;
		}
		return true;
	}

	public static void moveWindowSizeToCenterScreeen(int w, int h) {
		Dimension maxiwin =mainPage.getDriver().manage().window().getSize();
		if(w<maxiwin.getWidth() && h<maxiwin.getHeight()) {
			mainPage.getDriver().manage().window().setSize(new Dimension(w, h));
			mainPage.getDriver().manage().window().setPosition(new Point((maxiwin.getWidth() - w) / 2, (maxiwin.getHeight() - h) / 2));
		}
	}

	public static String CurrentDir() {
		File currentDirFile = new File(".");
		String helper = currentDirFile.getAbsolutePath();
		return helper.substring(0, helper.length() - 2);
	}
}