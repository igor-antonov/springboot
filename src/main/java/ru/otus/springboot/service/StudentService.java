package ru.otus.springboot.service;

import ru.otus.springboot.Student;

public interface StudentService {

    void askStudentFirstName();
    void askStudentSecondName();
    Student getStudent();
}
