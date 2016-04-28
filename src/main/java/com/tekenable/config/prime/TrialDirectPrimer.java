package com.tekenable.config.prime;

import com.tekenable.config.AuditConfigurator;
import com.tekenable.config.prime.trial.cancer.glaxo.GlaxoCancerPrimer;
import com.tekenable.config.prime.trial.cancer.lundbeck.LundbeckCancerPrimer;
import com.tekenable.config.prime.trial.cancer.pfizer.PfizerCancerPrimer;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.io.InputStream;

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

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("classpath:primer/imports/trialSites.sql")
    private Resource trialSitesResource;

    @PostConstruct
    public void initDB() throws IOException {

        this.configureTriggers();
        this.runPrimers();
        this.runImportFiles();
    }

    private void configureTriggers() {
        auditConfigurator.addAuditStuff();
    }

    private void runPrimers() throws IOException {

        cancerQuestionnairePrimer.initDB();
        lundbeckPrimer.initDB();
        pfizerPrimer.initDB();

    }

    private void runImportFiles() throws IOException {
        jdbcTemplate.batchUpdate(new String[]{IOUtils.toString(trialSitesResource.getInputStream())});
    }
}
