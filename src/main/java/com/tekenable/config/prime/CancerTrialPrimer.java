package com.tekenable.config.prime;

import com.tekenable.model.*;
import com.tekenable.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by smoczyna on 17/03/16.
 */
public class CancerTrialPrimer {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private QuestionnaireEntryRepository questionEntryRepository;

    @Autowired
    private TrialSelectorQuestionEntryRepository trialSelectorEntryRepository;

    // main question (no wrong answers here, it determines the initial path (the right questionnaire) to be follow
    // this question has no therapeutic area attached
    protected final Question q1 = new Question("What kind of disease you've got?");
    protected final Answer a11 = new Answer("Astma");
    protected final Answer a12 = new Answer("Cancer");
    protected final Answer a13 = new Answer("Alergy");
    protected final Answer a14 = new Answer("Diabetes");
    protected Set answers1 = new HashSet() {{add(a11); add(a12); add(a13); add(a14);}};
    protected final QuestionnaireEntry entry1 = new QuestionnaireEntry(q1, answers1);

    // the only acceptable answer right now is A12 which is supposed to lead to the following questionnaire
    // however there is no way to do that yet
    // all below questions belong to CANCER therapeutic area so all further questions has TS attached
    protected final TherapeuticArea taCancer = new TherapeuticArea("cancer");

    protected final Question q2 = new Question("What is the type of your cancer?", taCancer);
    protected final Answer a21 = new Answer("Stomach");
    protected final Answer a22 = new Answer("Skin");
    protected final Answer a23 = new Answer("Lungs");
    protected final Answer a24 = new Answer("Larynx");
    protected final Set answers2 = new HashSet() {{add(a21); add(a22); add(a23); add(a24);}};
    protected final QuestionnaireEntry entry2 = new QuestionnaireEntry(q2, answers2);

    protected final Question q3 = new Question("What is your age?", taCancer); // this is not cancer specific question actually
    protected Answer a31 = new Answer("0-18");
    protected Answer a32 =new Answer("19-35");
    protected Answer a33 = new Answer("35-70");
    protected Answer a34 = new Answer("70+");
    protected final Set answers3 = new HashSet() {{add(a31); add(a32); add(a33); add(a34);}};
    protected final QuestionnaireEntry entry3 = new QuestionnaireEntry( q3, answers3);

    // creating questionnaire out of above questions
    protected final Set cancerEntries = new HashSet<QuestionnaireEntry>() {{add(entry1); add(entry2); add(entry3);}};
    protected final Questionnaire cancerQuestionnaire = new Questionnaire("Cancer Questionnaire", cancerEntries);

    // creating trial structure
    protected final Trial cancerTrial = new Trial("Cancer Trial");
//    protected final QuestionEntry qe11 = new QuestionEntry(q2, a23);
    protected final TrialSelectorQuestionEntry ts11 = new TrialSelectorQuestionEntry(q2, a23, cancerTrial);

//    protected final QuestionEntry qe12 = new QuestionEntry(q3, a32);
    protected final TrialSelectorQuestionEntry ts12 = new TrialSelectorQuestionEntry(q3, a32, cancerTrial);

    public void initDB() {

        questionRepository.save(new HashSet<Question>() {{
            add(q1);
            add(q2);
            add(q3);
        }});

        entry1.setQuestionnaire(cancerQuestionnaire);
        entry2.setQuestionnaire(cancerQuestionnaire);
        entry3.setQuestionnaire(cancerQuestionnaire);

        questionnaireRepository.save(new HashSet<Questionnaire>() {{
            add(cancerQuestionnaire);
        }});

        trialRepository.save(cancerTrial);

//        questionEntryRepository.save(new HashSet<QuestionEntry>(){{
//            add(qe11);
//            add(qe12);
//        }});

        trialSelectorEntryRepository.save(new HashSet<TrialSelectorQuestionEntry>(){{
            add(ts11);
            add(ts12);
        }});
    }
}
