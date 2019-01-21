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
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springboot.config.YamlProp;
import ru.otus.springboot.service.*;
import java.util.Locale;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@EnableConfigurationProperties(YamlProp.class)
@SpringBootTest
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

    @Before
    public void prepare(){
        examService.readQuestions();
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
        Assertions.assertThat(5).isEqualTo(examService.checkTest());
    }

    @Test
    public void getFailExam(){
        when(inputServiceMock.ask("Третья планета от солнца")).thenReturn("Марс");
        when(inputServiceMock.ask("Год окончания второй мировой войны")).thenReturn("1941");
        when(inputServiceMock.ask("Столица России")).thenReturn("Сочи");
        when(inputServiceMock.ask("Сколько часов в сутках")).thenReturn("30");
        when(inputServiceMock.ask("Основатель Facebook (Фамилия)")).thenReturn("Дуров");
        Assertions.assertThat(5).isNotEqualTo(examService.checkTest());
    }

    @Test
    public void getStudentName(){
        String firstName = localizedService.getMessage("name.first");
        when(inputServiceMock.ask(firstName)).thenReturn("Иван");
        studentService.askStudentFirstName();
        student = studentService.getStudent();
        Assertions.assertThat("Иван").isEqualTo(student.getFirstName());
    }

    @Test
    public void getStudentSecondName(){
        String secondName = localizedService.getMessage("name.second");
        when(inputServiceMock.ask(secondName)).thenReturn("Иванов");
        studentService.askStudentSecondName();
        student = studentService.getStudent();
        Assertions.assertThat("Иванов").isEqualTo(student.getSecondName());
    }

    @Test
    public void getEnglishLocalQuestions(){
        localizedService.setLocale(Locale.ENGLISH);
        ExamService examServiceEn = new ExamServiceCSV(inputServiceMock,
                localizedService);
        examServiceEn.readQuestions();
        when(inputServiceMock.ask("Third planet from Sun")).thenReturn("Earth");
        when(inputServiceMock.ask("Year of the end of world war ii")).thenReturn("1945");
        when(inputServiceMock.ask("Capital of Russia")).thenReturn("Moscow");
        when(inputServiceMock.ask("How many hours in days")).thenReturn("24");
        when(inputServiceMock.ask("Founder of Facebook (second name)")).thenReturn("Zuckerberg");
        Assertions.assertThat(5).isEqualTo(examServiceEn.checkTest());
    }

    @After
    public void setDefaultLocale(){
        localizedService.setLocale(Locale.getDefault());
    }
}

