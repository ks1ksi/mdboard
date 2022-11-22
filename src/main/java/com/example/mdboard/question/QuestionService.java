package com.example.mdboard.question;

import com.example.mdboard.answer.Answer;
import com.example.mdboard.ex.DataNotFoundException;
import com.example.mdboard.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public Page<Question> getList(int page, String keyword) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> spec = search(keyword);
        return questionRepository.findAll(spec, pageable);
    }

    public Question create(String subject, String content, SiteUser author) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        question.setAuthor(author);
        questionRepository.save(question);
        return question;
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        questionRepository.save(question);
    }

    private Specification<Question> search(String keyword) {
        return new Specification<Question>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Question, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(
                        cb.like(q.get("subject"), "%" + keyword + "%"),
                        cb.like(q.get("content"), "%" + keyword + "%"),
                        cb.like(u1.get("username"), "%" + keyword + "%"),
                        cb.like(a.get("content"), "%" + keyword + "%"),
                        cb.like(u2.get("username"), "%" + keyword + "%"));
            }
        };
    }
}
