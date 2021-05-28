package com.anvesh.springsecurity.controller;

import com.anvesh.springsecurity.domain.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students/")
public class StudentManagementController {

    private final List<Student> studentList=new ArrayList<>(List.of(
            new Student("1","anvesh"),
            new Student("2","Ganesh"),
            new Student("3","Vishnu"),
            new Student("4","Chandu")
    ));

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudent(){
        System.out.println("GEt all Students...\n");
        return studentList;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('Student:read')")
    public  Student getStudent(@PathVariable String id){
        return studentList.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Student:write')")
    public void newStudent(@RequestBody Student student){
        System.out.println("new student\n");
        System.out.println("student object created");
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('Student:write')")
    public void deleteStudent(@PathVariable String id){
        System.out.println("deleting new student " + id + "\n");
        System.out.println("student object deleted");

    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('Student:write')")
    public void updateStudent(@PathVariable String id,@RequestBody Student student ){
        System.out.println("Udating new Student " + id);
        System.out.println("Student Object updated");
    }
}
