package de.atomfrede.github.karaoke.server.selenium;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import de.atomfrede.github.karaoke.server.mongo.SongRepository;
import org.apache.log4j.*;

public class SeleniumTest {
	
	WebDriver driver;
	String url;	
	SongRepository songrepository;
	
	@Before
	public void seUp() throws Exception {
		driver = new FirefoxDriver();
		url = "localhost:8080/ui";
		driver.get(url);
	}
	
	@Test
	public void testCreateNewSong() {
			
		driver.findElement(By.id("songId")).click();
		driver.findElement(By.id("addNewSong")).click();
		driver.findElement(By.id("newSongTitle")).sendKeys("new Title");
		driver.findElement(By.id("newSongInterpreter")).sendKeys("New Interpreter");
		driver.findElement(By.id("saveNewSong")).click();		
	}
	
	/*
	@Test
	public void testEditNewSong() {
		driver.findElement(By.))
	}
	
	@Test
	public void testDelete(){
		songrepository = new SongRepository(database);
	})*/
}
