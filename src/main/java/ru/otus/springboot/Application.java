package ru.otus.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.springboot.config.YamlProp;
import ru.otus.springboot.service.ExamService;
import ru.otus.springboot.service.LocalizedService;
import ru.otus.springboot.service.StudentService;

@SpringBootApplication
@EnableConfigurationProperties(YamlProp.class)
public class Application {

    static ExamService examService;
    static StudentService studentService;
    static LocalizedService localizedService;

    public Application(ExamService examService,
                       StudentService studentService,
                       LocalizedService localizedService){
        this.examService = examService;
        this.studentService = studentService;
        this.localizedService = localizedService;
    }


    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

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

