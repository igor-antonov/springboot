package ru.otus.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.springboot.config.YamlProp;

@SpringBootApplication
@EnableConfigurationProperties(YamlProp.class)
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}

