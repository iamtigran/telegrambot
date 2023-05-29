package io.proj3ct.tmbot.deadlines;

import org.glassfish.grizzly.Writer;
import org.glassfish.jersey.client.Initializable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.LinkedList;


public class Deadlines{

   private static LinkedList<String> myList = new LinkedList<>();


    public Deadlines(){
        downloadDeadlines();
    }




    private static void downloadDeadlines() {

        try {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Tigran\\Desktop\\JavaSpring Project\\chromedriver\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true);
            WebDriver driver = new ChromeDriver(options);
            // Create a new instance of the Chrome driver

            // Navigate to Google
            driver.get("https://elearning.aua.am/login/index.php");

            Thread.sleep(400);

            // Entering username
            WebElement userName = driver.findElement(By.xpath("//*[@id=\"username\"]"));
            userName.sendKeys("tigran_movsesyan");

            System.out.println("Entering username");

            WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));
            password.sendKeys("tikojan94*");

            Thread.sleep(50);

            WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"loginbtn\"]"));
            loginButton.click();

            Thread.sleep(3000);

            WebElement subject1 = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div[3]/section/aside/section[2]/div/div/div[1]/div[2]/div/div/div[1]/div/div/div[2]/div/div[1]/div/div/div[1]/div/div/div[2]/a"));
            subject1.click();

            WebElement title = driver.findElement(By.xpath("/html/body/div[1]/div[2]/header/div/div/div/div[1]/div[1]/div/div/h1"));
            WebElement hw = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/section/div[1]/h2"));
            WebElement dueDate = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/section/div[1]/div[2]/div[1]/table/tbody/tr[3]/td"));

            myList.add("Subject: " + title.getText() + ", " + hw.getText() + ", Due date: " + dueDate.getText());

            Thread.sleep(4000);

            driver.quit();


            System.out.println(myList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void createFile(){
        try {

            FileWriter myFile = new FileWriter("C:\\Users\\Tigran\\Desktop\\JavaSpring Project\\projects1.txt");
            myFile.write("Heelo");



        }
        catch (Exception e ){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
         downloadDeadlines();


    }
}
