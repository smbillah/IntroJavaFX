/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Student;

/**
 *
 * @author smbillah
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    
    @FXML
    private Button buttonCreateStudent;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonRead;

    @FXML
    private Button buttonReadByID;

    @FXML
    private Button buttonReadByName;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonReadByNameCGPA;

    @FXML
    void createStudent(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Name:");
        String name = input.next();
        
        System.out.println("Enter CPGA:");
        double cgpa = input.nextDouble();
        
        // create a student instance
        Student student = new Student();
        
        // set properties
        student.setId(id);
        student.setName(name);
        student.setCgpa(cgpa);
        
        // save this student to databse by calling Create operation        
        create(student);

    }

    @FXML
    void deleteStudent(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
         // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Student s = readById(id);
        System.out.println("we are deleting this student: "+ s.toString());
        delete(s);

    }
    

    @FXML
    void readByID(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Student s = readById(id);
        System.out.println(s.toString());

    }

    @FXML
    void readByName(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter Name:");
        String name = input.next();
        
        List<Student> s = readByName(name);
        System.out.println(s.toString());

    }

    @FXML
    void readByNameCGPA(ActionEvent event) {
        // name and cpga
        
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        
        System.out.println("Enter Name:");
        String name = input.next();
        
        System.out.println("Enter CPGA:");
        double cgpa = input.nextDouble();
        
        // create a student instance      
        List<Student> students =  readByNameAndCGPA(name, cgpa);

    }

    @FXML
    void readStudent(ActionEvent event) {

    }

    @FXML
    void updateStudent(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Name:");
        String name = input.next();
        
        System.out.println("Enter CPGA:");
        double cgpa = input.nextDouble();
        
        // create a student instance
        Student student = new Student();
        
        // set properties
        student.setId(id);
        student.setName(name);
        student.setCgpa(cgpa);
        
        // save this student to databse by calling Create operation        
        update(student);

    }
    

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");

        Query query = manager.createNamedQuery("Student.findAll");
        List<Student> data = query.getResultList();

        for (Student s : data) {
            System.out.println(s.getId() + " " + s.getName() + " " + s.getCgpa());
        }
    }

    // Database manager
    EntityManager manager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // loading students from database
        //database reference: "IntroJavaFXPU"
        manager = (EntityManager) Persistence.createEntityManagerFactory("IntroJavaFXPU").createEntityManager();

    }

    /*
    Implementing CRUD operations
    */
    
    // Create operation
    public void create(Student student) {
        try {
            // begin transaction
            manager.getTransaction().begin();
            
            // sanity check
            if (student.getId() != null) {
                
                // create student
                manager.persist(student);
                
                // end transaction
                manager.getTransaction().commit();
                
                System.out.println(student.toString() + " is created");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Read Operations
    public List<Student> readAll(){
        Query query = manager.createNamedQuery("Student.findAll");
        List<Student> students = query.getResultList();

        for (Student s : students) {
            System.out.println(s.getId() + " " + s.getName() + " " + s.getCgpa());
        }
        
        return students;
    }
    
    public Student readById(int id){
        Query query = manager.createNamedQuery("Student.findById");
        
        // setting query parameter
        query.setParameter("id", id);
        
        // execute query
        Student student = (Student) query.getSingleResult();
        if (student != null) {
            System.out.println(student.getId() + " " + student.getName() + " " + student.getCgpa());
        }
        
        return student;
    }        
    
    public List<Student> readByName(String name){
        Query query = manager.createNamedQuery("Student.findByName");
        
        // setting query parameter
        query.setParameter("name", name);
        
        // execute query
        List<Student> students =  query.getResultList();
        for (Student student: students) {
            System.out.println(student.getId() + " " + student.getName() + " " + student.getCgpa());
        }
        
        return students;
    }        
    
    public List<Student> readByNameAndCGPA(String name, double cpga){
        Query query = manager.createNamedQuery("Student.findByNameAndCgpa");
        
        // setting query parameter
        query.setParameter("cgpa", cpga);
        query.setParameter("name", name);
        
        
        // execute query
        List<Student> students =  query.getResultList();
        for (Student student: students) {
            System.out.println(student.getId() + " " + student.getName() + " " + student.getCgpa());
        }
        
        return students;
    }        
    
    
    // Update operation
    public void update(Student model) {
        try {

            Student existingStudent = manager.find(Student.class, model.getId());

            if (existingStudent != null) {
                // begin transaction
                manager.getTransaction().begin();
                
                // update all atttributes
                existingStudent.setName(model.getName());
                existingStudent.setCgpa(model.getCgpa());
                
                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Delete operation
    public void delete(Student student) {
        try {
            Student existingStudent = manager.find(Student.class, student.getId());

            // sanity check
            if (existingStudent != null) {
                
                // begin transaction
                manager.getTransaction().begin();
                
                //remove student
                manager.remove(existingStudent);
                
                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
