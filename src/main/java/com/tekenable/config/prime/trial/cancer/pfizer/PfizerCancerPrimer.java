package com.tekenable.config.prime.trial.cancer.pfizer;

import com.tekenable.config.prime.trial.cancer.TrialPrimer;
import com.tekenable.model.trial.TrialDirectImage;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * Created by mark on 04/04/2016.
 */
@PropertySource("classpath:pfizer/pfizer.properties")
public class PfizerCancerPrimer extends TrialPrimer {

    /**
     * Dr images
     */
    @Value("classpath:pfizer/images/cox.png") private Resource trialsite1DoctorImage;
    @Value("classpath:pfizer/images/elliot.jpg") private Resource trialsite2DoctorImage;
    @Value("classpath:pfizer/images/kelso.png") private Resource trialSite3DoctorImage;
    @Value("classpath:pfizer/images/pfizer.png") private Resource pfizerLogoImage;

    public void initDB() throws IOException {
        super.initDB();

        trialsite1Image = new TrialDirectImage(trialsite1DoctorImage.getFilename(), MediaType.IMAGE_PNG_VALUE, IOUtils.toByteArray(trialsite1DoctorImage.getInputStream()));
        trialsite2Image = new TrialDirectImage(trialsite2DoctorImage.getFilename(), MediaType.IMAGE_JPEG_VALUE, IOUtils.toByteArray(trialsite2DoctorImage.getInputStream()));
        trialsite3Image = new TrialDirectImage(trialSite3DoctorImage.getFilename(), MediaType.IMAGE_PNG_VALUE, IOUtils.toByteArray(trialSite3DoctorImage.getInputStream()));
        trialLogo = new TrialDirectImage(pfizerLogoImage.getFilename(), MediaType.IMAGE_PNG_VALUE, IOUtils.toByteArray(pfizerLogoImage.getInputStream()));

        createTrial();
    }

}
