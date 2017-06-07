package project;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.*;

public class PuzzleGenerator {
	public ArrayList<String> cuesAcrossList;
	public ArrayList<String> cuesDownList;
	public int blackPos[];
	public PuzzleGenerator()
	{
		cuesAcrossList = new ArrayList<String>();
		cuesDownList = new ArrayList<String>();
		blackPos = new int[25];
	}
	public void getCluesAndCrossword()
	{
		// declaration and instantiation of objects/variables
    	WebDriver driver; 
    	System.setProperty("webdriver.chrome.driver","C:\\Users\\USER\\Desktop\\chromedriver.exe");
    	driver = new ChromeDriver();
        String baseUrl = "https://www.nytimes.com/crosswords/game/mini?page=mini&type=mini&date=&_r=0";

        // launch Fire fox and direct it to the Base URL
        driver.get(baseUrl);
        
        String title = driver.getTitle();
        
        System.out.println("Title of current page is " + title);
        // get the actual value of the title
        
        List<WebElement> cueWrapper = driver.findElements(By.className("clue-list-wrapper"));
                        
        WebElement listAcross = cueWrapper.get(0).findElement(By.className("clue-list"));
        List<WebElement> cuesAcross = new ArrayList<WebElement>();
        cuesAcross = listAcross.findElements(By.tagName("li"));
        WebElement listDown = cueWrapper.get(1).findElement(By.className("clue-list"));
        List<WebElement> cuesDown = new ArrayList<WebElement>();
        cuesDown = listDown.findElements(By.tagName("li"));
        for(int i =0;i < cuesAcross.size();i++)
        {
        	cuesAcrossList.add(cuesAcross.get(i).getAttribute("value") + ". " + cuesAcross.get(i).getText());
        	cuesDownList.add(cuesDown.get(i).getAttribute("value") + ". " + cuesDown.get(i).getText());
        }
        List<WebElement> rows = driver.findElements(By.className("flex-row"));  
        String output = "";
        int count = 0;
        for(int i=0; i<rows.size(); i++){
        	List<WebElement> cells = rows.get(i).findElements(By.cssSelector(".flex-cell"));
        	output = output + "| ";
        	for(int j=0; j<cells.size(); j++){
        		if(cells.get(j).getAttribute("class").toString().equals("flex-cell black")){
        			blackPos[count] = 1;
        			output = output + "B| ";
        		}
        		else{
        			output = output + " | ";
        		}
        		count++;
        	}
        	output = output + "\n";       	
        }
       System.out.println(output);       
        //close Chrome
        driver.close();
       
        // exit the program explicitly
	}
 /*   public static void main(String[] args) {
       PuzzleGenerator pg = new PuzzleGenerator();
       pg.getCluesAndCrossword();
       for(int i = 0;i < pg.cuesAcrossList.size();i++)
       {
    	   System.out.println(pg.cuesAcrossList.get(i));
       }
    }*/
}