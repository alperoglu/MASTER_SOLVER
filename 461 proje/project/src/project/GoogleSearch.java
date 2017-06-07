package project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleSearch {
	WebDriver driver;
	ArrayList<String> unwords = new ArrayList<String>() {{
	    add("to");
	    add("for");
	    add("a");
	    add("an");
	    add("at");
	    add("the");
	    add("of");
	    add("and");
	    add("is");
	    add("was");
	}};
	public GoogleSearch()
	{
		
	}
	public ArrayList<String> search(String s, int wordSize)
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\USER\\Desktop\\chromedriver.exe");
    	driver = new ChromeDriver();
    	driver.manage().window().setPosition(new Point(-2000, 0));
		System.out.println("Searching GOOGLE for " + s);
		String searchUrl = "http://www.google.com" ;
        driver.get(searchUrl);
        List<WebElement> element = driver.findElements(By.name("q"));
        element.get(0).sendKeys(s + "\n"); 
        element.get(0).submit();
        
        ArrayList<String> result = new ArrayList<String>();

        // wait until the google page shows the result
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                  .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
        System.out.println("Looking at headers of search results.");
        List<WebElement> headers = driver.findElements(By.className("r"));
        for(int i=0; i<headers.size();i++){
        	String[] temp = headers.get(i).findElement(By.tagName("a")).getText().split("\\s+");
        	for(int j=0; j<temp.length; j++){
        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
        		if(temp[j].length()>0 && temp[j].length()<=wordSize && !unwords.contains(temp[j]))
        			result.add(temp[j]);
        	}
        }
        System.out.println("Looking at texts of search results.");       
        List<WebElement> texts = driver.findElements(By.className("st"));
        for(int i=0; i<texts.size();i++){
        	String[] temp =texts.get(i).getText().split("\\s+");
        	for(int j=0; j<temp.length; j++){
        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
        		if(temp[j].length()>0 && temp[j].length()<=wordSize && !unwords.contains(temp[j]))
        			result.add(temp[j]);
        	}
        }
        
        Set<String> hs = new HashSet<>();
        hs.addAll(result);
        result.clear();
        result.addAll(hs);
        driver.close();
		return result;
	}
/*	public static void main(String[] args) {
		GoogleSearch solver = new GoogleSearch();
		solver.search("actress barkin", 5);
	}*/
}
