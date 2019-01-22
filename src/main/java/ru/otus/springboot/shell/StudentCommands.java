package ru.otus.springboot.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.springboot.service.StudentService;

@ShellComponent
public class StudentCommands {
    private final StudentService service;

    public StudentCommands(StudentService studentService){
        this.service = studentService;
    }

    @ShellMethod("Input first name")
    public String name(
            @ShellOption String name
    ){
        return service.askStudentFirstName(name);
    }

    @ShellMethod("Input second name")
    public String surname(
            @ShellOption String surname
    ){
        return service.askStudentSecondName(surname);
    }
}
