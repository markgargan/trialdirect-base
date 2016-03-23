package com.tekenable.config.prime;

import com.tekenable.model.*;
import com.tekenable.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by smoczyna on 17/03/16.
 * this is the first method of questionnaire population
 */
public class CancerTrialPrimer {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private QuestionEntryRepository questionEntryRepository;

    @Autowired
    private TrialSelectorQuestionEntryRepository trialSelectorQuestionEntryRepository;


    // main class holding the trial questions and all possible answers
    protected final Questionnaire cancerQuestionnaire = new Questionnaire("Cancer Questionnaire");

    // the main question (type of disease) is served by trial therapeutic area now so it's not necessary here
    // all below questions belong to CANCER therapeutic area so all further questions has TS attached
    protected final TherapeuticArea taCancer = new TherapeuticArea("cancer");

    //all of the answers are correct here
    protected final Question q1 = new Question("What is the type of your cancer?", taCancer);
    protected final Answer a11 = new Answer("Stomach");
    protected final Answer a12 = new Answer("Skin");
    protected final Answer a13 = new Answer("Lungs");
    protected final Answer a14 = new Answer("Larynx");
    protected final Set answers1 = new HashSet() {{add(a11); add(a12); add(a13); add(a14);}};
    protected final QuestionnaireEntry entry2 = new QuestionnaireEntry("Type of cancer", q1, answers1, cancerQuestionnaire);

    // only 1 answer is right here
    protected final Question q2 = new Question("How long do (number of years) you suffer from cancer?");
    protected final Answer a21 = new Answer("1-2");
    protected final Answer a22 = new Answer("2-5");
    protected final Answer a23 = new Answer("5-10"); // this is the expected answer
    protected final Answer a24 = new Answer("10 and more");
    protected final Set answers2 = new HashSet() {{add(a21); add(a22); add(a23); add(a24);}};
    protected final QuestionnaireEntry entry1 = new QuestionnaireEntry("Cancer time frame", q1, answers2, cancerQuestionnaire);
    protected final QuestionEntry qe2 = new QuestionEntry(q2, a23);

    // only 1 answer is right here
    protected final Question q3 = new Question("What is your age?", taCancer);
    protected Answer a31 = new Answer("0-18");
    protected Answer a32 =new Answer("19-35"); // this is the expected answer
    protected Answer a33 = new Answer("35-70");
    protected Answer a34 = new Answer("70+");
    protected final Set answers3 = new HashSet() {{add(a31); add(a32); add(a33); add(a34);}};
    protected final QuestionnaireEntry entry3 = new QuestionnaireEntry("Patient age", q3, answers3, cancerQuestionnaire);
    protected final QuestionEntry qe3 = new QuestionEntry(q3, a32);

    // creating questionnaire out of above questions
    protected final Set cancerEntries = new HashSet() {{add(entry1); add(entry2); add(entry3);}};

    // this thing doesn't save questionnaire id in questionnaire entries (they are not explicitly assigned)
    //protected final Questionnaire cancerQuestionnaire = new Questionnaire("Cancer Questionnaire", cancerEntries);

    // creating trial structure
    protected final Trial cancerTrial = new Trial("Cancer Trial");

    protected final TrialSelectorQuestionEntry ts11 = new TrialSelectorQuestionEntry(q2, a23, cancerTrial);
    protected final TrialSelectorQuestionEntry ts12 = new TrialSelectorQuestionEntry(q3, a32, cancerTrial);

    public void initDB() {

        questionRepository.save(new HashSet<Question>() {{
            add(q1);
            add(q2);
            add(q3);
        }});

        cancerQuestionnaire.setQuestionaireEntries(cancerEntries);
        questionnaireRepository.save(new HashSet<Questionnaire>() {{
            add(cancerQuestionnaire);
        }});

        trialRepository.save(cancerTrial);

        questionEntryRepository.save(new HashSet<QuestionEntry>(){{
            add(qe2);
            add(qe3);
        }});

        trialSelectorQuestionEntryRepository.save(new HashSet<TrialSelectorQuestionEntry>(){{
            add(ts11);
            add(ts12);
        }});
    }
}
