package com.demo.StudentManagement.controller;

import com.demo.StudentManagement.entities.Student;
import com.demo.StudentManagement.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/addStudent")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){

        studentService.addStudent(student.getId(),student.getName(),student.getAge(),student.getDepartment(),student.getUniversity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/getStudent/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        try{
            Student student = studentService.findById(id);
            return new ResponseEntity<>(student,HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/getStudents/{uni}/{dpt}")
    public List<Student> getStudents(@PathVariable String uni,@PathVariable String dpt ){
        try{
            return studentService.findStudentByUniAndDpt(uni,dpt);
        }
        catch (Exception ex){
            return new ArrayList<>();
        }

    }
    @PutMapping("update/{id}/{dpt}")
    public ResponseEntity<String> updateStudent(@PathVariable Long id, @PathVariable String dpt) {
        studentService.updateDptById(id, dpt);
        return ResponseEntity.ok(String.format("Student updated with ID %d", id));
    }
    @DeleteMapping("/delStudent/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudentById(id);

            return ResponseEntity.ok(String.format("Student deleted with ID %d", id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
