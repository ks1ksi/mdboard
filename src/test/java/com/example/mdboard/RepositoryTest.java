package com.example.mdboard;

import com.example.mdboard.answer.Answer;
import com.example.mdboard.answer.AnswerRepository;
import com.example.mdboard.question.Question;
import com.example.mdboard.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @Transactional
    void questionTest() {
        Question q1 = new Question();
        q1.setSubject("제목1");
        q1.setContent("내용1");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("제목2");
        q2.setContent("내용2");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);

        List<Question> questions = this.questionRepository.findAll();
        assertEquals(2, questions.size());

        Question question = questions.get(0);
        assertEquals(question.getId(), q1.getId());

        Optional<Question> qid = this.questionRepository.findById(q1.getId());
        assertTrue(qid.isPresent());
        assertEquals(q1.getId(), qid.get().getId());

        Question qs = this.questionRepository.findBySubject("제목1");
        assertEquals(q1.getId(), qs.getId());

        Question qsc = this.questionRepository.findBySubjectAndContent("제목2", "내용2");
        assertEquals(q2.getId(), qsc.getId());

        List<Question> qsl = this.questionRepository.findBySubjectLike("제목%");
        assertEquals(2, qsl.size());

        Optional<Question> qupdate = this.questionRepository.findById(q2.getId());
        assertTrue(qupdate.isPresent());
        qupdate.get().setSubject("제목3");
        assertEquals("제목3", qupdate.get().getSubject());

        Question q3 = this.questionRepository.findBySubject("제목3");
        assertEquals(q2.getId(), q3.getId());

        this.questionRepository.delete(q3);
        assertEquals(1, this.questionRepository.count());
    }

    @Test
    @Transactional
    void answerTest() {
        Question q = new Question();
        q.setSubject("제목");
        q.setContent("내용");
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);

        Answer a = new Answer();
//        a.setQuestion(q);
        a.setMyQuestion(q);
        a.setContent("답변");
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);

        Optional<Answer> oa = this.answerRepository.findById(a.getId());
        assertTrue(oa.isPresent());
        assertEquals(a.getId(), oa.get().getId());
        assertEquals(a.getQuestion().getId(), q.getId());

        List<Answer> answerList = q.getAnswerList();
        assertEquals(1, answerList.size());
        assertEquals("답변", answerList.get(0).getContent());

    }

}
