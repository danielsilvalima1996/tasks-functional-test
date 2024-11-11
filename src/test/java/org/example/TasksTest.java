package org.example;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver acessarAplicacao() {
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://localhost:8001/tasks/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() {
        WebDriver driver = acessarAplicacao();

//        clicar em add todo
        driver.findElement(By.id("addTodo")).click();

//        escrever descrição
        driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

//        escrever a data
        driver.findElement(By.id("dueDate")).sendKeys(LocalDate.now().plusDays(1L).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

//        clicar em salvar
        driver.findElement(By.id("saveButton")).click();

        //        validar mensagem de sucesso
        String message = driver.findElement(By.id("message")).getText();
//        fechar o browser
        driver.quit();
        Assert.assertEquals("Success!", message);
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() {
        WebDriver driver = acessarAplicacao();

//        clicar em add todo
        driver.findElement(By.id("addTodo")).click();

//        escrever descrição
        driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

//        escrever a data
        driver.findElement(By.id("dueDate")).sendKeys(
                LocalDate.now().minusDays(1L)
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

//        clicar em salvar
        driver.findElement(By.id("saveButton")).click();

        //        validar mensagem de sucesso
        String message = driver.findElement(By.id("message")).getText();
//        fechar o browser
        driver.quit();
        Assert.assertEquals("Due date must not be in past", message);
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() {
        WebDriver driver = acessarAplicacao();

        try {
            //        clicar em add todo
            driver.findElement(By.id("addTodo")).click();

//        escrever a data
            driver.findElement(By.id("dueDate")).sendKeys(
                    LocalDate.now().plusDays(1L)
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

//        clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //        validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);

        } finally {
//        fechar o browser
            driver.quit();
        }

    }

    @Test
    public void naoDeveSalvarTarefaSemData() {
        WebDriver driver = acessarAplicacao();

        try {
            //        clicar em add todo
            driver.findElement(By.id("addTodo")).click();

//        escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

//        clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //        validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", message);

        } finally {
//        fechar o browser
            driver.quit();
        }

    }


}
