package ru.otus.springboot.service;

import java.util.Map;

public interface ExamService {
    void readQuestions();
    int checkTest();
    Map<String, String> getQuestions();
}
