package com.tekenable.config.prime;

import com.tekenable.model.*;
import com.tekenable.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class CancerQuestionnairePrimer {

    @Autowired
    private SpecialistAreaRepository specialistAreaRepository;

    @Autowired
    private QuestionRepository questionRepository;

    /*@Autowired
    private AnswerRepository answerRepository;*/

    @Autowired
    protected TrialRepository trialRepository;

    /*@Qualifier("")
    @Autowired
    private QuestionnaireEntryRepository questionEntryRepository;

    @Autowired
    private TrialSelectorQuestionnaireEntryRepository trialSelectorQuestionEntryRepository;*/

    @Autowired
    private UserSelectorQuestionnaireEntryRepository userSelectorQuestionEntryRepository;

    @Autowired
    private UserRepository userRepository;

    // This primer is for questions for the Cancer SpecialistArea.
    protected final SpecialistArea specialistAreaCancer = new SpecialistArea("Lung Cancer", 1, 1);
    // main question (no wrong answers here, it determines the initial path (the right questionnaire) to be follow
    // this question has no therapeutic area attached
    protected final Question q1 = new Question("What is the type of your cancer?", 1);
    protected final Answer a11 = new Answer("Stomach", 1);
    protected final Answer a12 = new Answer("Skin", 2);
    protected final Answer a13 = new Answer("Lungs", 3);
    protected final Answer a14 = new Answer("Larynx", 4);
    protected Set answers1 = new HashSet() {{add(a11); add(a12); add(a13); add(a14);}};
    protected final QuestionnaireEntry entry1 = new QuestionnaireEntry(q1, answers1, specialistAreaCancer);

    // the only acceptable answer right now is A12 which is supposed to lead to the following questionnaire
    // however there is no way to do that yet
    // all below questions belong to CANCER therapeutic area so all further questions has TS attached

    protected final Question q2 = new Question("How long do you suffer from cancer (in years)?", 2);
    protected final Answer a21 = new Answer("0-3", 1);
    protected final Answer a22 = new Answer("3-5", 2);
    protected final Answer a23 = new Answer("5-10", 3);
    protected final Answer a24 = new Answer("10 or more", 4);
    protected final Set answers2 = new HashSet() {{add(a21); add(a22); add(a23); add(a24);}};
    protected final QuestionnaireEntry entry2 = new QuestionnaireEntry(q2, answers2, specialistAreaCancer);

    protected final Question q3 = new Question("What is your age?", 3); // this is not cancer specific question actually
    protected Answer a31 = new Answer("0-18", 1);
    protected Answer a32 =new Answer("19-35", 2);
    protected Answer a33 = new Answer("35-70", 3);
    protected Answer a34 = new Answer("70+", 4);
    protected final Set answers3 = new HashSet() {{add(a31); add(a32); add(a33); add(a34);}};
    protected final QuestionnaireEntry entry3 = new QuestionnaireEntry(q3, answers3, specialistAreaCancer);

    // creating questionnaire out of above questions
    protected final Set cancerEntries = new HashSet<QuestionnaireEntry>() {{add(entry1); add(entry2); add(entry3);}};

    // creating trial structure
    protected final Trial cancerTrial = new Trial("Cancer Trial", specialistAreaCancer);

    protected final TrialSelectorQuestionnaireEntry ts11 = new TrialSelectorQuestionnaireEntry(q2, a23, cancerTrial);

    protected final TrialSelectorQuestionnaireEntry ts12 = new TrialSelectorQuestionnaireEntry(q3, a32, cancerTrial);

    protected final User robert = new User("Robert", specialistAreaCancer);

//    protected final User kate = new User("Kate", specialistAreaCancer);
//
//    protected final User dave = new User("Dave", specialistAreaCancer);

    protected final UserSelectorQuestionnaireEntry us_robert_q2_a23_cancer = new UserSelectorQuestionnaireEntry(robert, q2, a23, specialistAreaCancer);

    protected final UserSelectorQuestionnaireEntry us_robert_q3_a32_cancer = new UserSelectorQuestionnaireEntry(robert, q3, a32, specialistAreaCancer);


    public void initDB() throws IOException {

        // Associate the QuestionnaireEntries with the Cancer SpecialistArea
        specialistAreaCancer.setQuestionnaireentries(new HashSet<QuestionnaireEntry>(){{
            add(entry1);
            add(entry2);
            add(entry3);
        }});

        questionRepository.save(new HashSet<Question>() {{
            add(q1);
            add(q2);
            add(q3);
        }});

        entry1.setSpecialistArea(specialistAreaCancer);
        entry2.setSpecialistArea(specialistAreaCancer);
        entry3.setSpecialistArea(specialistAreaCancer);

        specialistAreaRepository.save(specialistAreaCancer);

//        trialRepository.save(cancerTrial);

        userRepository.save(robert);

        userSelectorQuestionEntryRepository.save(new HashSet<UserSelectorQuestionnaireEntry>(){{
            add(us_robert_q2_a23_cancer);
            add(us_robert_q3_a32_cancer);
        }});
    }
}
