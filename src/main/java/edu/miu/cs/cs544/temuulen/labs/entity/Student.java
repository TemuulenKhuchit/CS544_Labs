package edu.miu.cs.cs544.temuulen.labs.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Cacheable
@NamedQuery(
        name = "Student.CanGraduate",
        query = "SELECT s " +
                "FROM Student s " +
                "WHERE s.gpa >= 3.0 " +
                "AND SIZE(s.coursesAttended) >= 9 " +
                "AND s.courseAttending IS NULL"
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private float gpa;

    @ManyToOne
    @JoinColumn(name = "CURRENT_COURSE_ID")
    private Course courseAttending;

    @ManyToMany
    @JoinTable(
            name = "STUDENT_COURSES_ATTENDED",
            joinColumns = @JoinColumn(name = "STUDENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "COURSE_ID")
    )
    private List<Course> coursesAttended;

    @Version
    private int version;

    protected Student() {
    }

    public Student(String name, float gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public Course getCourseAttending() {
        return courseAttending;
    }

    public void setCourseAttending(Course courseAttending) {
        this.courseAttending = courseAttending;
    }

    public List<Course> getCoursesAttended() {
        return coursesAttended;
    }

    public void setCoursesAttended(List<Course> coursesAttended) {
        this.coursesAttended = coursesAttended;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gpa=" + gpa +
                ", courseAttending=" + courseAttending +
                ", coursesAttended=" + coursesAttended +
                ", version=" + version +
                '}';
    }
}
