package com.demo.StudentManagement.services;

import com.demo.StudentManagement.entities.Student;
import com.demo.StudentManagement.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public Student findById(Long id){
        return studentRepository.findById(id);
    }
    public List<Student> findByUniversity(String university) {
        return studentRepository.findByUniversity(university);
    }
    public void addStudent(Long id, String name, Integer age, String department, String university) {
        studentRepository.addStudent(id,name,age,department,university);
    }
    public List<Student> findStudentByUniAndDpt(String uni, String dpt) {
        return studentRepository.findStudentByUniAndDpt(uni, dpt);
    }
    public void updateDptById(Long id, String dpt){
        studentRepository.updateStudentDptById(id,dpt);
    }
    public void deleteStudentById(Long id){
        studentRepository.deleteStudentById(id);
    }
}
