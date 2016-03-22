package com.tekenable.controller;

import com.tekenable.model.*;
import com.tekenable.repository.QuestionRepository;
import com.tekenable.repository.QuestionnaireRepository;
import com.tekenable.repository.TherapeuticAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by smoczyna on 21/03/16.
 */

/**
 * this class cannot be full spring controller at present
 * since it is used by the primer to populate the database
 * (controllers are instantiated later than the primer)
 */

public class QuestionnaireController {

    private Questionnaire questionnaire;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    public Questionnaire getQuestionnaire() {
        return this.questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public void createQuestionaire(String title) {
        this.questionnaire = new Questionnaire(title);
    }

    private boolean saveQuestion(Question question) {
        try {
            Question q = this.questionRepository.save(question);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean save() {
        try {
            this.questionnaireRepository.save(this.getQuestionnaire());
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public long getQuestionnaireEntryCount() {
        return this.questionnaireRepository.count();
    }

    /**
     *
     * @param entry
     * @return true if successful
     * this method assumes that the question attached to the entry
     * has already been saved
     *
     */
    public boolean addQuestionnaireEntry(QuestionnaireEntry entry) {
        entry.setQuestionnaire(this.getQuestionnaire());
        this.getQuestionnaire().getQuestionaireEntries().add(entry);
        return this.save();
    }

    public boolean addQuestionaireEntry(String title, String therapeuticArea, String question, String[] answers) {
        TherapeuticArea ts = new TherapeuticArea(therapeuticArea);
        Question q = new Question(question, ts);
        if (!this.saveQuestion(q)) return false;

        Set<Answer> ans = new LinkedHashSet();
        QuestionnaireEntry entry = new QuestionnaireEntry();
        for (String answer : answers)
            ans.add(new Answer(answer));

        entry.setTitle(title);
        entry.setQuestion(q);
        entry.setAnswers(ans);
        entry.setQuestionnaire(this.getQuestionnaire());
        Set<QuestionnaireEntry> qEntries = this.getQuestionnaire().getQuestionaireEntries();
        if (qEntries==null) {
            qEntries = new LinkedHashSet();
            this.questionnaire.setQuestionaireEntries(qEntries);
        }
        qEntries.add(entry);
        return this.save();
    }

    public boolean addQuestionaireEntry(String title, String therapeuticArea, String question, Set<Answer> answers) {
        TherapeuticArea ts = new TherapeuticArea(therapeuticArea);
        Question q = new Question(question, ts);
        if (!this.saveQuestion(q)) return false;

        Set<Answer> ans = new LinkedHashSet();
        QuestionnaireEntry entry = new QuestionnaireEntry();

        for (Answer answer : answers)
            ans.add(answer);

        entry.setTitle(title);
        entry.setQuestion(q);
        entry.setAnswers(ans);
        entry.setQuestionnaire(this.getQuestionnaire());
        Set<QuestionnaireEntry> qEntries = this.getQuestionnaire().getQuestionaireEntries();
        if (qEntries==null) {
            qEntries = new LinkedHashSet();
            this.questionnaire.setQuestionaireEntries(qEntries);
        }
        qEntries.add(entry);
        return this.save();
    }
}
