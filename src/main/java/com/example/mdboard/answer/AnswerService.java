package com.example.mdboard.answer;

import com.example.mdboard.ex.DataNotFoundException;
import com.example.mdboard.question.Question;
import com.example.mdboard.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public Answer getAnswer(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isEmpty()) {
            throw new DataNotFoundException("answer not found");
        }
        return answer.get();
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}
