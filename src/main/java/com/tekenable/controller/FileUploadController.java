package com.tekenable.controller;

import com.tekenable.controller.util.FileValidator;
import com.tekenable.controller.util.MultiFileValidator;
import com.tekenable.model.Trial;
import com.tekenable.model.dto.FileBucket;
import com.tekenable.model.dto.MultiFileBucket;
import com.tekenable.model.trial.TrialDirectImage;
import com.tekenable.model.trial.TrialInfo;
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
import java.util.List;

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
        FileBucket fileModel = new FileBucket();
        model.addAttribute("fileBucket", fileModel);
        return "singleFileUploader";
    }

    @RequestMapping(value = "/uploadTrialLogo", method = RequestMethod.POST)
    public @ResponseBody Integer singleFileUpload(@Valid FileBucket fileBucket,
                            BindingResult result, HttpServletResponse response) throws IOException {

        if (result.hasErrors()) {

            throw new RuntimeException(result.getAllErrors().toString());
        } else {

            Trial trial = trialRepository.findOne(fileBucket.getTrialId());

            TrialDirectImage trialLogo = new TrialDirectImage(
                    fileBucket.getFile().getOriginalFilename(),
                    fileBucket.getFile().getContentType(),
                    fileBucket.getFile().getBytes());

            TrialInfo trialInfo = new TrialInfo(trial, fileBucket.getDescription());

            trialInfo.setTrialLogo(trialLogo);

            trialInfo.setTrial(trial);

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