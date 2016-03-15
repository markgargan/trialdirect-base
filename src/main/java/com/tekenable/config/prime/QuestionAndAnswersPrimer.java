package com.tekenable.config.prime;

import com.tekenable.model.Answer;
import com.tekenable.model.Question;
import com.tekenable.model.TherapeuticArea;
import com.tekenable.repository.AnswerRepository;
import com.tekenable.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class QuestionAndAnswersPrimer {

    // Create the TherapeuticAreas
    // Create TherapeuticArea
    protected final TherapeuticArea ta_cancer = new TherapeuticArea("cancer");
    protected final TherapeuticArea ta_diabetes = new TherapeuticArea("ta_diabetes");

    protected final Question qn_patientsCondition = new Question("What is your patients's condition", new HashSet<TherapeuticArea>() {{
        add(ta_cancer);
    }});

    protected final Question qn_patientsAge = new Question("What is your patients's age", new HashSet<TherapeuticArea>() {{
        add(ta_cancer);
        add(ta_diabetes);
    }});

    protected Answer ar_type1Diabetes = new Answer("Type 1 ta_diabetes", qn_patientsCondition);
    protected Answer ar_type2Diabetes = new Answer("Type 2 ta_diabetes", qn_patientsCondition);

    protected Answer ar_age0_18 = new Answer("0-18", qn_patientsAge);
    protected Answer ar_age19_35 =new Answer("19-35", qn_patientsAge);
    protected Answer ar_age35_70 = new Answer("35-70", qn_patientsAge);
    protected Answer ar_age70_plus = new Answer("70+", qn_patientsAge);

    public void initDB() {

        questionRepository.save(new HashSet<Question>() {{
            add(qn_patientsCondition);
            add(qn_patientsAge);
        }});

        answerRepository.save(new HashSet<Answer>() {{
            add(ar_type1Diabetes);
            add(ar_type2Diabetes);

            add(ar_age0_18);
            add(ar_age19_35);
            add(ar_age35_70);
            add(ar_age70_plus);
        }});
    }


    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;
}
