package com.qa.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CharacterCreateTest {
    private ChromeDriver driver;

    private ArrayList<int[]> intFields = new ArrayList<int[]>(Arrays.asList(
            new int[]{20, 15, 13, 18, 20, 10, 7, 9, 4},
            new int[]{10, 19, 20, 20, 17, 10, 8, 10, 2}
    ));

    private ArrayList<String[]> textFields = new ArrayList<String[]>(Arrays.asList(
            new String[]{"Socrowtes", "Kenku", "Monk", "Criminal", "Chaotic Neutral"},
            new String[]{"Jeff", "Human", "Bard", "Noble", "Neutral"}
            ));



    @Before
    public void setUp(){
        System.setProperty(SeleniumConst.DRIVER_KEY, SeleniumConst.DRIVER_LOCATION);
        //ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--headless");
        //driver = new ChromeDriver(chromeOptions);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(){
        driver.close();
    }

    @Test
    public void charSubmissionEmptyFieldsTest() throws InterruptedException {
        driver.get(SeleniumConst.HOMEPAGE_URL+"create-edit-character.html");
        WebElement charSelection = driver.findElement(By.id("playerId"));
        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"charsub\"]/input"));
        String defaultValue = charSelection.getAttribute("value");

        submitButton.click();

        Thread.sleep(100);

        charSelection = driver.findElement(By.id("playerId"));

        Thread.sleep(100);

        assertEquals(charSelection.getAttribute("value"), defaultValue);

        Thread.sleep(100);
    }

    @Test
    public void charSubmissionBoxValueResetTest() {
        driver.get(SeleniumConst.HOMEPAGE_URL+"create-edit-character.html");

        Actions actions = new Actions(driver);

        List<WebElement> inputFields = driver.findElement(By.id("charsub")).findElements(By.tagName("input"));

        int max;
        int min;
        String defaultValue;

        //Get for each input in inputFields
        for(WebElement element : inputFields) {
            //Check if it is of type "number"
            if(element.getAttribute("type").equals("number")) {
                //get max
                max = Integer.parseInt(element.getAttribute("max"));
                //get min
                min = Integer.parseInt(element.getAttribute("min"));

                //Get default value!
                defaultValue = element.getAttribute("value");

                //Check greater than max values cause reset!
                actions.click(element).sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE).sendKeys(Integer.toString(max+1)).sendKeys(Keys.ENTER).perform();
                assertEquals(defaultValue, element.getAttribute("value"));

                //Check less than max values cause reset!
                actions.click(element).sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE).sendKeys(Integer.toString(min-1)).sendKeys(Keys.ENTER).perform();
                assertEquals(defaultValue, element.getAttribute("value"));
            }
        }
    }

    //Create a character test!

    @Test
    public void charSubmissionWriteTest() throws InterruptedException {
        driver.get(SeleniumConst.HOMEPAGE_URL+"create-edit-character.html");
        Actions actions = new Actions(driver);

        List<WebElement> inputFields = driver.findElement(By.id("charsub")).findElements(By.tagName("input"));
        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"charsub\"]/input"));

        //Get the character dropdown
        WebElement charSelection = driver.findElement(By.id("playerId"));
        Select charSelect = new Select(charSelection);
        String defaultOption = charSelect.getFirstSelectedOption().getText();

        //Create variables needed for handling the generation of characters
        int textFieldCount;
        int noFieldCount;

        //For each text field entry create a character
        String[] textField;
        int[] intField;


        for(int i = 0; i < textFields.size(); i++){
            textField = textFields.get(i);
            intField = intFields.get(i);

            textFieldCount = 0;
            noFieldCount = 0;

            //Get for each input in inputFields
            for(WebElement element : inputFields) {
                //Check if it is of type "number"
                if(element.getAttribute("type").equals("number")) {
                    actions.click(element).sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE).sendKeys(Integer.toString(intField[noFieldCount])).perform();
                    noFieldCount++;
                    Thread.sleep(100);
                } else if(element.getAttribute("type").equals("text")){ //If its a text field
                    actions.click(element).sendKeys(textField[textFieldCount]);
                    textFieldCount++;
                    Thread.sleep(100);
                }
            }

            submitButton.click();
            Thread.sleep(100);

            assertEquals(textField[0],charSelect.getFirstSelectedOption().getText());

            charSelect.selectByIndex(0);

            assertEquals(inputFields.get(0).getAttribute("value"),"");
        }
    }



    //Update one of them as a test



    //create inventories for both

    //update one of them

    //delete one from the inventories page

    //delete the other character // Then check the inventories page is empty

    //this would conclude basic functionality testing!
}
