/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tekenable.trialdirect.config;

import com.tekenable.repository.*;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author smoczyna
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.tekenable.repository"})
public class TestRepoConfig {

    @Bean
    public TherapeuticParentRepository therapeuticParentRepositoryMock() {
        return Mockito.mock(TherapeuticParentRepository.class);
    }

    @Bean
    public TherapeuticAreaRepository therapeuticAreaRepositoryMock() {
        return Mockito.mock(TherapeuticAreaRepository.class);
    }

    @Bean
    public QuestionRepository questionRepositoryMock() {
        return Mockito.mock(QuestionRepository.class);
    }
    
    @Bean
    public AnswerRepository answerRepositoryMock() {
        return Mockito.mock(AnswerRepository.class);
    }
    
    @Bean
    public QuestionnaireEntryRepository qaEntryRepositoryMock() {
        return Mockito.mock(QuestionnaireEntryRepository.class);
    }

    @Bean
    public TrialRepository trialRepositoryMock() {
        return Mockito.mock(TrialRepository.class);
    }

    @Bean
    public UserRepository userRepositoryMock() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public TrialInfoRepository trialInfoRepositoryMock() { return Mockito.mock(TrialInfoRepository.class); }

    @Bean
    public TrialSiteRepository trialSiteRepositoryMock() { return Mockito.mock(TrialSiteRepository.class); }

}
