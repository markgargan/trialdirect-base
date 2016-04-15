package com.tekenable.servlet;

import com.tekenable.model.Trial;
import com.tekenable.model.common.TrialDirectAddress;
import com.tekenable.model.trial.TrialDirectImage;
import com.tekenable.model.trial.TrialFullDescription;
import com.tekenable.model.trial.TrialInfo;
import com.tekenable.model.trial.TrialSite;
import com.tekenable.repository.TrialInfoRepository;
import com.tekenable.repository.TrialRepository;
import com.tekenable.repository.TrialSiteRepository;
import com.tekenable.util.AutowireHelper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by mark on 12/04/2016.
 */
public class TrialLogoUploadServlet extends HttpServlet {

    private final static String DESCRIPTION = "description";
    private final static String TRIAL_ID = "trialId";
    private static final String TRIAL_SITE_FILE = "trialSiteFile";
    private static final String TRIAL_FULL_DESCRIPTION = "fullDescription";

    private static final String TRIAL_INFO_LOGO = "trialInfoLogo";
    private static final String SITE_DIRECTOR_BIO= "siteDescription";
    private static final String SITE_DIRECTOR   = "siteDirector";
    private static final String SITE_MAP        = "siteMap";

    private static final String SITE_ADDRESS1        = "siteAddress1";
    private static final String SITE_ADDRESS2        = "siteAddress2";
    private static final String SITE_ADDRESS3        = "siteAddress3";
    private static final String SITE_ADDRESS4        = "siteAddress4";
    private static final String SITE_ADDRESS5        = "siteAddress5";
    private static final String SITE_COUNTRY        = "siteCountry";

    @Autowired
    TrialRepository trialRepository;

    @Autowired
    TrialInfoRepository trialInfoRepository;

    @Autowired
    TrialSiteRepository trialSiteRepository;

    private WebApplicationContext springContext;

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        AutowireHelper.autowire(this, this.trialRepository);
        AutowireHelper.autowire(this, this.trialInfoRepository);
        AutowireHelper.autowire(this, this.trialSiteRepository);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TrialInfo trialInfo = new TrialInfo();
        Map<Integer, TrialSite> trialSites = new HashMap<Integer, TrialSite>();

        Trial trial = prepareEntities(request, trialInfo, trialSites);

        persistTrialInfo(trial, trialInfo, trialSites);

    }

    private void persistTrialInfo(Trial trial, TrialInfo trialInfo, Map<Integer, TrialSite> trialSiteMap) {

        // Create the TrialDirectImage that houses the Trial Icon
        trialInfo.setTrial(trial);

        // Associate the TrialInfo with each site
        for(TrialSite trialSite : trialSiteMap.values()) {
            trialSite.setTrialInfo(trialInfo);
        }

        final Collection<TrialSite> trialSites = trialSiteMap.values();

        Set<TrialSite> trialSiteSet = new HashSet<TrialSite>(){{
                addAll(trialSites);
        }};

        trialInfo.setTrialSites(trialSiteSet);

        trialInfoRepository.save(trialInfo);

    }

    private Trial prepareEntities(HttpServletRequest request, TrialInfo trialInfo, Map<Integer, TrialSite> trialSites) throws IOException, ServletException {

        Trial trial = null;

        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();

                    if (!fieldName.startsWith("trialSites[")) {
                        // It's a trialInfo field
                        if (fieldName.equals(DESCRIPTION)) {
                            trialInfo.setSummary(fieldValue);
                        } else if (fieldName.equals(TRIAL_ID)) {
                            trial = trialRepository.findOne(Integer.valueOf(fieldValue));
                        } else if (fieldName.equals(TRIAL_FULL_DESCRIPTION)) {
                            trialInfo.setTrialFullDescription(new TrialFullDescription(fieldValue));

                        }
                    } else {
                        handleTrialSiteField(fieldName, fieldValue, trialSites);
                    }
                } else {
                    String fieldName = item.getFieldName();
                    String fileName = FilenameUtils.getName(item.getName());
                    String contentType = item.getContentType();
                    byte[] contents = FileCopyUtils.copyToByteArray(item.getInputStream());

                    if (fieldName.equals(TRIAL_INFO_LOGO)) {
                        TrialDirectImage trialLogo = new TrialDirectImage(fileName, contentType, contents);
                        trialInfo.setTrialLogo(trialLogo);
                    } else if (fieldName.contains(TRIAL_SITE_FILE)) {
                        handleTrialSiteImage(fieldName, fileName, contentType, contents, trialSites);
                    }

                }
            }
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        } catch (NoSuchMethodException e) {
            throw new ServletException(e.getMessage(),e);
        }

        return trial;
    }

    private void handleTrialSiteImage(String fieldName, String fileName, String contentType, byte[] contents, Map<Integer, TrialSite> trialSites) {

        TrialSite trialSite = getTrialSite(fieldName, trialSites);
        trialSite.setTrialSiteImage(new TrialDirectImage(fileName, contentType, contents));

    }


    private void handleTrialSiteField(String fieldName, String fieldValue, Map<Integer, TrialSite> trialSites) throws NoSuchMethodException {

        TrialSite trialSite = getTrialSite(fieldName, trialSites);

        TrialDirectAddress trialDirectAddress = trialSite.getTrialDirectAddress()==null ? new TrialDirectAddress() : trialSite.getTrialDirectAddress();

        if (fieldName.contains(SITE_DIRECTOR_BIO)) {
            trialSite.setSiteDirectorBio(fieldValue);
        } else if (fieldName.contains(SITE_DIRECTOR)) {
            trialSite.setSiteDirector(fieldValue);
        } else if (fieldName.contains(SITE_ADDRESS1)) {
            trialDirectAddress.setAddress1(fieldValue);
        } else if (fieldName.contains(SITE_ADDRESS2)) {
            trialDirectAddress.setAddress2(fieldValue);
        } else if (fieldName.contains(SITE_ADDRESS3)) {
            trialDirectAddress.setAddress3(fieldValue);
        } else if (fieldName.contains(SITE_ADDRESS4)) {
            trialDirectAddress.setAddress4(fieldValue);
        } else if (fieldName.contains(SITE_ADDRESS5)) {
            trialDirectAddress.setAddress5(fieldValue);
        } else if (fieldName.contains(SITE_COUNTRY)) {
            trialDirectAddress.setCountry(fieldValue);
        }

        else if (fieldName.contains(SITE_MAP)) {
            trialSite.setSiteMap(fieldValue);
        }
    }

    private TrialSite getTrialSite(String fieldName, Map<Integer, TrialSite> trialSites) {
        // Extract the index of the trialSite
        Integer trialSiteIndex = Integer.valueOf( fieldName.substring(fieldName.indexOf('[')+1, fieldName.indexOf(']')));

        TrialSite trialSite = null;

        if (trialSites.get(trialSiteIndex) == null) {
            trialSite = new TrialSite();
            trialSites.put(trialSiteIndex, trialSite);
        } else {
            trialSite = trialSites.get(trialSiteIndex);
        }

        return trialSite;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
