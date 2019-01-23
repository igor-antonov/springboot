package ru.otus.springboot;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springboot.config.YamlProp;
import ru.otus.springboot.service.*;
import java.util.Locale;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@EnableConfigurationProperties(YamlProp.class)
@SpringBootTest(properties={
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class ApplicationTests {

    private Student student;
    @Autowired
    ExamService examService;
    @Autowired
    StudentService studentService;
    @Autowired
    LocalizedService localizedService;
    @MockBean
    InputService inputServiceMock;
    @Autowired
    Shell shell;

    @Before
    public void prepare(){
        examService.readQuestions();
        shell.evaluate(() -> "name Иван");
        shell.evaluate(() -> "surname Иванов");
    }

    @Test
    public void getQuantityQuestions(){
        int quantityQuestions = examService.getQuestions().size();
        Assertions.assertThat(5).isEqualTo(quantityQuestions);
    }

    @Test
    public void getPassExam() {
        when(inputServiceMock.ask("Третья планета от солнца")).thenReturn("Земля");
        when(inputServiceMock.ask("Год окончания второй мировой войны")).thenReturn("1945");
        when(inputServiceMock.ask("Столица России")).thenReturn("Москва");
        when(inputServiceMock.ask("Сколько часов в сутках")).thenReturn("24");
        when(inputServiceMock.ask("Основатель Facebook (Фамилия)")).thenReturn("Цукерберг");
        String shellRes = shell.evaluate(() -> "test").toString();
        String result = shellRes.substring(shellRes.indexOf("Количество"));
        Assertions.assertThat("Количество правильных ответов: 5").isEqualTo(result);
    }

    @Test
    public void getFailExam(){
        when(inputServiceMock.ask("Третья планета от солнца")).thenReturn("Марс");
        when(inputServiceMock.ask("Год окончания второй мировой войны")).thenReturn("1941");
        when(inputServiceMock.ask("Столица России")).thenReturn("Сочи");
        when(inputServiceMock.ask("Сколько часов в сутках")).thenReturn("30");
        when(inputServiceMock.ask("Основатель Facebook (Фамилия)")).thenReturn("Дуров");
        String shellRes = shell.evaluate(() -> "test").toString();
        String result = shellRes.substring(shellRes.indexOf("Количество"));
        Assertions.assertThat("Количество правильных ответов: 5").isNotEqualTo(result);
    }

    @Test
    public void getStudentName(){
        Assertions.assertThat("Иван").isEqualTo(studentService.getStudent().getFirstName());
    }

    @Test
    public void getStudentSecondName(){
        Assertions.assertThat("Иванов").isEqualTo(studentService.getStudent().getSecondName());
    }

    @Test
    public void getEnglishLocalQuestions(){
        shell.evaluate(() -> "locale en");

        when(inputServiceMock.ask("Third planet from Sun")).thenReturn("Earth");
        when(inputServiceMock.ask("Year of the end of world war ii")).thenReturn("1945");
        when(inputServiceMock.ask("Capital of Russia")).thenReturn("Moscow");
        when(inputServiceMock.ask("How many hours in days")).thenReturn("24");
        when(inputServiceMock.ask("Founder of Facebook (second name)")).thenReturn("Zuckerberg");
        String shellRes = shell.evaluate(() -> "test").toString();
        String result = shellRes.substring(shellRes.indexOf("Quantity"));
        Assertions.assertThat("Quantity of correct answers: 5").isEqualTo(result);
    }

    @Test
    public void getEnglishLocale(){
        String localeEn = shell.evaluate(() -> "locale en").toString();
        System.out.println(localeEn);
        Assertions.assertThat(localizedService.getLocale().toLanguageTag().equals(
        localeEn));
    }

    @After
    public void setDefaultLocale(){
        localizedService.setLocale(Locale.getDefault());
    }
}

