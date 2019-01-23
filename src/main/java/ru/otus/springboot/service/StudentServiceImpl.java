package ru.otus.springboot.service;

import org.springframework.stereotype.Service;
import ru.otus.springboot.Student;

@Service
public class StudentServiceImpl implements StudentService {

    private LocalizedService localizedService;
    private Student student = new Student();
    private String firstName;
    private String secondName;

    public StudentServiceImpl(LocalizedService localizedService){
        this.localizedService = localizedService;
    }

    @Override
    public String askStudentFirstName(String studentFirstName) {
        firstName = localizedService.getMessage("name.first");
        student.setFirstName(studentFirstName);
        return firstName + ": " + studentFirstName;
    }

    @Override
    public String askStudentSecondName(String studentSecondName) {
        secondName = localizedService.getMessage("name.second");
        student.setSecondName(studentSecondName);
        return secondName + ": " + studentSecondName;
    }

    public Student getStudent(){
        return student;
    }
}
