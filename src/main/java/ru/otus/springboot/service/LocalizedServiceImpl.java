package ru.otus.springboot.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.springboot.config.YamlProp;

import java.util.Formatter;
import java.util.Locale;

@Service
public class LocalizedServiceImpl implements LocalizedService {

    private Locale locale = Locale.getDefault();;
    private MessageSource messageSource;
    private String csvPath;

    public LocalizedServiceImpl(MessageSource messageSource, YamlProp prop){
        this.csvPath = prop.getPath();
        this.messageSource = messageSource;
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getMessage(String message){
        return messageSource.getMessage(message, null, locale);
    }

    public String getPath(){
        return String.format(csvPath, locale.toLanguageTag());
    }
}
