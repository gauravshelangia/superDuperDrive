package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ObjectUtils;

import java.sql.SQLException;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	private static String USER_NAME = "userName";
	private static String USER_NAME_FIELD = "userName";
	private static String PASSWORD = "password";
	private static String PASSWORD_FIELD = "password";



	@LocalServerPort
	private int port;

	private WebDriver driver;
	
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() throws SQLException {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(4)
	public void testLoginSuccess(){
		driver.get("http://localhost:" + this.port + "/");
		driver.manage().window().maximize();
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		WebElement username = driver.findElement(By.name(USER_NAME_FIELD));
		username.sendKeys(USER_NAME);
		WebElement password = driver.findElement(By.name(PASSWORD_FIELD));
		password.sendKeys(PASSWORD);
		WebElement login = driver.findElement(By.id("login"));
		login.click();
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	@Order(2)
	public void testSignUpPageAccessSuccess(){
		driver.get("http://localhost:" + this.port + "/");
		driver.manage().window().maximize();
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		WebElement signupLink = driver.findElement(By.id("signup-label"));
		signupLink.click();
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}


	@Test
	@Order(3)
	public void testSignUpSuccess(){
		WebDriverWait wait = new WebDriverWait (driver, 30);

		driver.get("http://localhost:" + this.port + "/");
		driver.manage().window().maximize();
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		WebElement signupLink = driver.findElement(By.id("signup-label"));
		signupLink.click();
		Assertions.assertEquals("Sign Up", driver.getTitle());

		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		wait.until(ExpectedConditions.elementToBeClickable(inputFirstName)).click();

		WebElement inputLastName= driver.findElement(By.id("inputLastName"));
		wait.until(ExpectedConditions.elementToBeClickable(inputLastName)).click();

		WebElement inputUsername= driver.findElement(By.id("inputUsername"));
		wait.until(ExpectedConditions.elementToBeClickable(inputUsername)).click();

		WebElement inputPassword= driver.findElement(By.id("inputPassword"));
		wait.until(ExpectedConditions.elementToBeClickable(inputPassword)).click();

		inputFirstName.sendKeys("firstName");
		inputLastName.sendKeys("lastName");
		inputUsername.sendKeys(USER_NAME);
		inputPassword.sendKeys(PASSWORD);

		WebElement signupButton= driver.findElement(By.id("signupButton"));
		wait.until(ExpectedConditions.elementToBeClickable(signupButton)).click();

		WebElement loginLink= driver.findElement(By.id("loginLabel"));
		wait.until(ExpectedConditions.elementToBeClickable(loginLink));
		jse.executeScript("arguments[0].click()", loginLink);


		WebElement username = driver.findElement(By.name(USER_NAME_FIELD));
		wait.until(ExpectedConditions.elementToBeClickable(username)).click();

		WebElement password = driver.findElement(By.name(PASSWORD_FIELD));
		wait.until(ExpectedConditions.elementToBeClickable(password)).click();

		username.sendKeys(USER_NAME);
		password.sendKeys(PASSWORD);

		WebElement login = driver.findElement(By.id("login"));
		wait.until(ExpectedConditions.elementToBeClickable(login));
		jse.executeScript("arguments[0].click()", login);

		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	@Order(5)
	public void unauthorizedHomePage() {
		driver.get("http://localhost:" + this.port + "/home.html");
		Assertions.assertEquals("SuperDuperError", driver.getTitle());
	}

	@Test
	@Order(6)
	public void validLoginAndNoteCreationTest() {
		String noteTitle = "Super Duper drive";
		WebDriverWait wait = new WebDriverWait (driver, 30);
		driver.get("http://localhost:" + this.port + "/");
		driver.manage().window().maximize();
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		WebElement username = driver.findElement(By.name(USER_NAME_FIELD));
		username.sendKeys(USER_NAME);
		WebElement password = driver.findElement(By.name(PASSWORD_FIELD));
		password.sendKeys(PASSWORD);
		WebElement login = driver.findElement(By.id("login"));
		wait.until(ExpectedConditions.elementToBeClickable(login)).click();

		WebElement notes = driver.findElement(By.xpath("//a[@href='#nav-notes']"));
		jse.executeScript("arguments[0].click()", notes);

		WebElement addNoteButton = driver.findElement(By.xpath("//button[@id='addNoteButton']"));
		wait.until(ExpectedConditions.elementToBeClickable(addNoteButton)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);;

		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.click();
		noteDescription.sendKeys("Note creation test");
		WebElement noteSubmit = driver.findElement(By.id("save-note-id"));
		noteSubmit.click();
		Assertions.assertEquals("Home", driver.getTitle());

		jse.executeScript("arguments[0].click()", driver.findElement(By.xpath("//a[@href='#nav-notes']")));

		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> noteList = notesTable.findElements(By.tagName("th"));
		boolean noteCreated = false;
		for (int i=0; i<noteList.size(); i++){
			WebElement row = noteList.get(i);
			if (!row.isDisplayed()){
				jse.executeScript("arguments[0].scrollIntoView(true);", row);
			}
			if (row.getAttribute("innerHTML").equals(noteTitle)){
				noteCreated = true;
				break;
			}
		}
		Assertions.assertTrue(noteCreated);
	}

	@Test
	@Order(7)
	public void validLoginAndNoteUpdateTest() {
		String noteTitle = "New Note title";
		WebDriverWait wait = new WebDriverWait (driver, 30);
		driver.get("http://localhost:" + this.port + "/");
		driver.manage().window().maximize();
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		WebElement username = driver.findElement(By.name(USER_NAME_FIELD));
		username.sendKeys(USER_NAME);
		WebElement password = driver.findElement(By.name(PASSWORD_FIELD));
		password.sendKeys(PASSWORD);
		WebElement login = driver.findElement(By.id("login"));
		wait.until(ExpectedConditions.elementToBeClickable(login)).click();

		WebElement notes = driver.findElement(By.xpath("//a[@href='#nav-notes']"));
		jse.executeScript("arguments[0].click()", notes);

		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> noteList = notesTable.findElements(By.tagName("td"));
		boolean noteEdited = false;
		for (int i=0; i<noteList.size(); i++){
			WebElement row = noteList.get(i);
			if (!row.isDisplayed()){
				jse.executeScript("arguments[0].scrollIntoView(true);", row);
			}
			WebElement editButton = null;
			try {
				editButton = row.findElement(By.name("editNote"));
			} catch (NoSuchElementException e){
				// Do nothing go to next
				continue;
			}
			if (!ObjectUtils.isEmpty(editButton)){
				wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
				WebElement newNoteTitle = driver.findElement(By.id("note-title"));
				wait.until(ExpectedConditions.elementToBeClickable(newNoteTitle)).click();
				newNoteTitle.clear();
				newNoteTitle.sendKeys(noteTitle);

				WebElement noteSubmit = driver.findElement(By.id("save-note-id"));
				noteSubmit.click();
				Assertions.assertEquals("Home", driver.getTitle());
				break;
			}
		}

		jse.executeScript("arguments[0].click()", driver.findElement(By.xpath("//a[@href='#nav-notes']")));

		WebElement newNotesTable = driver.findElement(By.id("userTable"));
		List<WebElement> newNoteList = newNotesTable.findElements(By.tagName("th"));
		for (int j=0; j<newNoteList.size(); j++){
			WebElement newRow = newNoteList.get(j);
			if (!newRow.isDisplayed()){
				jse.executeScript("arguments[0].scrollIntoView(true);", newRow);
			}
			// row.getText doesn't seems to work always
			if (newRow.getAttribute("innerHTML").equals(noteTitle)){
				noteEdited = true;
				break;
			}
		}
		Assertions.assertTrue(noteEdited);
	}

	@Test
	@Order(8)
	public void validLoginAndNoteDeletionTest() {
		String noteTitle = "Super Duper drive";
		WebDriverWait wait = new WebDriverWait (driver, 30);
		driver.get("http://localhost:" + this.port + "/");
		driver.manage().window().maximize();
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		WebElement username = driver.findElement(By.name(USER_NAME_FIELD));
		username.sendKeys(USER_NAME);
		WebElement password = driver.findElement(By.name(PASSWORD_FIELD));
		password.sendKeys(PASSWORD);
		WebElement login = driver.findElement(By.id("login"));
		wait.until(ExpectedConditions.elementToBeClickable(login)).click();

		WebElement notes = driver.findElement(By.xpath("//a[@href='#nav-notes']"));
		jse.executeScript("arguments[0].click()", notes);


		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> noteList = notesTable.findElements(By.tagName("a"));

		String deleteNoteId = null;
		for (int i=0; i<noteList.size(); i++){
			WebElement deleteNoteButton = noteList.get(i);

			if (!ObjectUtils.isEmpty(deleteNoteButton) && deleteNoteButton.getAttribute("id").contains("note")){
				deleteNoteId = deleteNoteButton.getAttribute("id");
				wait.until(ExpectedConditions.elementToBeClickable(deleteNoteButton)).click();
				break;
			}
		}

		jse.executeScript("arguments[0].click()", driver.findElement(By.xpath("//a[@href='#nav-notes']")));

		boolean noteDelete = true;
		WebElement newNotesTable = driver.findElement(By.id("userTable"));
		List<WebElement> newNoteList = newNotesTable.findElements(By.tagName("a"));
		for (int j=0; j<newNoteList.size(); j++){
			WebElement newRow = newNoteList.get(j);
			if (!newRow.isDisplayed()){
				jse.executeScript("arguments[0].scrollIntoView(true);", newRow);
			}
			if (!ObjectUtils.isEmpty(newRow) && newRow.getAttribute("id").contains("note") &&
					newRow.getAttribute("id").equals(deleteNoteId)){
				noteDelete = false;
				break;
			}
		}
		Assertions.assertTrue(noteDelete);
	}


	@Test
	@Order(9)
	public void validLoginAndCredentialCreationTest() {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		String credUsername = "Gaurav";
		driver.get("http://localhost:" + this.port + "/");
		driver.manage().window().maximize();
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		WebElement username = driver.findElement(By.name(USER_NAME_FIELD));
		username.sendKeys(USER_NAME);
		WebElement password = driver.findElement(By.name(PASSWORD_FIELD));
		password.sendKeys(PASSWORD);
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

		jse.executeScript("arguments[0].click()", driver.findElement(By.xpath("//a[@href='#nav-credentials']")));

		WebElement credentialTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credList = credentialTable.findElements(By.tagName("td"));
		boolean credentialCreated = false;
		for (int i=0; i<credList.size(); i++){
			WebElement row = credList.get(i);
			if (!row.isDisplayed()){
				jse.executeScript("arguments[0].scrollIntoView(true);", row);
			}
			if (row.getAttribute("innerHTML").equals(credUsername)){
				credentialCreated = true;
				break;
			}
		}
		Assertions.assertTrue(credentialCreated);
	}

	@Test
	@Order(10)
	public void validLoginAndCredentialUpdateTest() {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		String credUsername = "Gaurav1";
		driver.get("http://localhost:" + this.port + "/");
		driver.manage().window().maximize();
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		WebElement username = driver.findElement(By.name(USER_NAME_FIELD));
		username.sendKeys(USER_NAME);
		WebElement password = driver.findElement(By.name(PASSWORD_FIELD));
		password.sendKeys(PASSWORD);
		WebElement login = driver.findElement(By.id("login"));
		login.click();
		WebElement credentials = driver.findElement(By.xpath("//a[@href='#nav-credentials']"));
		jse.executeScript("arguments[0].click()", credentials);

		jse.executeScript("arguments[0].click()", driver.findElement(By.xpath("//a[@href='#nav-credentials']")));

		WebElement credentialTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credList = credentialTable.findElements(By.tagName("td"));
		boolean credEdited = false;
		for (int i=0; i<credList.size(); i++){
			WebElement row = credList.get(i);
			if (!row.isDisplayed()){
				jse.executeScript("arguments[0].scrollIntoView(true);", row);
			}

			WebElement editButton = null;
			try {
				editButton = row.findElement(By.name("editCredential"));
			} catch (NoSuchElementException e){
				// Do nothing go to next
			}
			if (!ObjectUtils.isEmpty(editButton)){
				wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
				WebElement newCredUserName = driver.findElement(By.id("credential-username"));
				wait.until(ExpectedConditions.elementToBeClickable(newCredUserName)).click();
				newCredUserName.clear();
				newCredUserName.sendKeys(credUsername);

				WebElement credSubmit = driver.findElement(By.id("save-credential"));
				credSubmit.click();
				Assertions.assertEquals("Home", driver.getTitle());
				break;
			}
		}

		jse.executeScript("arguments[0].click()", driver.findElement(By.xpath("//a[@href='#nav-credentials']")));

		WebElement newCredentialTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> newCredList = newCredentialTable.findElements(By.tagName("td"));
		for (int j=0; j<newCredList.size(); j++){
			WebElement row = newCredList.get(j);
			if (!row.isDisplayed()){
				jse.executeScript("arguments[0].scrollIntoView(true);", row);
			}
			//System.out.println("TEXT == "+row.getText());
			// row.getText doesn't seems to work always

			if (row.getAttribute("innerHTML").equals(credUsername)){
				credEdited = true;
				break;
			}
		}
		Assertions.assertTrue(credEdited);
	}

	@Test
	@Order(11)
	public void validLoginAndCredentialDeletionTest() {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		driver.get("http://localhost:" + this.port + "/");
		driver.manage().window().maximize();
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		WebElement username = driver.findElement(By.name(USER_NAME_FIELD));
		username.sendKeys(USER_NAME);
		WebElement password = driver.findElement(By.name(PASSWORD_FIELD));
		password.sendKeys(PASSWORD);
		WebElement login = driver.findElement(By.id("login"));
		wait.until(ExpectedConditions.elementToBeClickable(login)).click();

		jse.executeScript("arguments[0].click()", driver.findElement(By.xpath("//a[@href='#nav-credentials']")));

		WebElement credentialTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credList = credentialTable.findElements(By.tagName("a"));

		String deletedCredId=null;

		for (int i=0; i<credList.size(); i++){
			WebElement deleteCredButton = credList.get(i);

			if (!ObjectUtils.isEmpty(deleteCredButton) && deleteCredButton.getAttribute("id").contains("cred")){
				deletedCredId = deleteCredButton.getAttribute("id");
				wait.until(ExpectedConditions.elementToBeClickable(deleteCredButton)).click();
				break;
			}
		}

		jse.executeScript("arguments[0].click()", driver.findElement(By.xpath("//a[@href='#nav-credentials']")));

		boolean credDeleted = true;
		WebElement newcredentialTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> newCredList = newcredentialTable.findElements(By.tagName("a"));
		for (int j=0; j<newCredList.size(); j++){
			WebElement newRow = newCredList.get(j);
			if (!newRow.isDisplayed()){
				jse.executeScript("arguments[0].scrollIntoView(true);", newRow);
			}
			if (!ObjectUtils.isEmpty(newRow) && newRow.getAttribute("id").contains("cred") &&
					newRow.getAttribute("id").equals(deletedCredId)){
				credDeleted = false;
				break;
			}
		}
		Assertions.assertTrue(credDeleted);
	}


}
