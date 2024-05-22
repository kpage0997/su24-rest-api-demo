package com.csc340.restapidemo;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/students")
public class RestApiController {

    //Returns list of all students
    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return StudentData.readStudents();
    }

    //Return specific student by id
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return StudentData.findStudentById(id);
    }

    //Creates a student
    @PostMapping("/create")
    public void createStudent(@RequestBody Student student) {
        List<Student> students = StudentData.readStudents();
        students.add(student);
        StudentData.writeStudents(students);
    }

    //Update student with given id
    @PutMapping("/update/{id}")
    public void updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        List<Student> students = StudentData.readStudents();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                students.set(i, updatedStudent);
                break;
            }
        }
        StudentData.writeStudents(students);
    }

    //Delete student with given id
    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable int id) {
        List<Student> students = StudentData.readStudents();
        students.removeIf(student -> student.getId() == id);
        StudentData.writeStudents(students);
    }

    // New endpoint to call the Jokes API; Returns random joke from jokes api
    @GetMapping("/joke")
    public String getJoke() {
        String url = "https://official-joke-api.appspot.com/jokes/random";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        // Print the response for debugging
        System.out.println("Joke response: " + response);

        // Parse and return relevant parts of the response
        return response;
    }
}
