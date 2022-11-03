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
        for (int i = 1; i <= 300; i++) {
            Question question = new Question();
            question.setSubject("제목 " + i);
            question.setContent("내용 " + i);
            question.setCreateDate(LocalDateTime.now());
            questionRepository.save(question);
        }
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
