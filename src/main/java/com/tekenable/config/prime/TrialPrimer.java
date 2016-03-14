package com.tekenable.config.prime;

import com.tekenable.model.*;
import com.tekenable.repository.AnswerRepository;
import com.tekenable.repository.TrialRepository;
import com.tekenable.repository.TherapeuticAreaRepository;
import com.tekenable.repository.TrialSelectorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashSet;

public class TrialPrimer {

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private TrialSelectorRepository trialSelectorRepository;

    public void initDB() {

        final Trial t_lundbegTrial = new Trial("Lundbeg");
        final Trial t_fooTrial = new Trial("foo");

        final TrialSelector ts_lundbegSelector = new TrialSelector("Mark", t_lundbegTrial);
        final TrialSelector ts_fooSelector = new TrialSelector("Jar", t_fooTrial);

        trialSelectorRepository.save(new HashSet<TrialSelector>(){{
            add(ts_lundbegSelector);
            add(ts_fooSelector);
        }});
    }
}
