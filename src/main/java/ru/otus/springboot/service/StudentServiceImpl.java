package ru.otus.springboot.service;

import org.springframework.stereotype.Service;
import ru.otus.springboot.Student;

@Service
public class StudentServiceImpl implements StudentService {

    private LocalizedService localizedService;
    private InputService inputService;
    private Student student = new Student();
    private String firstName;
    private String secondName;

    public StudentServiceImpl(LocalizedService localizedService, InputService inputService){
        this.localizedService = localizedService;
        this.inputService = inputService;
    }

    @Override
    public void askStudentFirstName() {
        firstName = localizedService.getMessage("name.first");
        System.out.println(firstName);
        student.setFirstName(inputService.ask(firstName));
    }

    @Override
    public void askStudentSecondName() {
        secondName = localizedService.getMessage("name.second");
        System.out.println(secondName);
        student.setSecondName(inputService.ask(secondName));
    }

    public Student getStudent(){
        return student;
    }
}
