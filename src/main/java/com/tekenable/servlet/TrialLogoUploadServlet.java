package com.tekenable.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final static String TRIAL_ID = "trialId";
    private final static String TRIAL_SITE_ID_PATTERN = "trialSites\\[.*\\]\\.id";
    private final static String TRIAL_INFO_ID = "trialInfoId";
    private final static String TRIAL_INFO_SUMMARY = "summary";
    private static final String TRIAL_INFO_FULL_DESCRIPTION = "trialFullDescription";
    private static final String TRIAL_SITE_FILE = "trialSiteFile";

    private static final String TRIAL_INFO_LOGO = "trialInfoLogo";
    private static final String FACILITY_DESCRIPTION= "facilityDescription";
    private static final String FACILITY_NAME = "facilityName";
    private static final String PRINCIPAL_INVESTIGATOR = "principalInvestigator";

    private static final String SITE_ADDRESS1        = "siteAddress1";
    private static final String SITE_ADDRESS2        = "siteAddress2";
    private static final String SITE_ADDRESS3        = "siteAddress3";
    private static final String SITE_ADDRESS4        = "siteAddress4";
    private static final String SITE_ADDRESS5        = "siteAddress5";
    private static final String SITE_COUNTRY        = "siteCountry";
    private static final String SITE_SORT_ORDER        = "sortOrder";

    private static final String SITE_MAP        = "siteMap";

    @Autowired
    TrialRepository trialRepository;

    @Autowired
    TrialInfoRepository trialInfoRepository;

    @Autowired
    TrialSiteRepository trialSiteRepository;

    private WebApplicationContext springContext;

    private TrialInfo trialInfo = new TrialInfo();
    private Map<Integer, TrialSite> trialSites = new HashMap<Integer, TrialSite>();


    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        AutowireHelper.autowire(this, this.trialRepository);
        AutowireHelper.autowire(this, this.trialInfoRepository);
        AutowireHelper.autowire(this, this.trialSiteRepository);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        resetTrialInfoAndSites();

        Trial trial = prepareEntities(request);

        persistTrialInfo(trial);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        resetTrialInfoAndSites();
        Trial trial = prepareEntities(request);

        persistTrialInfo(trial);

        // Don't return the trialLogo image in the response
        trialInfo.setTrialLogo(null);
        trialInfo.setTrial(null);

        for (TrialSite trialSite : trialSites.values()) {
            trialSite.setTrialSiteImage(null);
            trialSite.setTrialInfo(null);
        }

        response.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();

        String trialIdJson = mapper.writeValueAsString(trial.getId());

        mapper.writeValue(response.getOutputStream(), trialIdJson);
    }

    private void persistTrialInfo(Trial trial) {

        // Create the TrialDirectImage that houses the Trial Icon
        trialInfo.setTrial(trial);

        // Associate the TrialInfo with each site
        for(TrialSite trialSite : trialSites.values()) {
            trialSite.setTrialInfo(trialInfo);
        }

        final Collection<TrialSite> _trialSites = trialSites.values();

        Set<TrialSite> trialSiteSet = new HashSet<TrialSite>(){{
                addAll(_trialSites);
        }};

        trialInfo.setTrialSites(trialSiteSet);

        trialInfoRepository.save(trialInfo);

    }

    private Trial prepareEntities(HttpServletRequest request) throws IOException, ServletException {

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
                        if (fieldName.equals(TRIAL_ID)) {
                            trial = trialRepository.findOne(Integer.valueOf(fieldValue));
                        } else if (fieldName.equals(TRIAL_INFO_SUMMARY)) {
                            trialInfo.setSummary(fieldValue);
                        } else if (fieldName.equals(TRIAL_INFO_FULL_DESCRIPTION)) {
                            trialInfo.setTrialFullDescription(new TrialFullDescription(fieldValue));
                        } else if (fieldName.equals(TRIAL_INFO_ID)) {
                            TrialInfo trialInfoForMerge = trialInfoRepository.findOne(Integer.valueOf(fieldValue));
                            trialInfo = mergeTrialInfo(trialInfo, trialInfoForMerge);
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

    private TrialInfo mergeTrialInfo(TrialInfo dto, TrialInfo existingTrialInfo) {

        if (dto.getTrialLogo() != null) {
            existingTrialInfo.setTrialLogo(dto.getTrialLogo());
        }

        if (dto.getSummary()!=null) {
            existingTrialInfo.setSummary(dto.getSummary());
        }

        if (dto.getTrialFullDescription()!=null && dto.getTrialFullDescription().getFullDescription()!= null){
            if ( existingTrialInfo.getTrialFullDescription() == null) {
                existingTrialInfo.setTrialFullDescription(new TrialFullDescription(dto.getTrialFullDescription().getFullDescription()));
            } else {
                existingTrialInfo.getTrialFullDescription().setFullDescription(dto.getTrialFullDescription().getFullDescription());
            }
        }

        return existingTrialInfo;
    }

    private TrialSite mergeTrialSite(TrialSite dto, TrialSite existingTrialSite) {

        if (dto.getTrialSiteImage() != null) {
            existingTrialSite.setTrialSiteImage(dto.getTrialSiteImage());
        }

        if (dto.getFacilityDescription()!=null) {
            existingTrialSite.setFacilityDescription(dto.getFacilityDescription());
        }

        if (dto.getPrincipalInvestigator()!= null) {
            existingTrialSite.setPrincipalInvestigator(dto.getPrincipalInvestigator());
        }

        if (dto.getFacilityName()!= null) {
            existingTrialSite.setFacilityName(dto.getFacilityName());
        }

        if (dto.getSiteMap()!= null) {
            existingTrialSite.setSiteMap(dto.getSiteMap());
        }

        if (dto.getTrialDirectAddress()!=null) {
            existingTrialSite.setTrialDirectAddress(dto.getTrialDirectAddress());
        }

        if (dto.getSortOrder()!=null) {
            existingTrialSite.setSortOrder(dto.getSortOrder());
        }

        return existingTrialSite;
    }

    private void handleTrialSiteImage(String fieldName, String fileName, String contentType, byte[] contents, Map<Integer, TrialSite> trialSites) {

        TrialSite trialSite = getTrialSite(fieldName, trialSites);
        trialSite.setTrialSiteImage(new TrialDirectImage(fileName, contentType, contents));

    }


    private void handleTrialSiteField(String fieldName, String fieldValue, Map<Integer, TrialSite> trialSites) throws NoSuchMethodException {

        TrialSite trialSite = getTrialSite(fieldName, trialSites);

        TrialDirectAddress trialDirectAddress = trialSite.getTrialDirectAddress() == null ? new TrialDirectAddress() : trialSite.getTrialDirectAddress();

        trialSite.setTrialDirectAddress(trialDirectAddress);

        if (fieldName.matches(TRIAL_SITE_ID_PATTERN)) {
            TrialSite existingTrialSite  = trialSiteRepository.findOne(Integer.valueOf(fieldValue));

            // Replace the trialSite in the map with the existing trialSite which
            // will then be merged.
            if (existingTrialSite != null) {

                for (Integer key : trialSites.keySet()) {
                    if (trialSites.get(key).equals(trialSite)){
                        trialSites.put(key, existingTrialSite);
                    }
                }
            }

            mergeTrialSite(trialSite, existingTrialSite);
        } else if (fieldName.contains(FACILITY_DESCRIPTION)) {
            trialSite.setFacilityDescription(fieldValue);
        } else if (fieldName.contains(FACILITY_NAME)) {
            trialSite.setFacilityName(fieldValue);
        }else if (fieldName.contains(PRINCIPAL_INVESTIGATOR)) {
            trialSite.setPrincipalInvestigator(fieldValue);
        }else if (fieldName.contains(SITE_ADDRESS1)) {
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
        } else if (fieldName.contains(SITE_SORT_ORDER)) {
            trialSite.setSortOrder(Integer.valueOf(fieldValue));
        } else if (fieldName.contains(SITE_MAP)) {
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

    private void resetTrialInfoAndSites(){
        trialInfo = new TrialInfo();

        trialSites = new HashMap<Integer, TrialSite>();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
