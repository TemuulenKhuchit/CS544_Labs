package edu.miu.cs.cs544.temuulen.labs.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Cacheable(false)
@DiscriminatorValue("DistanceEducation")
public class DistanceEducation extends Course {

    @Column(name = "EXAM_PROFESSOR")
    private String examProfessor;

    @ElementCollection
    @CollectionTable(name = "DE_WEBINAR_SESSION_DATES", joinColumns = @JoinColumn(name = "COURSE_ID"))
    @Column(name = "SESSION_DATE")
    private List<LocalDate> webinarSessionDates;

    public DistanceEducation() {}

    public DistanceEducation(String title, LocalDate startDate, String professorName, String examProfessor, List<LocalDate> webinarSessionDates) {
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

    public List<LocalDate> getWebinarSessionDates() {
        return webinarSessionDates;
    }

    public void setWebinarSessionDates(List<LocalDate> webinarSessionDates) {
        this.webinarSessionDates = webinarSessionDates;
    }

    @Override
    public String toString() {
        return "DistanceEducation{" + "examProfessor='" + examProfessor + '\'' + ", webinarSessionDates=" + webinarSessionDates + "} " + super.toString();
    }
}
