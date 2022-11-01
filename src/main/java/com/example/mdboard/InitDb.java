package com.example.mdboard;

import com.example.mdboard.answer.Answer;
import com.example.mdboard.answer.AnswerRepository;
import com.example.mdboard.question.Question;
import com.example.mdboard.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @PostConstruct
    public void init() {
        addQuestions();
        addAnswers();
    }

    public void addQuestions() {
        Question question1 = new Question();
        question1.setSubject("제목1");
        question1.setContent("내용1");
        question1.setCreateDate(LocalDateTime.now());
        questionRepository.save(question1);

        Question question2 = new Question();
        question2.setSubject("제목2");
        question2.setContent("내용2");
        question2.setCreateDate(LocalDateTime.now());
        questionRepository.save(question2);
    }

    public void addAnswers() {
        List<Question> questionList = questionRepository.findAll();
        for (Question question : questionList) {
            Answer answer = new Answer();
            answer.setContent("댓글");
            answer.setCreateDate(LocalDateTime.now());
            answer.setMyQuestion(question);
            answerRepository.save(answer);
        }
    }

}
