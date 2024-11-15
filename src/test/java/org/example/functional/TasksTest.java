package org.example.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver acessarAplicacao() throws MalformedURLException {
        WebDriver driver = new ChromeDriver();
        /*DesiredCapabilities cap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.12:4444/wd/hub"), cap);*/
        driver.navigate().to("http://192.168.0.12:8001/tasks/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException {
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
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
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
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
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
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
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

    @Test
    public void deveRemoverTarefaComSucesso() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            // inserir tarefa
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Adicionado item para deletar via Selenium");
            driver.findElement(By.id("dueDate")).sendKeys(LocalDate.now().plusDays(1L).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            driver.findElement(By.id("saveButton")).click();
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
            // remover
            driver.findElement(By.xpath("//*[@id=\"todoTable\"]/tbody/tr[1]/td[3]/a")).click();
            message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
        } finally {
            driver.quit();
        }
    }

}
