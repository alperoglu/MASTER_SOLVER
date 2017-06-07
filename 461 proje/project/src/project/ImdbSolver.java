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

public class ImdbSolver {
	WebDriver driver;
	public ImdbSolver()
	{
		
	}
	public ArrayList<String> search(String s, int wordSize)
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\USER\\Desktop\\chromedriver.exe");
    	driver = new ChromeDriver();
    	driver.manage().window().setPosition(new Point(-2000, 0));
		System.out.println("Searching IMDb for " + s);
		s = s.replaceAll(" ", "%20");
    	s = s.replaceAll("'", "%27");
    	s = s.replaceAll("\\?", "%3F");
    	s = s.replaceAll(",", "%2C");
    	s = s.replaceAll("!", "%21");
    	s = s.replaceAll(":", "%3A");
        String searchUrl = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + s + "&s=all";
        driver.get(searchUrl);
        
        ArrayList<String> result = new ArrayList<String>();
        List<WebElement> results = driver.findElements(By.className("result_text"));
        for(int i=0; i<results.size(); i++){
        	String[] temp =results.get(i).findElement(By.tagName("a")).getText().split("\\s+");
        	for(int j=0; j<temp.length; j++){
        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
        		if(temp[j].length()>0 && temp[j].length()<=wordSize )
        			result.add(temp[j]);
        	}
        	if(results.get(i).findElement(By.tagName("a")).getText().replaceAll("[^a-zA-Z]", "").length() <= wordSize)
        		result.add(results.get(i).findElement(By.tagName("a")).getText().replaceAll("[^a-zA-Z]", "").toLowerCase());
        	
        }
        Set<String> hs = new HashSet<>();
        hs.addAll(result);
        result.clear();
        result.addAll(hs);
        driver.close();
		return result;
	}
	public ArrayList<String> search(String s, String type, int wordSize) // type = ch, tt, nm, kw, co
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\USER\\Desktop\\chromedriver.exe");
    	driver = new ChromeDriver();
		System.out.println("Searching IMDb for " + s + "of type: "+ type);
		s = s.replaceAll(" ", "%20");
    	s = s.replaceAll("'", "%27");
    	s = s.replaceAll("\\?", "%3F");
    	s = s.replaceAll(",", "%2C");
    	s = s.replaceAll("!", "%21");
    	s = s.replaceAll(":", "%3A");
        String searchUrl = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + s + "&s=all#" + type;
        driver.get(searchUrl);
        
        List<WebElement> results = driver.findElements(By.className("findSection"));
        ArrayList<String> result = new ArrayList<String>();
        
        List<WebElement> found = new ArrayList<WebElement>();
        for(int i=0; i<results.size(); i++){
        	if(results.get(i).findElements(By.name(type)).size()>0){
        		found = results.get(i).findElements(By.className("result_text"));
        	}
        }
        for(int i=0; i<found.size(); i++){
        	String[] temp =found.get(i).findElement(By.tagName("a")).getText().split("\\s+");
        	for(int j=0; j<temp.length; j++){
        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
        		if(temp[j].length()>0 && temp[j].length()<=wordSize )
        			result.add(temp[j]);
        	}
        	if(found.get(i).findElement(By.tagName("a")).getText().replaceAll("[^a-zA-Z]", "").length() <= wordSize)
        		result.add(found.get(i).findElement(By.tagName("a")).getText().replaceAll("[^a-zA-Z]", "").toLowerCase());
        	
        }
        Set<String> hs = new HashSet<>();
        hs.addAll(result);
        result.clear();
        result.addAll(hs);
        driver.close();
		return result;
	}
/*	public static void main(String[] args) {
	ImdbSolver solver = new ImdbSolver();
	solver.search("barkin","co", 5);
	//answer = ts.search("Kanye West", "artists");
	}*/
}
