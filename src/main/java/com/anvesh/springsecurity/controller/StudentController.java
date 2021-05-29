package com.anvesh.springsecurity.controller;


import com.anvesh.springsecurity.domain.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("get")
public class StudentController {

    private final List<Student> studentList=new ArrayList<>(List.of(
            new Student("1","anvesh"),
            new Student("2","Ganesh")
    ));

    @GetMapping(path = "/{id}")
    public Student getStudent(@PathVariable String id){
        return studentList.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new RuntimeException("Not found student for id "+id));
    }

}
