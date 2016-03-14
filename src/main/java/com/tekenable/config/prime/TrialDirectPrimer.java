package com.tekenable.config.prime;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by mark on 14/03/2016.
 */
public class TrialDirectPrimer {

    @Autowired
    QuestionAndAnswersPrimer questionAndAnswersPrimer;

    @Autowired
    TrialPrimer trialPrimer;

    @PostConstruct
    public void initDB() {

        questionAndAnswersPrimer.initDB();

        trialPrimer.initDB();
    }
}
