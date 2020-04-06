package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void unauthorizedHomePage() {
		driver.get("http://localhost:" + this.port + "/home.html");
		Assertions.assertEquals("SuperDuperError", driver.getTitle());
	}

	@Test
	public void validLoginAndNoteCreationTest() {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		driver.get("http://localhost:" + this.port + "/");
		driver.manage().window().maximize();
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		WebElement username = driver.findElement(By.name("userName"));
		username.sendKeys("a");
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("a");
		WebElement login = driver.findElement(By.id("login"));
		login.click();
		WebElement notes = driver.findElement(By.xpath("//a[@href='#nav-notes']"));
		jse.executeScript("arguments[0].click()", notes);


		WebElement addNoteButton = driver.findElement(By.xpath("//button[@id='addNoteButton']"));
		wait.until(ExpectedConditions.elementToBeClickable(addNoteButton)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys("Super Duper drive");;

		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.click();
		noteDescription.sendKeys("Note creation test");
		WebElement noteSubmit = driver.findElement(By.id("save-note-id"));
		noteSubmit.click();
		Assertions.assertEquals("Home", driver.getTitle());

	}


	@Test
	public void validLoginAndCredentialCreationTest() {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		driver.get("http://localhost:" + this.port + "/");
		driver.manage().window().maximize();
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		WebElement username = driver.findElement(By.name("userName"));
		username.sendKeys("a");
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("a");
		WebElement login = driver.findElement(By.id("login"));
		login.click();
		WebElement credentials = driver.findElement(By.xpath("//a[@href='#nav-credentials']"));
		jse.executeScript("arguments[0].click()", credentials);

		WebElement addCredentialButton = driver.findElement(By.xpath("//button[@id='credentialbutton']"));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialbutton"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys("Udacity.com");

		WebElement usernameText = driver.findElement(By.id("credential-username"));
		usernameText.click();
		usernameText.sendKeys("Gaurav");
		WebElement passwordText = driver.findElement(By.id("credential-password"));
		passwordText.click();
		passwordText.sendKeys("rehoboam");

		WebElement credentialSubmit = driver.findElement(By.id("save-credential"));
		credentialSubmit.click();
		Assertions.assertEquals("Home", driver.getTitle());

	}

}
