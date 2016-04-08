package com.tekenable.controller;

import com.tekenable.controller.util.FileValidator;
import com.tekenable.controller.util.MultiFileValidator;
import com.tekenable.model.Trial;
import com.tekenable.model.dto.FileBucket;
import com.tekenable.model.dto.TrialInfoBucket;
import com.tekenable.model.dto.MultiFileBucket;
import com.tekenable.model.dto.TrialSiteBucket;
import com.tekenable.model.trial.TrialDirectImage;
import com.tekenable.model.trial.TrialInfo;
import com.tekenable.model.trial.TrialSite;
import com.tekenable.repository.TrialInfoRepository;
import com.tekenable.repository.TrialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class FileUploadController {

    private static String UPLOAD_LOCATION="C:/mytemp/";

    @Autowired
    TrialRepository trialRepository;

    @Autowired
    TrialInfoRepository trialInfoRepository;

    @Autowired
    FileValidator fileValidator;

    @Autowired
    MultiFileValidator multiFileValidator;

    @InitBinder("trialInfo")
    protected void initBinderTrialInfo(WebDataBinder binder) {
//        binder.setValidator(fileValidator);
    }

    @InitBinder("fileBucket")
    protected void initBinderFileBucket(WebDataBinder binder) {
        binder.setValidator(fileValidator);
    }

    @InitBinder("multiFileBucket")
    protected void initBinderMultiFileBucket(WebDataBinder binder) {
        binder.setValidator(multiFileValidator);
    }

    @RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
    public String getHomePage(ModelMap model) {
        return "welcome";
    }

    @RequestMapping(value = "/singleUpload", method = RequestMethod.GET)
    public String getSingleUploadPage(ModelMap model) {
        TrialInfoBucket fileModel = new TrialInfoBucket();
        model.addAttribute("fileBucket", fileModel);
        return "singleFileUploader";
    }

    @RequestMapping(value = "/uploadTrialInfo", method = RequestMethod.POST)
    public @ResponseBody Integer uploadTrialInfo(@Valid TrialInfoBucket trialInfoBucket,
                            BindingResult result, HttpServletResponse response) throws IOException {

        if (result.hasErrors()) {

            throw new RuntimeException(result.getAllErrors().toString());
        } else {

            // Retrieve the appropriate Trial
            Trial trial = trialRepository.findOne(trialInfoBucket.getTrialId());

            // Create the TrialDirectImage that houses the Trial Icon
            TrialDirectImage trialLogo = new TrialDirectImage(
                    trialInfoBucket.getFile().getOriginalFilename(),
                    trialInfoBucket.getFile().getContentType(),
                    trialInfoBucket.getFile().getBytes());

            TrialInfo trialInfo = new TrialInfo(trial, trialInfoBucket.getDescription());

            trialInfo.setTrialLogo(trialLogo);

            trialInfo.setTrial(trial);

            Set<TrialSite> trialSites = new HashSet<TrialSite>();

            if (trialInfoBucket.getTrialSites() !=null) {
                for (TrialSiteBucket trialSiteBucket : trialInfoBucket.getTrialSites()) {
                    TrialSite trialSite = new TrialSite(trialInfo,
                            trialSiteBucket.getSiteDirector(),
                            trialSiteBucket.getSiteDirector(),
                            trialSiteBucket.getSiteSummary(),
                            trialSiteBucket.getSiteMap()
                    );

                    TrialDirectImage trialSiteImage = new TrialDirectImage(
                            trialSiteBucket.getTrialSiteFile().getOriginalFilename(),
                            trialSiteBucket.getTrialSiteFile().getContentType(),
                            trialSiteBucket.getTrialSiteFile().getBytes());

                    trialSite.setTrialSiteImage(trialSiteImage);

                    trialSites.add(trialSite);
                }
            }

            trialInfo.setTrialSites(trialSites);

            trialInfoRepository.save(trialInfo);

            return trialInfo.getId();
        }
    }

    @RequestMapping(value = "/multiUpload", method = RequestMethod.GET)
    public String getMultiUploadPage(ModelMap model) {
        MultiFileBucket filesModel = new MultiFileBucket();
        model.addAttribute("multiFileBucket", filesModel);
        return "multiFileUploader";
    }

    @RequestMapping(value = "/multiUpload", method = RequestMethod.POST)
    public String multiFileUpload(@Valid MultiFileBucket multiFileBucket,
                                  BindingResult result, ModelMap model) throws IOException {

        if (result.hasErrors()) {
            System.out.println("validation errors in multi upload");
            return "multiFileUploader";
        } else {
            System.out.println("Fetching files");
            List<String> fileNames = new ArrayList<String>();
            // Now do something with file...
            for (FileBucket bucket : multiFileBucket.getFiles()) {
                FileCopyUtils.copy(bucket.getFile().getBytes(), new File(UPLOAD_LOCATION + bucket.getFile().getOriginalFilename()));
                fileNames.add(bucket.getFile().getOriginalFilename());
            }

            model.addAttribute("fileNames", fileNames);
            return "multiSuccess";
        }
    }

}