package com.tekenable.model;

import com.tekenable.repository.TrialSelectorQuestionnaireEntryRepository;
import com.tekenable.util.AutowireHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PostPersist;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mark on 25/03/2016.
 */
public class TrialListener {
    private static TrialListener ourInstance = new TrialListener();

    public static TrialListener getInstance() {
        return ourInstance;
    }

    @Autowired
    public TrialSelectorQuestionnaireEntryRepository trialSelectorQuestionnaireEntryRepository;

    public TrialListener() {
    }

    /**
     * As soon as a trial is created we must create TrialSelectorQuestionnaireEntries
     * for each question in the Therapeutic questionnaire
     *
     * This way as each question's answer is checked to indicate it's an acceptable answer
     * we then remove the trialSelectorQuestionnaireEntry
     *
     * @param trial
     */
    @PostPersist
    public void onCreate(Trial trial) {
        AutowireHelper.autowire(this, this.trialSelectorQuestionnaireEntryRepository);
        Set<TrialSelectorQuestionnaireEntry> trialSelectorQuestionnaireEntries = new HashSet<TrialSelectorQuestionnaireEntry>();
        Set<QuestionnaireEntry> questionnaireEntries = trial.getTherapeuticarea().getQuestionnaireentries();
        for ( QuestionnaireEntry questionnaireEntry : questionnaireEntries) {

            for (Answer answer : questionnaireEntry.getAnswers()) {
                trialSelectorQuestionnaireEntries.add(new TrialSelectorQuestionnaireEntry(
                        questionnaireEntry.getQuestion(),
                        answer,
                        trial)
                );
            }
        }

        trialSelectorQuestionnaireEntryRepository.save(trialSelectorQuestionnaireEntries);

    }
}
