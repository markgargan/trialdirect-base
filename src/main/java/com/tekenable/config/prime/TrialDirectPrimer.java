package com.tekenable.config.prime;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class TrialDirectPrimer {

    @Autowired
    CancerTrialPrimer cancerTrialPrimer;

    @Autowired
    DiabetesTrialPrimer diabetesTrialPrimer;

    @PostConstruct
    public void initDB() {

        try {
            cancerTrialPrimer.initDB();
            diabetesTrialPrimer.initDB();

        }catch (NullPointerException pae) {
            pae.printStackTrace();
        }
    }
}
