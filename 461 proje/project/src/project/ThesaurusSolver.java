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

public class ThesaurusSolver {
	WebDriver driver;
	public ThesaurusSolver()
	{
	 	
	}
	public ArrayList<String> search(String s, int wordSize)
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\USER\\Desktop\\chromedriver.exe");
    	driver = new ChromeDriver();
    	driver.manage().window().setPosition(new Point(-2000, 0));
		System.out.println("Looking at synonyms of " + s + " on thesaurus.com.");
		s = s.replaceAll(" ", "%20");
    	s = s.replaceAll("'", "%27");
    	s = s.replaceAll("\\?", "%3F");
    	s = s.replaceAll(",", "%2C");
    	s = s.replaceAll("!", "%21");
    	s = s.replaceAll(":", "%3A");
        String searchUrl = "http://www.thesaurus.com/browse/" + s;
        driver.get(searchUrl);
        
        ArrayList<String> result = new ArrayList<String>();
        List<WebElement> tabs = driver.findElements(By.className("pos-tab"));
        for(int i=0; i<tabs.size(); i++){
        	System.out.println((i+1) + ". Looking at synonyms that mean " + tabs.get(i).getText());
        	tabs.get(i).click();
        	List<WebElement> wordRows = driver.findElement(By.id("filters-" +i)).findElements(By.className("text"));
        	
            for(int j= 0;j < wordRows.size()/2+1;j++)
            {
            	if(wordRows.get(j).getText().replaceAll("[^a-zA-Z]", "").length() <= wordSize)
            		result.add(wordRows.get(j).getText().replaceAll("[^a-zA-Z]", "").toLowerCase());
            }
        }
        /*List<WebElement> wordRows = driver.findElements(By.className("text"));
        for(int i= 1;i < wordRows.size()/2+1;i++)
        {
        	System.out.print(wordRows.get(i).getText()+ "\t");
        }*/
        Set<String> hs = new HashSet<>();
        hs.addAll(result);
        result.clear();
        result.addAll(hs);
        driver.close();
		return result;
	}
/*	public static void main(String[] args) {
			ThesaurusSolver ts = new ThesaurusSolver();
			ts.search("harsh",5);
	}*/
}
