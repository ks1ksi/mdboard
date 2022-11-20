package com.example.mdboard.answer;

import com.example.mdboard.question.Question;
import com.example.mdboard.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    public void setMyQuestion(Question question) {
        this.question = question;
        question.getAnswerList().add(this);
    }
}
