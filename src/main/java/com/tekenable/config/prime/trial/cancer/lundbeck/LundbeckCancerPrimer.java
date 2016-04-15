package com.tekenable.config.prime.trial.cancer.lundbeck;

import com.tekenable.config.AuditConfigurator;
import com.tekenable.config.prime.CancerTrialPrimer;
import com.tekenable.config.prime.trial.cancer.TrialPrimer;
import com.tekenable.model.Trial;
import com.tekenable.model.trial.TrialDirectImage;
import com.tekenable.model.trial.TrialInfo;
import com.tekenable.model.trial.TrialSite;
import com.tekenable.repository.TrialInfoRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

/**
 * Created by mark on 04/04/2016.
 */
@PropertySource("classpath:lundbeck/lundbeck.properties")
public class LundbeckCancerPrimer extends TrialPrimer{

    /**
     * Dr images
     */
    @Value("classpath:lundbeck/images/cuddy.jpg") private Resource trialsite1DoctorImage;
    @Value("classpath:lundbeck/images/house.jpeg") private Resource trialsite2DoctorImage;
    @Value("classpath:lundbeck/images/foreman.jpg") private Resource trialSite3DoctorImage;
    @Value("classpath:lundbeck/images/trialLogo.gif") private Resource trialLogoImage;

    public void initDB() throws IOException {
        super.initDB();

        trialsite1Image = new TrialDirectImage(trialsite1DoctorImage.getFilename(), MediaType.IMAGE_JPEG_VALUE, IOUtils.toByteArray(trialsite1DoctorImage.getInputStream()));
        trialsite2Image = new TrialDirectImage(trialsite2DoctorImage.getFilename(), MediaType.IMAGE_JPEG_VALUE, IOUtils.toByteArray(trialsite2DoctorImage.getInputStream()));
        trialsite3Image = new TrialDirectImage(trialSite3DoctorImage.getFilename(), MediaType.IMAGE_JPEG_VALUE, IOUtils.toByteArray(trialSite3DoctorImage.getInputStream()));
        trialLogo = new TrialDirectImage(trialLogoImage.getFilename(), MediaType.IMAGE_GIF_VALUE, IOUtils.toByteArray(trialLogoImage.getInputStream()));

        createTrial();
    }

}
