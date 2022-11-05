package com.example.mdboard;

import com.example.mdboard.answer.Answer;
import com.example.mdboard.answer.AnswerRepository;
import com.example.mdboard.question.Question;
import com.example.mdboard.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.addQuestions();
        initService.addAnswers();
    }

    @RequiredArgsConstructor
    @Component
    static class InitService {
        private final QuestionRepository questionRepository;
        private final AnswerRepository answerRepository;

        @Transactional
        public void addQuestions() {
            for (int i = 1; i <= 300; i++) {
                Question question = new Question();
                question.setSubject("제목 " + i);
                question.setContent("내용 " + i);
                question.setCreateDate(LocalDateTime.now());
                questionRepository.save(question);
            }
        }

        @Transactional
        public void addAnswers() {
            for (Question question : questionRepository.findAll()) {
                Answer answer = new Answer();
                answer.setContent("답변");
                answer.setCreateDate(LocalDateTime.now());
                answer.setMyQuestion(question);
                answerRepository.save(answer);
            }
        }
    }


}
