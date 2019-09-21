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

public class CharacterCreatePersistenceTest {
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
    public void charPersistenceTest() {
        driver.get(SeleniumConst.HOMEPAGE_URL+"create-edit-character.html");
        Actions actions = new Actions(driver);

        List<WebElement> inputFields = driver.findElement(By.id("charsub")).findElements(By.tagName("input"));
        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"charsub\"]/input"));

        //Get the character dropdown
        WebElement charSelection = driver.findElement(By.id("playerId"));
        Select charSelect = new Select(charSelection);

        int textFieldCount;
        int noFieldCount;

        //Get for each input in inputFields

        for(int i = 0; i < textFields.size(); i++){

            String[] textField = textFields.get(i);
            int[] intField = intFields.get(i);

            charSelect.selectByIndex(i+1);

            textFieldCount = 0;
            noFieldCount = 0;

            for(WebElement element : inputFields) {
                //Check if it is of type "number"
                if(element.getAttribute("type").equals("number")) {
                    assertEquals(intField[noFieldCount], Integer.parseInt(element.getAttribute("value")));
                    noFieldCount++;
                } else if(element.getAttribute("type").equals("text")){ //If its a text field
                    assertEquals(textField[textFieldCount], element.getAttribute("value"));
                    textFieldCount++;
                }
            }

        }
    }



    //Update one of them as a test



    //create inventories for both

    //update one of them

    //delete one from the inventories page

    //delete the other character // Then check the inventories page is empty

    //this would conclude basic functionality testing!
}
