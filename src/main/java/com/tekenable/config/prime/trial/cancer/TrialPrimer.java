package com.tekenable.config.prime.trial.cancer;

import com.tekenable.model.TherapeuticArea;
import com.tekenable.repository.TherapeuticAreaRepository;
import com.tekenable.repository.TrialRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;

/**
 * Created by mark on 04/04/2016.
 */
public abstract class TrialPrimer implements ApplicationContextAware {

    protected ApplicationContext ctx = null;

    protected TherapeuticArea therapeuticAreaCancer = null;

    @Autowired
    protected TherapeuticAreaRepository therapeuticAreaRepository;

    @Autowired
    protected TrialRepository trialRepository;

    public void initDB() throws IOException {

        therapeuticAreaCancer = therapeuticAreaRepository.findOne(1);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
