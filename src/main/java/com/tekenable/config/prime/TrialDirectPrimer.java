package com.tekenable.config.prime;

import com.tekenable.model.Answer;
import com.tekenable.model.Question;
import com.tekenable.model.TherapeuticArea;
import com.tekenable.repository.AnswerRepository;
import com.tekenable.repository.QuestionRepository;
import com.tekenable.repository.TherapeuticAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Created by mark on 14/03/2016.
 */
@Component
public class TrialDirectPrimer {

    @Autowired
    private TherapeuticAreaRepository therapeuticAreaRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public void initDB() {

        // Create the TherapeuticAreas
        // Create TherapeuticArea
        final TherapeuticArea cancer = new TherapeuticArea("cancer");
        final TherapeuticArea diabetes = new TherapeuticArea("diabetes");

        Question whatAge = new Question("What is your patient's condition");
        final Answer type1Diabetes = new Answer("type 1 diabetes", whatAge);
        final Answer type2Diabetes = new Answer("type 2 diabetes", whatAge);

        answerRepository.save(new HashSet<Answer>(){{
            add(type1Diabetes);
            add(type2Diabetes);
        }});
    }
}
