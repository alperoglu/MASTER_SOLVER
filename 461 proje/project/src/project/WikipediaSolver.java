package project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WikipediaSolver {
	WebDriver driver;
	public WikipediaSolver()
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\USER\\Desktop\\chromedriver.exe");
    	driver = new ChromeDriver();
	}
	public String search(String s)
	{
	 	s = s.replaceAll(" ", "+");
    	s = s.replaceAll("'", "%27");
    	s = s.replaceAll("\\?", "%3F");
    	s = s.replaceAll(":", "%3A");
        String searchUrl = "https://en.wikipedia.org/w/index.php?search=" + s + "&title=Special:Search&profile=default&fulltext=1" ;
        driver.get(searchUrl);
        String result = "";
		return result;
	}
	public String topicSearch(String s)
	{
	 	s = s.replaceAll(" ", "_");
    	s = s.replaceAll("'", "%27");
    	s = s.replaceAll("\\?", "%3F");
    	s = s.replaceAll(":", "%3A");
        String searchUrl = "https://en.wikipedia.org/wiki/" + s;
        driver.get(searchUrl);
        String result = "";
		return result;
	}
//	public static void main(String[] args) {
//	WikipediaSolver solver = new WikipediaSolver();
//	String answer=solver.topicSearch("animal fat");
//	}
}
