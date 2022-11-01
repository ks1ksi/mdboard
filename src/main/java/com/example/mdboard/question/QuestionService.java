package com.example.mdboard.question;

import com.example.mdboard.ex.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return  questionRepository.findAll();
    }

    public Question getQuestion(Long id) {
        Optional<Question> oq = questionRepository.findById(id);
        if (oq.isEmpty()) throw new DataNotFoundException("question not found");
        return oq.get();
    }

    public void create(String subject, String content) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);
    }
}
