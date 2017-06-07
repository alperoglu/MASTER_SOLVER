package project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class GoogleTranslate {
	WebDriver driver;
	public ArrayList<String> languages;
	public ArrayList<String> abbr;
	public GoogleTranslate()
	{
		
    	languages = new ArrayList();
    	abbr = new ArrayList();
    	this.populateLanguages();
	}
	public void populateLanguages()
	{
		languages.add(new String("afrikaans"));
		abbr.add(new String ("ap"));
		languages.add(new String("albanian"));
		abbr.add(new String ("sq"));
		languages.add(new String("arabic"));
		abbr.add(new String ("ar"));
		languages.add(new String("armenian"));
		abbr.add(new String ("hy"));
		languages.add(new String("azerbaijani"));
		abbr.add(new String ("az"));
		languages.add(new String("belarusian"));
		abbr.add(new String ("be"));
		languages.add(new String("bengali"));
		abbr.add(new String ("bn"));
		languages.add(new String("bosnian"));
		abbr.add(new String ("bs"));
		languages.add(new String("bulgarian"));
		abbr.add(new String ("bg"));
		languages.add(new String("cambodian"));
		abbr.add(new String ("km"));
		languages.add(new String("catalan"));
		abbr.add(new String ("ca"));
		languages.add(new String("cherokee"));
		abbr.add(new String ("chr"));
		languages.add(new String("chichewa"));
		abbr.add(new String ("ny"));
		languages.add(new String("chinese "));
		abbr.add(new String ("zh-CN"));
		languages.add(new String("croatian"));
		abbr.add(new String ("hr"));
		languages.add(new String("czech"));
		abbr.add(new String ("cs"));
		languages.add(new String("danish"));
		abbr.add(new String ("da"));
		languages.add(new String("dutch"));
		abbr.add(new String ("nl"));
		languages.add(new String("english"));
		abbr.add(new String ("en"));
		languages.add(new String("finnish"));
		abbr.add(new String ("fi"));
		languages.add(new String("french"));
		abbr.add(new String ("fr"));
		languages.add(new String("georgian"));
		abbr.add(new String ("ka"));
		languages.add(new String("german"));
		abbr.add(new String ("de"));
		languages.add(new String("greek"));
		abbr.add(new String ("el"));
		languages.add(new String("hebrew"));
		abbr.add(new String ("iw"));
		languages.add(new String("hindi"));
		abbr.add(new String ("hi"));
		languages.add(new String("hungarian"));
		abbr.add(new String ("hu"));
		languages.add(new String("icelandic"));
		abbr.add(new String ("is"));
		languages.add(new String("indonesian"));
		abbr.add(new String ("id"));
		languages.add(new String("irish"));
		abbr.add(new String ("ga"));
		languages.add(new String("italian"));
		abbr.add(new String ("it"));
		languages.add(new String("japanese"));
		abbr.add(new String ("ja"));
		languages.add(new String("hebrew"));
		abbr.add(new String ("iw"));
		languages.add(new String("Kazakh"));
		abbr.add(new String ("kk"));
		languages.add(new String("korean"));
		abbr.add(new String ("ko"));
		languages.add(new String("kurdish"));
		abbr.add(new String ("ku"));
		languages.add(new String("latin"));
		abbr.add(new String ("la"));
		languages.add(new String("latvian"));
		abbr.add(new String ("lv"));
		languages.add(new String("lithuanian"));
		abbr.add(new String ("lt"));
		languages.add(new String("macedonian"));
		abbr.add(new String ("mk"));
		languages.add(new String("norwegian"));
		abbr.add(new String ("no"));	
		languages.add(new String("persian"));
		abbr.add(new String ("fa"));
		languages.add(new String("polish"));
		abbr.add(new String ("pl"));
		languages.add(new String("portuguese"));
		abbr.add(new String ("pt-PT"));
		languages.add(new String("romanian"));
		abbr.add(new String ("ro"));
		languages.add(new String("russian"));
		abbr.add(new String ("ru"));
		languages.add(new String("serbian"));
		abbr.add(new String ("sr"));
		languages.add(new String("slovenian"));
		abbr.add(new String ("slv"));
		languages.add(new String("spanish"));
		abbr.add(new String ("es"));
		languages.add(new String("swedish"));
		abbr.add(new String ("sv"));
		languages.add(new String("turkish"));
		abbr.add(new String ("tr"));
		languages.add(new String("ukrainian"));
		abbr.add(new String ("uk"));
		languages.add(new String("vietnamese"));
		abbr.add(new String ("vi"));
		languages.add(new String("yiddish"));
		abbr.add(new String ("yi"));
	}
	public ArrayList<String> search(String s,String lang, int wordSize)
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\USER\\Desktop\\chromedriver.exe");
    	driver = new ChromeDriver();
    	driver.manage().window().setPosition(new Point(-2000, 0));
		System.out.println("Translating the word: " + s + "to language: " + lang);
		ArrayList<String> result = new ArrayList<String>();
		driver.manage().timeouts().implicitlyWait(5
                , TimeUnit.SECONDS);
		int index = 0;
		index = languages.indexOf(lang);
		String shortLang = abbr.get(index);
        driver.get("http://translate.google.com/#auto/" + shortLang);    
        String text="";         
        text=s;
        // Enter the query string "Cheese"
        WebElement query = driver.findElement(By.id("source"));
        query.sendKeys(text);
        WebElement query1 = driver.findElement(By.id("gt-submit"));
        query1.click();
        
        String temp=driver.findElement(By.id("result_box")).getText().replaceAll("[^a-zA-Z]", "").toLowerCase();
        if(temp.length()<=wordSize){
        	result.add(temp);
        }
        
        List<WebElement> results = driver.findElements(By.className("gt-baf-word-clickable"));
        
        for(int i=0; i<results.size(); i++){
        	temp=results.get(i).getText().replaceAll("[^a-zA-Z]", "").toLowerCase();
        	if(temp.length()<=wordSize){
            	result.add(temp);
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
		GoogleTranslate solver = new GoogleTranslate();
		solver.populateLanguages();
		solver.search("bonjour", "English", 5);
	}*/
}