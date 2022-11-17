package com.example.mdboard.answer;

import com.example.mdboard.question.Question;
import com.example.mdboard.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Transactional
    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setMyQuestion(question);
//        answer.setQuestion(question);
        answer.setContent(content);
        answer.setAuthor(author);
        answer.setCreateDate(LocalDateTime.now());
        answerRepository.save(answer);
        return answer;
    }

}
