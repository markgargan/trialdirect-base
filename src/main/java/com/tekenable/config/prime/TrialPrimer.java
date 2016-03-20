package com.tekenable.config.prime;

import com.tekenable.model.QuestionEntry;
import com.tekenable.model.Trial;
import com.tekenable.model.TrialSelectorQuestionEntry;
import com.tekenable.repository.QuestionEntryRepository;
import com.tekenable.repository.TrialRepository;
import com.tekenable.repository.TrialSelectorQuestionEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class TrialPrimer extends QuestionAndAnswersPrimer{

    protected final Trial tl_lundbegTrial = new Trial("Lundbeg");
    protected final Trial tl_fooTrial = new Trial("Foo");

    protected final QuestionEntry qe_lundbeg_age0_18 = new QuestionEntry(qn_patientsAge, ar_age0_18);
    protected final TrialSelectorQuestionEntry tse_lundbeg_age0_18 = new TrialSelectorQuestionEntry(qn_patientsAge, ar_age0_18, tl_lundbegTrial);

    /**
     * Lundbeg Trial Question
     */
    public void initDB() {
        super.initDB();

        trialRepository.save(new HashSet<Trial>(){{
            add(tl_lundbegTrial);
            add(tl_fooTrial);
        }});

        questionEntryRepository.save(new HashSet<QuestionEntry>(){{
            add(qe_lundbeg_age0_18);
        }});

        trialSelectorQuestionEntryRepository.save(new HashSet<TrialSelectorQuestionEntry>(){{
            add(tse_lundbeg_age0_18);
        }});

    }

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private QuestionEntryRepository questionEntryRepository;

    @Autowired
    private TrialSelectorQuestionEntryRepository trialSelectorQuestionEntryRepository;

}
