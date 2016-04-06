package com.tekenable.config.prime;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class TrialDirectPrimer {

//    @Autowired
//    CancerTrialPrimer cancerTrialPrimer;


    @Autowired
    TrialPrimer trialPrimer;


    @PostConstruct
    public void initDB() {

        try {
//            cancerTrialPrimer.initDB();
            trialPrimer.initDB();
        }catch (NullPointerException pae) {
            pae.printStackTrace();
        }
    }
}
