package ru.otus.springboot.service;

import ru.otus.springboot.Student;

public interface StudentService {

    String askStudentFirstName(String StudentFirstName);
    String askStudentSecondName(String StudentSecondName);
    Student getStudent();
}
