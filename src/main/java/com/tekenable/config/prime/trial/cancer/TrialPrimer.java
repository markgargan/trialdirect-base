package com.tekenable.config.prime.trial.cancer;

import com.tekenable.model.SpecialistArea;
import com.tekenable.repository.SpecialistAreaRepository;
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

    protected SpecialistArea specialistAreaCancer = null;

    @Autowired
    protected SpecialistAreaRepository specialistAreaRepository;

    @Autowired
    protected TrialRepository trialRepository;

    public void initDB() throws IOException {

        specialistAreaCancer = specialistAreaRepository.findOne(1);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
