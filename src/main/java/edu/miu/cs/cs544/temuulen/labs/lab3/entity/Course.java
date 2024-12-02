package edu.miu.cs.cs544.temuulen.labs.lab3.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "COURSE_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "PROFESSOR_NAME")
    private String professorName;

    protected Course(){}

    public Course(String title, LocalDate startDate, String professorName) {
        this.title = title;
        this.startDate = startDate;
        this.professorName = professorName;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }
}
