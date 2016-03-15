package com.tekenable.config.prime;

import com.tekenable.model.*;
import com.tekenable.repository.TrialRepository;
import com.tekenable.repository.TrialSelectorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class TrialPrimer extends QuestionAndAnswersPrimer{

    protected final Trial tl_lundbegTrial = new Trial("Lundbeg");
    protected final Trial tl_fooTrial = new Trial("Foo");

    /**
     * Lundbeg Trial Question
     */
    final TrialSelector ts_lundbegCancer0_18Selector = new TrialSelector(tl_lundbegTrial, qn_patientsAge, ar_age0_18);
    final TrialSelector ts_lundbegCancer35_70Selector = new TrialSelector(tl_lundbegTrial, qn_patientsAge, ar_age35_70);

    final TrialSelector ts_fooDiabetesType1Selector = new TrialSelector(tl_fooTrial, qn_patientsCondition, ar_type1Diabetes);
    final TrialSelector ts_fooDiabetesType2Selector = new TrialSelector(tl_fooTrial, qn_patientsCondition, ar_type2Diabetes);

    public void initDB() {
        super.initDB();

        trialRepository.save(new HashSet<Trial>(){{
            add(tl_lundbegTrial);
            add(tl_fooTrial);
        }});

//        trialSelectorRepository.save(new HashSet<TrialSelector>(){{
//            add(ts_lundbegCancer0_18Selector);
//            add(ts_lundbegCancer35_70Selector);
//            add(ts_fooDiabetesType1Selector);
//            add(ts_fooDiabetesType2Selector);
//        }});
    }

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private TrialSelectorRepository trialSelectorRepository;
}
