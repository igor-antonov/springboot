package ru.otus.springboot.service;

import java.util.Locale;

public interface LocalizedService {
    String getMessage(String message);
    void setLocale(Locale locale);
    String getPath();
    Locale getLocale();
}
