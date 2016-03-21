package com.tekenable.model.projections;

import com.tekenable.model.Answer;
import com.tekenable.model.Question;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(name = "WithAnswers", types = { Question.class })
public interface WithAnswers {

    String getQuestionText();

    Set<Answer> getAnswers();
}