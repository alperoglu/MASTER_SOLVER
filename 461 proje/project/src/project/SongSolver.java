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

public class SongSolver {
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
	public SongSolver()
	{
		
	}
	public ArrayList<String> search(String s, int wordSize)
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\USER\\Desktop\\chromedriver.exe");
    	driver = new ChromeDriver();
    	driver.manage().window().setPosition(new Point(-2000, 0));
		System.out.println("Searching allmusic.com for " + s);
		s = s.replaceAll(" ", "%20");
    	s = s.replaceAll("'", "%27");
    	s = s.replaceAll("\\?", "%3F");
    	s = s.replaceAll(",", "%2C");
    	s = s.replaceAll("!", "%21");
    	s = s.replaceAll(":", "%3A");
        String searchUrl = "http://www.allmusic.com/search/all/" + s;
        driver.get(searchUrl);
        List<WebElement> names = driver.findElements(By.className("info"));
        ArrayList<String> result = new ArrayList<String>();
        
        for(int i = 0; i < names.size();i++)
        {
        	WebElement name = names.get(i).findElement(By.tagName("a"));
        	String[] temp =name.getText().split("\\s+");
        	for(int j=0; j<temp.length; j++){
        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
        		if(temp[j].length()>0 && temp[j].length()<=wordSize && !unwords.contains(temp[j]) )
        			result.add(temp[j]);
        	}
        	if(name.getText().replaceAll("[^a-zA-Z]", "").length() <= wordSize && !unwords.contains(name.getText()))
        		result.add(name.getText().replaceAll("[^a-zA-Z]", "").toLowerCase());
        }
        Set<String> hs = new HashSet<>();
        hs.addAll(result);
        result.clear();
        result.addAll(hs);
        driver.close();
		return result;
	}
	public ArrayList<String> search(String s, String type, int wordSize)
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\USER\\Desktop\\chromedriver.exe");
    	driver = new ChromeDriver();
    	driver.manage().window().setPosition(new Point(-2000, 0));
		System.out.println("Searching allmusic.com for " + s + " of type " + type);
		s = s.replaceAll(" ", "%20");
    	s = s.replaceAll("'", "%27");
    	s = s.replaceAll("\\?", "%3F");
    	s = s.replaceAll(",", "%2C");
    	s = s.replaceAll("!", "%21");
    	s = s.replaceAll(":", "%3A");
        String searchUrl = "http://www.allmusic.com/search/" + type +"/" + s;
        driver.get(searchUrl);
        
        ArrayList<String> result = new ArrayList<String>();
        
        if(type.equals("artists"))
        {
	        List<WebElement> names = driver.findElements(By.className("name"));
	        ArrayList<String> results = new ArrayList<String>();
	        for(int i = 0; i < names.size();i++)
	        {
	        	WebElement name = names.get(i).findElement(By.tagName("a"));
	        	results.add(name.getText());
	        	String[] temp =name.getText().split("\\s+");
	        	for(int j=0; j<temp.length; j++){
	        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
	        		if(temp[j].length()>0 && temp[j].length()<=wordSize && !unwords.contains(temp[j]) )
	        			result.add(temp[j]);
	        	}
	        	if(name.getText().replaceAll("[^a-zA-Z]", "").length() <= wordSize && !unwords.contains(name.getText()))
	        		result.add(name.getText().replaceAll("[^a-zA-Z]", "").toLowerCase());
	        }
        }
        else if(type.equals("albums"))
        {
	        List<WebElement> names = driver.findElements(By.className("title"));
	        ArrayList<String> results = new ArrayList<String>();
	        for(int i = 0; i < names.size();i++)
	        {
	        	WebElement name = names.get(i).findElement(By.tagName("a"));
	        	results.add(name.getText());
	        	String[] temp =name.getText().split("\\s+");
	        	for(int j=0; j<temp.length; j++){
	        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
	        		if(temp[j].length()>0 && temp[j].length()<=wordSize && !unwords.contains(temp[j]) )
	        			result.add(temp[j]);
	        	}
	        	if(name.getText().replaceAll("[^a-zA-Z]", "").length() <= wordSize && !unwords.contains(name.getText()))
	        		result.add(name.getText().replaceAll("[^a-zA-Z]", "").toLowerCase());
	        }
        }
        else if(type.equals("songs"))
        {
	        List<WebElement> names = driver.findElements(By.className("title"));
	        ArrayList<String> results = new ArrayList<String>();
	        for(int i = 0; i < names.size();i++)
	        {
	        	WebElement name = names.get(i).findElement(By.tagName("a"));
	        	results.add(name.getText());
	        	String[] temp =name.getText().split("\\s+");
	        	for(int j=0; j<temp.length; j++){
	        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
	        		if(temp[j].length()>0 && temp[j].length()<=wordSize && !unwords.contains(temp[j]) )
	        			result.add(temp[j]);
	        	}
	        	if(name.getText().replaceAll("[^a-zA-Z]", "").length() <= wordSize && !unwords.contains(name.getText()))
	        		result.add(name.getText().replaceAll("[^a-zA-Z]", "").toLowerCase());
	        }
        }
        else if(type.equals("compositions"))
        {
	        List<WebElement> names = driver.findElements(By.className("title"));
	        ArrayList<String> results = new ArrayList<String>();
	        for(int i = 0; i < names.size();i++)
	        {
	        	WebElement name = names.get(i).findElement(By.tagName("a"));
	        	results.add(name.getText());
	        	String[] temp =name.getText().split("\\s+");
	        	for(int j=0; j<temp.length; j++){
	        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
	        		if(temp[j].length()>0 && temp[j].length()<=wordSize && !unwords.contains(temp[j]) )
	        			result.add(temp[j]);
	        	}
	        	if(name.getText().replaceAll("[^a-zA-Z]", "").length() <= wordSize && !unwords.contains(name.getText()))
	        		result.add(name.getText().replaceAll("[^a-zA-Z]", "").toLowerCase());
	        }
        }
        else if(type.equals("labels"))
        {
	        List<WebElement> names = driver.findElements(By.className("name"));
	        ArrayList<String> results = new ArrayList<String>();
	        for(int i = 0; i < names.size();i++)
	        {
	        	WebElement name = names.get(i).findElement(By.tagName("a"));
	        	results.add(name.getText());
	        	String[] temp =name.getText().split("\\s+");
	        	for(int j=0; j<temp.length; j++){
	        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
	        		if(temp[j].length()>0 && temp[j].length()<=wordSize && !unwords.contains(temp[j]) )
	        			result.add(temp[j]);
	        	}
	        	if(name.getText().replaceAll("[^a-zA-Z]", "").length() <= wordSize && !unwords.contains(name.getText()))
	        		result.add(name.getText().replaceAll("[^a-zA-Z]", "").toLowerCase());
	        }
        }
        else if(type.equals("articles"))
        {
	        List<WebElement> names = driver.findElements(By.className("article-title"));
	        ArrayList<String> results = new ArrayList<String>();
	        for(int i = 0; i < names.size();i++)
	        {
	        	WebElement name = names.get(i).findElement(By.tagName("a"));
	        	results.add(name.getText());
	        	String[] temp =name.getText().split("\\s+");
	        	for(int j=0; j<temp.length; j++){
	        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
	        		if(temp[j].length()>0 && temp[j].length()<=wordSize && !unwords.contains(temp[j]) )
	        			result.add(temp[j]);
	        	}
	        	if(name.getText().replaceAll("[^a-zA-Z]", "").length() <= wordSize && !unwords.contains(name.getText()))
	        		result.add(name.getText().replaceAll("[^a-zA-Z]", "").toLowerCase());
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
	SongSolver ts = new SongSolver();
	ts.search("Kanye West", 5);
	//ts.search("Kanye West", "albums", 5);
	}*/
}
