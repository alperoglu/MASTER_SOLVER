package project;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AnswerFinder {
	WebDriver driver;
	public AnswerFinder()
	{
		/*System.setProperty("webdriver.chrome.driver","C:\\Users\\USER\\Desktop\\chromedriver.exe");
    	driver = new ChromeDriver();*/
	}
	public ArrayList<String> search(String s, int wordSize)
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\USER\\Desktop\\chromedriver.exe");
    	driver = new ChromeDriver();
    	driver.manage().window().setPosition(new Point(-2000, 0));
		s = s.replace(" ", "-");
    	s = s.replaceAll("'", "%27");
    	s = s.replaceAll("\\?", "%3F");
    	s = s.replaceAll(":", "-");
        String searchUrl = "http://www.wordplays.com/crossword-solver/" + s;
        driver.get(searchUrl);
        WebElement table = driver.findElement(By.className("wp-widget-content"));
        List <WebElement>  answerCol = table.findElements(By.tagName("a"));
        ArrayList<String> result = new ArrayList<String>();
        for(int i = 0; i < answerCol.size();i++){
        	if(answerCol.get(i).getText().replaceAll("\\s+","").length() <= wordSize)
        		result.add(answerCol.get(i).getText().replaceAll("\\s+",""));
        		//result.add(answerCol.get(i).getText().toLowerCase().replaceAll("\\s+",""));
        }
        driver.close();
		return result;
	}
/*	public static void main(String[] args) {
	AnswerFinder solver = new AnswerFinder();
	String answer=solver.search("animal fat");
	}*/
}
