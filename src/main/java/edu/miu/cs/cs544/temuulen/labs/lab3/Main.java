package edu.miu.cs.cs544.temuulen.labs.lab3;

import edu.miu.cs.cs544.temuulen.labs.lab3.entity.Course;
import edu.miu.cs.cs544.temuulen.labs.lab3.entity.DistanceEducation;
import edu.miu.cs.cs544.temuulen.labs.lab3.entity.OnCampus;
import edu.miu.cs.cs544.temuulen.labs.lab3.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Creating courses
            OnCampus course1 = new OnCampus("Enterprise Architecture", new Date(), "Najeeb Najeeb", "101", 40);
            OnCampus course2 = new OnCampus("Algorithms", new Date(), "Premchand Nair", "102", 25);
            DistanceEducation course3 = new DistanceEducation("Cloud Computing", new Date(), "Unubold Tumurbaatar", "Najeeb", Arrays.asList(new Date(), new Date()));
            DistanceEducation course4 = new DistanceEducation("MWA", new Date(), "Asaad Saad", "Sarah Sarah", Arrays.asList(new Date(), new Date()));
            OnCampus course5 = new OnCampus("FPP", new Date(), "Clark Clark", "Room 103", 35);
            OnCampus course6 = new OnCampus("MPP", new Date(), "Davis Davis", "Room 104", 30);
            DistanceEducation course7 = new DistanceEducation("STC-2", new Date(), "Evans Evans", "Najeeb", Arrays.asList(new Date(), new Date()));
            DistanceEducation course8 = new DistanceEducation("WAP", new Date(), "Fox Fox", "Najeeb", Arrays.asList(new Date(), new Date()));
            OnCampus course9 = new OnCampus("STC", new Date(), "Harris Harris", "Room 105", 20);
            OnCampus course10 = new OnCampus("Career Strategy", new Date(), "Bat Bat", "Room 106", 40);

            Arrays.asList(course1, course2, course3, course4, course5, course6, course7, course8, course9, course10)
                    .forEach(em::persist);

            // Creating students
            Student student1 = new Student("Temuulen", 3.8f);
            student1.setCourseAttending(null);
            student1.setCoursesAttended(Arrays.asList(course2, course3, course4, course5, course6, course7, course8, course9, course10));

            Student student2 = new Student("Odko", 2.6f);
            student2.setCourseAttending(course3);
            student2.setCoursesAttended(Arrays.asList(course1, course2));

            Student student3 = new Student("Luka", 2.2f);
            student3.setCourseAttending(null);
            student3.setCoursesAttended(Arrays.asList(course1, course2, course3, course4));

            Student student4 = new Student("Sukhbat", 3.2f);
            student4.setCourseAttending(null);
            student4.setCoursesAttended(Arrays.asList(course1, course2, course3, course4, course5, course6, course7, course8, course9));

            Student student5 = new Student("Nomin", 3.6f);
            student5.setCourseAttending(course1);
            student5.setCoursesAttended(Arrays.asList(course1, course2, course3, course4, course5, course6, course7, course8, course9));

            Arrays.asList(student1, student2, student3, student4, student5).forEach(em::persist);

            em.getTransaction().commit();

            // JPQL Query
            String jpql = "SELECT s FROM Student s JOIN TREAT(s.courseAttending AS OnCampus) c WHERE s.gpa > 3.5 AND c.capacity > 30";
            TypedQuery<Student> jpqlQuery = em.createQuery(jpql, Student.class);
            List<Student> jpqlResult = jpqlQuery.getResultList();

            System.out.println("JPQL Query Results:");
            for (Student s : jpqlResult) {
                System.out.println("Student: " + s.getName() + ", GPA: " + s.getGpa());
            }

            // Named Query
            TypedQuery<Student> namedQuery = em.createNamedQuery("Student.CanGraduate", Student.class);
            List<Student> namedQueryResult = namedQuery.getResultList();

            System.out.println("\nNamed Query Results (CanGraduate):");
            for (Student s : namedQueryResult) {
                System.out.println("Student: " + s.getName() + ", GPA: " + s.getGpa() + ", Courses Attended: " + s.getCoursesAttended().size());
            }

            // CriteriaAPI Query
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Student> criteriaQuery = cb.createQuery(Student.class);
            Root<Student> studentRoot = criteriaQuery.from(Student.class);
            Join<Student, Course> courseJoin = studentRoot.join("courseAttending");

            Join<Student, DistanceEducation> deCourseJoin = cb.treat(courseJoin, DistanceEducation.class);

            Predicate gpaLessThan = cb.lessThan(studentRoot.get("gpa"), 3.0f);
            Predicate examProfessorIsNajeeb = cb.equal(deCourseJoin.get("examProfessor"), "Najeeb");

            criteriaQuery.select(studentRoot).where(cb.and(gpaLessThan, examProfessorIsNajeeb));

            List<Student> criteriaResult = em.createQuery(criteriaQuery).getResultList();

            System.out.println("\nCriteria API Query Results:");
            for (Student s : criteriaResult) {
                System.out.println("Student: " + s.getName() + ", GPA: " + s.getGpa());
            }

        } finally {
            em.close();
            emf.close();
        }
    }
}