package com.tekenable.config.prime;

import com.tekenable.config.AuditConfigurator;
import com.tekenable.model.Trial;
import com.tekenable.model.trial.TrialDirectImage;
import com.tekenable.model.trial.TrialInfo;
import com.tekenable.model.trial.TrialSite;
import com.tekenable.repository.TrialInfoRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TrialPrimer extends CancerTrialPrimer implements ApplicationContextAware {

    private ApplicationContext ctx = null;

    @Autowired
    private TrialInfoRepository trialInfoRepository;

    @Autowired
    private AuditConfigurator auditConfigurator;

    private static final String pfizerGoogleMap =
            "https://www.google.ie/maps/place/Pfizer+Ireland+Pharmaceuticals/@53.324624,-6.4396619,17z/data=!3m1!4b1!4m2!3m1!1s0x486773bd46855f9f:0xc3be27201e4dfca6?hl=en";

    private static final String lundbeckGoogleMap =
            "https://www.google.ie/maps/place/Lundbeck+Ireland+Ltd/@53.291452,-6.422782,17z/data=!3m1!4b1!4m2!3m1!1s0x48670dd99e2792cd:0x73c87c24cd7c3d16?hl=en";

    private static final String doc1ImageLocation = "classpath:images/doc1.png";
    private static final String doc2ImageLocation = "classpath:images/doc2.jpg";
    private static final String doc3ImageLocation = "classpath:images/doc3.jpg";

    private static final String lundbeckLogoImage = "classpath:images/lundbeck_logo.gif";
    private static final String pfizerLogoImage = "classpath:images/pfizer.png";

    TrialDirectImage doc1Image = null;
    TrialDirectImage doc2Image = null;
    TrialDirectImage doc3Image = null;
    TrialDirectImage lundbeckLogo = null;
    TrialDirectImage pfizerLogo = null;

    public void initDB() {

        super.initDB();

        doc1Image = new TrialDirectImage("doc1.png", "png", loadImageFromClasspath(doc1ImageLocation));
        doc2Image = new TrialDirectImage("doc2.jpg", "jpg", loadImageFromClasspath(doc2ImageLocation));
        doc3Image = new TrialDirectImage("doc3.jpg", "jpg", loadImageFromClasspath(doc3ImageLocation));
        lundbeckLogo = new TrialDirectImage("lundbeck_logo.gif", "gif", loadImageFromClasspath(lundbeckLogoImage));
        pfizerLogo = new TrialDirectImage("pfizer.png", "png", loadImageFromClasspath(pfizerLogoImage));

        createPfizerTrial();

        createLundbeckTrial();

        auditConfigurator.addAuditStuff();
    }

    private void createPfizerTrial() {

        Trial pfizerTrial = new Trial("Pfizer Cancer Trial", therapeuticAreaCancer);

        final TrialInfo pfizerTrialInfo = new TrialInfo(pfizerTrial, "Pfizer Info1");

        pfizerTrialInfo.setTrialLogo(pfizerLogo);

        pfizerTrial.setTrialInfos(new HashSet<TrialInfo>(){{
            add(pfizerTrialInfo);
        }});

        final TrialSite trialSite1 = new TrialSite(pfizerTrialInfo,
                "Pfizer SiteDirector",
                "Pfizer SiteSummary",
                "Pfizer siteDescription",
                pfizerGoogleMap);

        trialSite1.setTrialSiteImage(doc1Image);

        pfizerTrialInfo.setTrialSites(new HashSet<TrialSite>() {{
                                          add(trialSite1);
        }});

        trialRepository.save(pfizerTrial);
    }

    private void createLundbeckTrial() {

        Trial lundbeckTrial = new Trial("Lundbeck Cancer Trial", therapeuticAreaCancer);

        final TrialInfo lundbeckTrialInfo = new TrialInfo(lundbeckTrial, "lundbeck Info1");

        lundbeckTrialInfo.setTrialLogo(lundbeckLogo);

        lundbeckTrial.setTrialInfos(new HashSet<TrialInfo>(){{
            add(lundbeckTrialInfo);
        }});

        final TrialSite trialSite1 = new TrialSite(lundbeckTrialInfo,
                "Lundbeck SiteDirector",
                "Lundbeck SiteSummary",
                "Lundbeck siteDescription",
                lundbeckGoogleMap);

        lundbeckTrialInfo.setTrialSites(new HashSet<TrialSite>() {{
            add(trialSite1);
        }});

        trialSite1.setTrialSiteImage(doc2Image);

        trialRepository.save(lundbeckTrial);
    }

    private byte[] loadImageFromClasspath(String imageLocation) {
        Resource picResource = this.ctx.getResource(imageLocation);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {

            InputStream bin = picResource.getInputStream();

            FileCopyUtils.copy(bin, bout);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bout.toByteArray();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

}
