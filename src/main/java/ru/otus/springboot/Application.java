package ru.otus.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import ru.otus.springboot.config.YamlProp;
import ru.otus.springboot.service.ExamService;
import ru.otus.springboot.service.LocalizedService;
import ru.otus.springboot.service.StudentService;

@SpringBootApplication
@EnableConfigurationProperties(YamlProp.class)
public class Application {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Application.class, args);

        ExamService examService = context.getBean(ExamService.class);
        StudentService studentService = context.getBean(StudentService.class);
        LocalizedService localizedService = context.getBean(LocalizedService.class);

        examService.readQuestions();
        studentService.askStudentFirstName();
        studentService.askStudentSecondName();

        Student student = studentService.getStudent();
        String result = localizedService.getMessage("result");
        String studentTag = localizedService.getMessage("student.tag");

        System.out.println(result + " " + examService.checkTest());
        System.out.println(studentTag + " " + student.getFirstName() + " " + student.getSecondName());

    }

}

