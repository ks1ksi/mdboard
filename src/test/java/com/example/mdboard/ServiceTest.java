package com.example.mdboard;

import com.example.mdboard.answer.Answer;
import com.example.mdboard.answer.AnswerService;
import com.example.mdboard.question.Question;
import com.example.mdboard.question.QuestionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @Test
    @Transactional
    void getAnswerListTest() {
        Question question = questionService.create("제목", "내용", null);
        Answer answer1 = answerService.create(question, "답변", null);
        Answer answer2 = answerService.create(question, "답변2", null);

        Assertions.assertEquals(2, question.getAnswerList().size());
        Assertions.assertSame(question, answer1.getQuestion());
        Assertions.assertSame(question, answer2.getQuestion());
    }

}
