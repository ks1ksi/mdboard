package com.example.mdboard.answer;

import com.example.mdboard.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setMyQuestion(question);
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

}
