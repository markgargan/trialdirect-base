/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tekenable.trialdirect.config;

import com.tekenable.repository.AnswerRepository;
import com.tekenable.repository.QuestionRepository;
import com.tekenable.repository.QuestionnaireEntryRepository;
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
    
}
