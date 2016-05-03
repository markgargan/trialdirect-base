package com.tekenable.config.prime.trial.cancer.pfizer;

import com.tekenable.config.prime.trial.cancer.TrialPrimer;
import com.tekenable.model.Trial;
import com.tekenable.model.trial.TrialDirectImage;
import com.tekenable.model.trial.TrialInfo;
import com.tekenable.model.trial.TrialSite;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by mark on 04/04/2016.
 */
public class PfizerCancerPrimer extends TrialPrimer {

    /**
     * Dr images
     */
    @Value("classpath:primer/pfizer/images/mercy.jpg") private Resource trialSite1DoctorImage;
    @Value("classpath:primer/pfizer/images/mercy.jpg") private Resource trialSite2DoctorImage;
    @Value("classpath:primer/pfizer/images/mercy.jpg") private Resource trialSite3DoctorImage;
    @Value("classpath:primer/pfizer/images/pfizer.png") private Resource pfizerLogoImage;

    public void initDB() throws IOException {
        super.initDB();

        trialsite1Image = new TrialDirectImage(trialSite1DoctorImage.getFilename(), MediaType.IMAGE_JPEG_VALUE, IOUtils.toByteArray(trialSite1DoctorImage.getInputStream()));
        trialsite2Image = new TrialDirectImage(trialSite2DoctorImage.getFilename(), MediaType.IMAGE_JPEG_VALUE, IOUtils.toByteArray(trialSite2DoctorImage.getInputStream()));
        trialsite3Image = new TrialDirectImage(trialSite3DoctorImage.getFilename(), MediaType.IMAGE_JPEG_VALUE, IOUtils.toByteArray(trialSite3DoctorImage.getInputStream()));
        trialLogo = new TrialDirectImage(pfizerLogoImage.getFilename(), MediaType.IMAGE_PNG_VALUE, IOUtils.toByteArray(pfizerLogoImage.getInputStream()));

        createTrial();
    }

    /**
     * Trial Info
     */
    @Value("${pfizer.trial.title}") protected String trialTitle;
    @Value("${pfizer.trialinfo.summary}") protected String trialInfoSummary;
    @Value("${pfizer.trialinfo.fullDescription}") protected String trialInfoFullDescription;

    /**
     * Trial Site 1 Info
     */
    @Value("${pfizer.trialsite1.facility.name}") protected String trialSite1_facility_name;
    @Value("${pfizer.trialsite1.facility.description}") protected String trialSite1_facility_description;
    @Value("${pfizer.trialsite1.principal.investigator}") protected String trialSite1_principal_investigator;
    @Value("${pfizer.trialsite1.address1}") protected String trialSite1_address1;
    @Value("${pfizer.trialsite1.address2}") protected String trialSite1_address2;
    @Value("${pfizer.trialsite1.address3}") protected String trialSite1_address3;
    @Value("${pfizer.trialsite1.address4}") protected String trialSite1_address4;
    @Value("${pfizer.trialsite1.address5}") protected String trialSite1_address5;
    @Value("${pfizer.trialsite1.country}") protected String trialSite1_country;
    @Value("${pfizer.trialsite1.site.map}") protected String trialSite1_site_map;

    /**
     * Trial Site 2 Info
     */
    @Value("${pfizer.trialsite2.facility.name}") protected String trialSite2_facility_name;
    @Value("${pfizer.trialsite2.facility.description}") protected String trialSite2_facility_description;
    @Value("${pfizer.trialsite2.principal.investigator}") protected String trialSite2_principal_investigator;
    @Value("${pfizer.trialsite2.address1}") protected String trialSite2_address1;
    @Value("${pfizer.trialsite2.address2}") protected String trialSite2_address2;
    @Value("${pfizer.trialsite2.address3}") protected String trialSite2_address3;
    @Value("${pfizer.trialsite2.address4}") protected String trialSite2_address4;
    @Value("${pfizer.trialsite2.address5}") protected String trialSite2_address5;
    @Value("${pfizer.trialsite2.country}") protected String trialSite2_country;
    @Value("${pfizer.trialsite2.site.map}") protected String trialSite2_site_map;

    /**
     * Trial Site 3 Info
     */
    @Value("${pfizer.trialsite3.facility.name}") protected String trialSite3_facility_name;
    @Value("${pfizer.trialsite3.facility.description}") protected String trialSite3_facility_description;
    @Value("${pfizer.trialsite3.principal.investigator}") protected String trialSite3_principal_investigator;
    @Value("${pfizer.trialsite3.address1}") protected String trialSite3_address1;
    @Value("${pfizer.trialsite3.address2}") protected String trialSite3_address2;
    @Value("${pfizer.trialsite3.address3}") protected String trialSite3_address3;
    @Value("${pfizer.trialsite3.address4}") protected String trialSite3_address4;
    @Value("${pfizer.trialsite3.address5}") protected String trialSite3_address5;
    @Value("${pfizer.trialsite3.country}") protected String trialSite3_country;
    @Value("${pfizer.trialsite3.site.map}") protected String trialSite3_site_map;


    protected TrialDirectImage trialsite1Image = null;
    protected TrialDirectImage trialsite2Image = null;
    protected TrialDirectImage trialsite3Image = null;
    protected TrialDirectImage trialLogo = null;

    protected void createTrial() {

        Trial trial = new Trial(trialTitle, therapeuticAreaCancer);

        final TrialInfo trialInfo = new TrialInfo(trial, trialInfoSummary, trialInfoFullDescription);

        trialInfo.setTrialLogo(trialLogo);

        trial.setTrialInfos(new HashSet<TrialInfo>(){{
            add(trialInfo);
        }});

        final TrialSite trialSite1 = new TrialSite(trialInfo,
                trialSite1_facility_name,
                trialSite1_facility_description,
                trialSite1_address1,
                trialSite1_address2,
                trialSite1_address3,
                trialSite1_address4,
                trialSite1_address5,
                trialSite1_country,
                trialSite1_site_map,
                trialSite1_principal_investigator);

        final TrialSite trialSite2 = new TrialSite(trialInfo,
                trialSite2_facility_name,
                trialSite2_facility_description,
                trialSite2_address2,
                trialSite2_address2,
                trialSite2_address3,
                trialSite2_address4,
                trialSite2_address5,
                trialSite2_country,
                trialSite2_site_map,
                trialSite2_principal_investigator);

        final TrialSite trialSite3 = new TrialSite(trialInfo,
                trialSite3_facility_name,
                trialSite3_facility_description,
                trialSite3_address1,
                trialSite3_address2,
                trialSite3_address3,
                trialSite3_address4,
                trialSite3_address5,
                trialSite3_country,
                trialSite3_site_map,
                trialSite3_principal_investigator);

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
