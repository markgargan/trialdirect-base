package com.tekenable.controller.util;

import com.tekenable.model.dto.TrialInfoBucket;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
 
@Component
public class FileValidator implements Validator {
     
    public boolean supports(Class<?> clazz) {
        return TrialInfoBucket.class.isAssignableFrom(clazz);
    }
 
    public void validate(Object obj, Errors errors) {
        TrialInfoBucket file = (TrialInfoBucket) obj;
         
        if(file.getFile()!=null){
            if (file.getFile().getSize() == 0) {
                errors.rejectValue("file", "missing.file");
            }
        }
    }
}