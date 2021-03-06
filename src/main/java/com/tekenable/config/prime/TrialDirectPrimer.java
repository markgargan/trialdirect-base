package com.tekenable.config.prime;

import com.tekenable.config.AuditConfigurator;
import com.tekenable.config.prime.trial.cancer.TrialPrimer;
import com.tekenable.config.prime.trial.cancer.glaxo.GlaxoCancerPrimer;
import com.tekenable.config.prime.trial.cancer.lundbeck.LundbeckCancerPrimer;
import com.tekenable.config.prime.trial.cancer.pfizer.PfizerCancerPrimer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;

public class TrialDirectPrimer {


    @Autowired
    CancerQuestionnairePrimer cancerQuestionnairePrimer;

    @Autowired
    GlaxoCancerPrimer glaxoPrimer;

    @Autowired
    LundbeckCancerPrimer lundbeckPrimer;

    @Autowired
    PfizerCancerPrimer pfizerPrimer;

    @Autowired
    private AuditConfigurator auditConfigurator;


    @PostConstruct
    public void initDB() throws IOException {
        this.configureAudit();
        this.runPrimers();
    }

    private void configureAudit() {
        auditConfigurator.checkAuditConfiguration();
    }

    private void runPrimers() throws IOException {

        cancerQuestionnairePrimer.initDB();
//            glaxoPrimer.initDB();
        lundbeckPrimer.initDB();
        pfizerPrimer.initDB();

    }
}
