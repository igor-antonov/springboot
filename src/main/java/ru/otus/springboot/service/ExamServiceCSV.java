package ru.otus.springboot.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;
import ru.otus.springboot.Application;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Igor on 18.12.2018.
 */
@Service
public class ExamServiceCSV implements ExamService {

    private InputService inputService;
    private LocalizedService localizedService;
    private Map<String, String> questions;

    public ExamServiceCSV(InputService inputService, LocalizedService localizedService) {
        this.inputService = inputService;
        this.localizedService = localizedService;
    }

    public Map<String, String> getQuestions() {
        return questions;
    }

    public void readQuestions(){
        try
        {
            questions = new HashMap<String, String>();
            final CSVParser parser =
                    new CSVParserBuilder()
                            .withSeparator(';')
                            .withIgnoreQuotations(true)
                            .build();
            final CSVReader reader =
                    new CSVReaderBuilder(new InputStreamReader(Application.class.getClassLoader().
                            getResourceAsStream(localizedService.getPath())))
                            .withSkipLines(1)
                            .withCSVParser(parser)
                            .build();
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                questions.put(nextLine[0], nextLine[1]);
            }
        }
        catch (Exception e)
        {
            System.out.println("Ошибка при получении CSV: " + e.getMessage() + e.getStackTrace());
        }
    }

    public int checkTest() {
        int correctAnswerCount = 0;
        for (Map.Entry<String, String> question : questions.entrySet()) {
            System.out.println(question.getKey());
            String answer = inputService.ask(question.getKey());
            if (question.getValue().equals(answer)) {
                correctAnswerCount++;
            }
        }
        return correctAnswerCount;
    }
}
