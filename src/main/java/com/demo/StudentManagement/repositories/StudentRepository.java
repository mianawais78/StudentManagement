package com.demo.StudentManagement.repositories;

import com.demo.StudentManagement.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    //Method to findById using JPQL
    public Student findById(Long id){
        TypedQuery<Student> query = entityManager.createQuery(
                "SELECT s FROM Student s WHERE s.id = :id", Student.class
        );
        query.setParameter("id",id);
        return query.getSingleResult();
    }
    //Method to findAllStudent having same university
    public List<Student> findByUniversity(String university) {
        TypedQuery<Student> query = entityManager.createQuery(
                "SELECT s from Student s WHERE s.university = :university",Student.class);
        query.setParameter("university",university);
        return query.getResultList();
    }
    public List<Student> findStudentByUniAndDpt(String uni, String dpt) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query = cb.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);
        Predicate uniPredicate = cb.equal(root.get("university"), uni);
        Predicate dptPredicate = cb.equal(root.get("department"), dpt);
        Predicate finalPredicate = cb.and(uniPredicate, dptPredicate);
        query.where(finalPredicate);
        TypedQuery<Student> typedQuery = entityManager.createQuery(query);
        List<Student> students = typedQuery.getResultList();
        return students;
    }
    @Transactional
    public void deleteStudentById(Long id) {
        Query query = entityManager.createQuery("DELETE FROM Student s WHERE s.id = :id");
        query.setParameter("id", id);
        int deletedCount = query.executeUpdate();
        System.out.println("Number of records deleted: " + deletedCount);
    }
    @Transactional
    public void updateStudentDptById(Long id, String newDpt) {
        Query query = entityManager.createQuery("UPDATE Student s SET s.department = :newDpt WHERE s.id = :id");
        query.setParameter("newDpt", newDpt);
        query.setParameter("id", id);
        int updatedCount = query.executeUpdate();
        System.out.println("Number of records updated: " + updatedCount);
    }
//    Function to add Student using jpql.
//    public void addStudent(Student student) {
//        entityManager.persist(student);
//    }

    //Method to add Student using native query
    @Transactional
    public void addStudent(Long id, String name, Integer age, String department, String university){
        Query query  = entityManager.createNativeQuery(
                "INSERT INTO Student (id,name,age,department,university) VALUES (?,?,?,?,?)");
        query.setParameter(1,id);
        query.setParameter(2,name);
        query.setParameter(3,age);
        query.setParameter(4,department);
        query.setParameter(5,university);
        query.executeUpdate();

    }



}
