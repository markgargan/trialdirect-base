package com.tekenable.config.prime.trial.cancer;

import com.tekenable.config.AuditConfigurator;
import com.tekenable.config.prime.CancerTrialPrimer;
import com.tekenable.config.prime.trial.cancer.pfizer.PfizerCancerPrimer;
import com.tekenable.model.Trial;
import com.tekenable.model.trial.TrialDirectImage;
import com.tekenable.model.trial.TrialInfo;
import com.tekenable.model.trial.TrialSite;
import com.tekenable.repository.TrialInfoRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

/**
 * Created by mark on 04/04/2016.
 */
public abstract class TrialPrimer extends CancerTrialPrimer implements ApplicationContextAware {

    /**
     * Trial Info
     */
    @Value("${trial.title}") protected String trialTitle;
    @Value("${trialinfo.summary}") protected String trialInfoSummary;
    @Value("${trialinfo.fullDescription}") protected String trialInfoFullDescription;

    /**
     * Trial Site 1 Info
     */
    @Value("${trialsite1.director}") protected String trialSite1_director;
    @Value("${trialsite1.bio}") protected String trialSite1_bio;
    @Value("${trialsite1.address1}") protected String trialSite1_address1;
    @Value("${trialsite1.address2}") protected String trialSite1_address2;
    @Value("${trialsite1.address3}") protected String trialSite1_address3;
    @Value("${trialsite1.address4}") protected String trialSite1_address4;
    @Value("${trialsite1.address5}") protected String trialSite1_address5;
    @Value("${trialsite1.country}") protected String trialSite1_country;
    @Value("${trialsite1.site.map}") protected String trialSite1_site_map;

    /**
     * Trial Site 2 Info
     */
    @Value("${trialsite1.director}") protected String trialSite2_director;
    @Value("${trialsite1.bio}") protected String trialSite2_bio;
    @Value("${trialsite1.address1}") protected String trialSite2_address1;
    @Value("${trialsite1.address2}") protected String trialSite2_address2;
    @Value("${trialsite1.address3}") protected String trialSite2_address3;
    @Value("${trialsite1.address4}") protected String trialSite2_address4;
    @Value("${trialsite1.address5}") protected String trialSite2_address5;
    @Value("${trialsite1.country}") protected String trialSite2_country;
    @Value("${trialsite1.site.map}") protected String trialSite2_site_map;

    /**
     * Trial Site 3 Info
     */
    @Value("${trialsite1.director}") protected String trialSite3_director;
    @Value("${trialsite1.bio}") protected String trialSite3_bio;
    @Value("${trialsite1.address1}") protected String trialSite3_address1;
    @Value("${trialsite1.address2}") protected String trialSite3_address2;
    @Value("${trialsite1.address3}") protected String trialSite3_address3;
    @Value("${trialsite1.address4}") protected String trialSite3_address4;
    @Value("${trialsite1.address5}") protected String trialSite3_address5;
    @Value("${trialsite1.country}") protected String trialSite3_country;
    @Value("${trialsite1.site.map}") protected String trialSite3_site_map;


    protected TrialDirectImage trialsite1Image = null;
    protected TrialDirectImage trialsite2Image = null;
    protected TrialDirectImage trialsite3Image = null;
    protected TrialDirectImage trialLogo = null;

    /**
     * Dr Bios
     */
    @Value("${trial1.bio}") protected String trialsite1Bio;
    @Value("${trial2.bio}") protected String trialsite2Bio;
    @Value("${trial3.bio}") protected String trialsite3Bio;

    protected ApplicationContext ctx = null;

    public void initDB() throws IOException {
        super.initDB();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    protected void createTrial() {

        Trial trial = new Trial(trialTitle, therapeuticAreaCancer);

        final TrialInfo trialInfo = new TrialInfo(trial, trialInfoSummary);

        trialInfo.setTrialLogo(trialLogo);

        trial.setTrialInfos(new HashSet<TrialInfo>(){{
            add(trialInfo);
        }});

        final TrialSite trialSite1 = new TrialSite(trialInfo,
                trialSite1_director,
                trialSite1_bio,
                trialSite1_address1,
                trialSite1_address2,
                trialSite1_address3,
                trialSite1_address4,
                trialSite1_address5,
                trialSite1_country,
                trialSite1_site_map);

        final TrialSite trialSite2 = new TrialSite(trialInfo,
                trialSite2_director,
                trialSite2_bio,
                trialSite2_address2,
                trialSite2_address2,
                trialSite2_address3,
                trialSite2_address4,
                trialSite2_address5,
                trialSite2_country,
                trialSite2_site_map);

        final TrialSite trialSite3 = new TrialSite(trialInfo,
                trialSite3_director,
                trialSite3_bio,
                trialSite3_address1,
                trialSite3_address2,
                trialSite3_address3,
                trialSite3_address4,
                trialSite3_address5,
                trialSite3_country,
                trialSite3_site_map);
        
        trialSite1.setTrialSiteImage(trialsite1Image);
        trialSite2.setTrialSiteImage(trialsite2Image);
        trialSite3.setTrialSiteImage(trialsite3Image);

        trialInfo.setTrialSites(new HashSet<TrialSite>() {{
            add(trialSite1);
            add(trialSite2);
            add(trialSite3);
        }});

        trialRepository.save(trial);
    }
}
