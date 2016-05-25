package com.tekenable.controller;

import com.tekenable.repository.TrialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(name= "/")
public class AppController {


  @RequestMapping(method = RequestMethod.GET)
  public String viewApplication() {
    return "index";
  }

  @Autowired
  TrialRepository trialRepository;

  @RequestMapping(value = "/availabletrialids/{userId}/therapeuticarea/{usersTherapeuticAreaId}", method = RequestMethod.GET)
  public @ResponseBody List<Integer> availableTrialsIds(@PathVariable("userId") Integer userId,
                                                        @PathVariable("usersTherapeuticAreaId") Integer usersTherapeuticAreaId) {
    return trialRepository.getAvailableTrialIds(userId, usersTherapeuticAreaId);
  }

}
