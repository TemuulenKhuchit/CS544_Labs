package edu.miu.cs.cs544.temuulen.labs.lab3.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("DistanceEducation")
public class DistanceEducation extends Course{

    @Column(name = "EXAM_PROFESSOR")
    private String examProfessor;

    @ElementCollection
    @CollectionTable(name = "DE_WEBINAR_SESSION_DATES", joinColumns = @JoinColumn(name = "COURSE_ID"))
    @Column(name = "SESSION_DATE")
    @Temporal(TemporalType.DATE)
    private List<Date> webinarSessionDates;

    public DistanceEducation() {}

    public DistanceEducation(String title, Date startDate, String professorName, String examProfessor, List<Date> webinarSessionDates) {
        super(title, startDate, professorName);
        this.examProfessor = examProfessor;
        this.webinarSessionDates = webinarSessionDates;
    }

    public String getExamProfessor() {
        return examProfessor;
    }

    public void setExamProfessor(String examProfessor) {
        this.examProfessor = examProfessor;
    }

    public List<Date> getWebinarSessionDates() {
        return webinarSessionDates;
    }

    public void setWebinarSessionDates(List<Date> webinarSessionDates) {
        this.webinarSessionDates = webinarSessionDates;
    }
}
