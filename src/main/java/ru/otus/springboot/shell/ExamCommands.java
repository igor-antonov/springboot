package ru.otus.springboot.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.springboot.service.ExamService;
import ru.otus.springboot.service.LocalizedService;
import ru.otus.springboot.service.StudentService;

@ShellComponent
public class ExamCommands {

    private final ExamService examService;
    private final StudentService studentService;
    private final LocalizedService localizedService;

    public ExamCommands(ExamService examService,
                        StudentService studentService,
                        LocalizedService localizedService){
        this.studentService = studentService;
        this.examService = examService;
        this.localizedService = localizedService;
    }

    @ShellMethod(value = "Get Result")
    public String test(){
        if (studentService.getStudent().getFirstName() == null){
            return localizedService.getMessage("name.first.err");
        }
        else if (studentService.getStudent().getSecondName() == null){
            return localizedService.getMessage("name.second.err");
        }
        String result = localizedService.getMessage("result");
        String studentTag = localizedService.getMessage("student.tag");
        examService.readQuestions();
        String message = studentTag + " "
                + studentService.getStudent().getFirstName() +
                " " + studentService.getStudent().getSecondName() +
                "\n" + result + " " + examService.checkTest();
        return message;
    }
}
