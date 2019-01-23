package ru.otus.springboot.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.springboot.service.ExamService;
import ru.otus.springboot.service.LocalizedService;
import ru.otus.springboot.service.StudentService;

import java.util.Locale;

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
        String result = localizedService.getMessage("result");
        String studentTag = localizedService.getMessage("student.tag");
        localizedService.getLocale().toLanguageTag();
        examService.readQuestions();
        String message = studentTag + " "
                + studentService.getStudent().getFirstName() +
                " " + studentService.getStudent().getSecondName() +
                "\n" + result + " " + examService.checkTest();
        return message;
    }

    @ShellMethod("Set locale")
    public String locale(String inputLocale){
        String message = "";
        switch (inputLocale){
            case "ru" : localizedService.setLocale(new Locale("ru", "RU")); break;
            case "en" : localizedService.setLocale(Locale.ENGLISH); break;
            default: message = localizedService.getMessage("locale.command.err");
        }
        return message == ""
               ? localizedService.getLocale().toLanguageTag()
                : message;
    }

    @ShellMethodAvailability("test")
    public Availability availabilityTest(){
        String message = "";
        if (studentService.getStudent().getFirstName() == null){
            message = localizedService.getMessage("name.first.err");
        }
        else if (studentService.getStudent().getSecondName() == null){
            message = localizedService.getMessage("name.second.err");
        }
        return message == ""
                ? Availability.available()
                : Availability.unavailable(message);
    }
}
