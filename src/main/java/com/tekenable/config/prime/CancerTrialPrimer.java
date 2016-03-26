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
    private TherapeuticAreaRepository therapeuticAreaRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private QuestionnaireEntryRepository questionEntryRepository;

    @Autowired
    private TrialSelectorQuestionnaireEntryRepository trialSelectorQuestionEntryRepository;

    @Autowired
    private UserSelectorQuestionnaireEntryRepository userSelectorQuestionEntryRepository;


    // This primer is for questions for the Cancer TherapeuticArea.
    protected final TherapeuticArea therapeuticAreaCancer = new TherapeuticArea("Cancer");
    // main question (no wrong answers here, it determines the initial path (the right questionnaire) to be follow
    // this question has no therapeutic area attached
    protected final Question q1 = new Question("What kind of disease you've got?");
    protected final Answer a11 = new Answer("Astma");
    protected final Answer a12 = new Answer("Cancer");
    protected final Answer a13 = new Answer("Alergy");
    protected final Answer a14 = new Answer("Diabetes");
    protected Set answers1 = new HashSet() {{add(a11); add(a12); add(a13); add(a14);}};
    protected final QuestionnaireEntry entry1 = new QuestionnaireEntry(q1, answers1, therapeuticAreaCancer);

    // the only acceptable answer right now is A12 which is supposed to lead to the following questionnaire
    // however there is no way to do that yet
    // all below questions belong to CANCER therapeutic area so all further questions has TS attached

    protected final Question q2 = new Question("What is the type of your cancer?");
    protected final Answer a21 = new Answer("Stomach");
    protected final Answer a22 = new Answer("Skin");
    protected final Answer a23 = new Answer("Lungs");
    protected final Answer a24 = new Answer("Larynx");
    protected final Set answers2 = new HashSet() {{add(a21); add(a22); add(a23); add(a24);}};
    protected final QuestionnaireEntry entry2 = new QuestionnaireEntry(q2, answers2, therapeuticAreaCancer);

    protected final Question q3 = new Question("What is your age?"); // this is not cancer specific question actually
    protected Answer a31 = new Answer("0-18");
    protected Answer a32 =new Answer("19-35");
    protected Answer a33 = new Answer("35-70");
    protected Answer a34 = new Answer("70+");
    protected final Set answers3 = new HashSet() {{add(a31); add(a32); add(a33); add(a34);}};
    protected final QuestionnaireEntry entry3 = new QuestionnaireEntry(q3, answers3, therapeuticAreaCancer);

    // creating questionnaire out of above questions
    protected final Set cancerEntries = new HashSet<QuestionnaireEntry>() {{add(entry1); add(entry2); add(entry3);}};

    // creating trial structure
    protected final Trial cancerTrial = new Trial("Cancer Trial", therapeuticAreaCancer);

    protected final TrialSelectorQuestionnaireEntry ts11 = new TrialSelectorQuestionnaireEntry(q2, a23, cancerTrial);

    protected final TrialSelectorQuestionnaireEntry ts12 = new TrialSelectorQuestionnaireEntry(q3, a32, cancerTrial);

    protected final UserSelectorQuestionnaireEntry us11 = new UserSelectorQuestionnaireEntry(1000L, q2, a23, therapeuticAreaCancer);

    protected final UserSelectorQuestionnaireEntry us12 = new UserSelectorQuestionnaireEntry(1001L, q3, a32, therapeuticAreaCancer);


    public void initDB() {

        // Associate the QuestionnaireEntries with the Cancer TherapeuticArea
        therapeuticAreaCancer.setQuestionnaireentries(new HashSet<QuestionnaireEntry>(){{
            add(entry1);
            add(entry2);
            add(entry3);
        }});

        questionRepository.save(new HashSet<Question>() {{
            add(q1);
            add(q2);
            add(q3);
        }});

        entry1.setTherapeuticArea(therapeuticAreaCancer);
        entry2.setTherapeuticArea(therapeuticAreaCancer);
        entry3.setTherapeuticArea(therapeuticAreaCancer);

        therapeuticAreaRepository.save(therapeuticAreaCancer);

        trialRepository.save(cancerTrial);

        userSelectorQuestionEntryRepository.save(new HashSet<UserSelectorQuestionnaireEntry>(){{
            add(us11);
            add(us12);
        }});

    }
}
