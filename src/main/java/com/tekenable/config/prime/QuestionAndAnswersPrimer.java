package com.tekenable.config.prime;

import com.tekenable.model.Answer;
import com.tekenable.model.Question;
import com.tekenable.model.TherapeuticArea;
import com.tekenable.repository.AnswerRepository;
import com.tekenable.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashSet;

public class QuestionAndAnswersPrimer {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public void initDB() {

        // Create the TherapeuticAreas
        // Create TherapeuticArea
        final TherapeuticArea cancer = new TherapeuticArea("cancer");
        final TherapeuticArea diabetes = new TherapeuticArea("diabetes");

        final Question q_patientsCondition = new Question("What is your patients's condition", new HashSet<TherapeuticArea>() {{
            add(cancer);
        }});

        final Question q_patientsAge = new Question("What is your patients's age", new HashSet<TherapeuticArea>() {{
            add(cancer);
            add(diabetes);
        }});


        questionRepository.save(new HashSet<Question>() {{
            add(q_patientsCondition);
            add(q_patientsAge);
        }});

        answerRepository.save(new HashSet<Answer>() {{
            add(new Answer("Type 1 diabetes", q_patientsCondition));
            add(new Answer("Type 2 diabetes", q_patientsCondition));

            add(new Answer("0-18", q_patientsAge));
            add(new Answer("19-35", q_patientsAge));
            add(new Answer("35-70", q_patientsAge));
            add(new Answer("70+", q_patientsAge));
        }});

    }
}
