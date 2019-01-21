package ru.otus.springboot.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by Igor on 18.12.2018.
 */

@Service
public class InputServiceImpl implements InputService{

    private Scanner scanner;

    public InputServiceImpl(InputStream in) {
        scanner = new Scanner(in);
    }

    public InputServiceImpl() {
        scanner = new Scanner(System.in);
    }

    public String ask(String message) {
        return scanner.nextLine();
    }
}
