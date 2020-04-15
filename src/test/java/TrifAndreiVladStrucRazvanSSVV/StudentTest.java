package TrifAndreiVladStrucRazvanSSVV;

import TrifAndreiVladStrucRazvanSSVV.domain.Nota;
import TrifAndreiVladStrucRazvanSSVV.domain.Student;
import TrifAndreiVladStrucRazvanSSVV.domain.Tema;
import TrifAndreiVladStrucRazvanSSVV.repository.*;
import TrifAndreiVladStrucRazvanSSVV.service.Service;
import TrifAndreiVladStrucRazvanSSVV.validation.NotaValidator;
import TrifAndreiVladStrucRazvanSSVV.validation.StudentValidator;
import TrifAndreiVladStrucRazvanSSVV.validation.TemaValidator;
import TrifAndreiVladStrucRazvanSSVV.validation.ValidationException;
import org.junit.Test;


import java.time.LocalDate;

import static org.junit.Assert.*;

public class StudentTest {

    private StudentValidator studentValidator;
    private TemaValidator temaValidator;
    private String filenameStudent;
    private String filenameTema;
    private String filenameNota;
    private StudentXMLRepo studentXMLRepository;
    private TemaXMLRepo temaXMLRepository;
    private NotaValidator notaValidator;
    private NotaXMLRepo notaXMLRepository;
    private Service service;

    private void Setup(){
        this.studentValidator = new StudentValidator();
        this.temaValidator = new TemaValidator();
        this.filenameStudent = "src/test/java/TrifAndreiVladStrucRazvanSSVV/fisiere/Studenti.xml";
        this.filenameTema = "src/test/java/TrifAndreiVladStrucRazvanSSVV/fisiere/Teme.xml";
        this.filenameNota = "src/test/java/TrifAndreiVladStrucRazvanSSVV/fisiere/Note.xml";

        this.studentXMLRepository = new StudentXMLRepo(filenameStudent);
        this.temaXMLRepository = new TemaXMLRepo(filenameTema);
        this.notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        this.notaXMLRepository = new NotaXMLRepo(filenameNota);
        this.service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    // Add Student
    @Test
    public void testFindStudent(){
        this.Setup();

        Student retrievedStudent = this.service.findStudent("1");

        assertNotNull(retrievedStudent);
        assertEquals("TestStudent", retrievedStudent.getNume());
    }

    @Test
    public void testAddStudent(){
        this.Setup();

        Student addedStudent = new Student("100", "Trif", 937, "taie2433@scs.ubbcluj.ro");
        this.service.addStudent(addedStudent);
        Student retrievedStudent = this.service.findStudent(addedStudent.getID());

        assertNotNull(retrievedStudent);
        assertEquals(addedStudent.getNume(), retrievedStudent.getNume());
        assertEquals(addedStudent.getEmail(), retrievedStudent.getEmail());
    }

    @Test
    public void testAddStudentEmptyId() {
        this.Setup();

        Student new_student = new Student("","Struc",937,"etc@yahoo.com");


        try{
            service.addStudent(new_student);
            assert(false);
        }
        catch(Exception e) {
            assert(true);
        }
    }

    @Test
    public void testAddStudentNullId() {
        this.Setup();

        Student new_student = new Student(null,"Struc",937,"etc@yahoo.com");


        try{
            service.addStudent(new_student);
            assert(false);
        }
        catch(Exception e) {
            assert(true);
        }
    }

    @Test
    public void testAddStudentUnderZeroGroup() {
        this.Setup();

        Student new_student = new Student("2133","Struc",-4,"etc@yahoo.com");


        try{
            service.addStudent(new_student);
            assert(false);
        }
        catch(Exception e) {
            assert(true);
        }
    }

    @Test
    public void testAddStudentEmptyEmail() {
        this.Setup();

        Student new_student = new Student("2133","Struc",937,"");


        try{
            service.addStudent(new_student);
            assert(false);
        }
        catch(Exception e) {
            assert(true);
        }
    }

    @Test
    public void testAddStudentNullEmail() {
        this.Setup();

        Student new_student = new Student("2133","Struc",937,null);
        try{
            service.addStudent(new_student);
            assert(false);
        }
        catch(Exception e) {
            assert(true);
        }
    }

    // Add Assignment
    @Test
    public void testAddTemaThrowException(){
        this.Setup();

        Tema toBeAdded = new Tema("", "testDescription", 5, 4);


        try{
            this.service.addTema(toBeAdded);
        } catch (ValidationException exception){
            assertEquals(exception.getMessage(), "Numar tema invalid!");
        }
    }

    @Test
    public void testAddTemaAdditionSucceeded(){
        this.Setup();

        Tema toBeAdded = new Tema("100", "testDescription", 5, 4);
        this.service.addTema(toBeAdded);

        Tema retrieved = this.service.findTema("100");

        assertEquals(retrieved.getDescriere(), toBeAdded.getDescriere());
    }

    @Test
    public void addAssignementFailDescription(){
        this.Setup();
        Tema new_tema = new Tema("1111","", 99, 98);

        try{
            service.addTema(new_tema);
        }
        catch(Exception e) {
            assertEquals(e.getMessage(),"Descriere invalida!");
        }

    }
    @Test
    public void addAssignementFailDeadline(){
        this.Setup();
        Tema new_tema = new Tema("1111","Test", 0, 98);

        try{
            service.addTema(new_tema);
        }
        catch(Exception e) {
            assertEquals(e.getMessage(),"Deadlineul trebuie sa fie intre 1-14.");
        }

    }
    @Test
    public void addAssignementFailDeadlineMinus(){
        this.Setup();
        Tema new_tema = new Tema("1111","Test", -100, 98);

        try{
            service.addTema(new_tema);
        }
        catch(Exception e) {
            assertEquals(e.getMessage(),"Deadlineul trebuie sa fie intre 1-14.");
        }

    }
    @Test
    public void addAssignementFailDeadlinePlus(){
        this.Setup();
        Tema new_tema = new Tema("1111","Test", 16, 15);

        try{
            service.addTema(new_tema);
        }
        catch(Exception e) {
            assertEquals(e.getMessage(),"Deadlineul trebuie sa fie intre 1-14.");
        }

    }
    @Test
    public void addAssignementFailPrimire(){
        this.Setup();
        Tema new_tema = new Tema("1111","Test", 10, 0);

        try{
            service.addTema(new_tema);
        }
        catch(Exception e) {
            assertEquals(e.getMessage(),"Saptamana primirii trebuie sa fie intre 1-14.");
        }

    }
    @Test
    public void addAssignementFailPrimireMinus(){
        this.Setup();
        Tema new_tema = new Tema("1111","Test", 10, -100);

        try{
            service.addTema(new_tema);
        }
        catch(Exception e) {
            assertEquals(e.getMessage(),"Saptamana primirii trebuie sa fie intre 1-14.");
        }

    }
    @Test
    public void addAssignementFailPrimirePlus(){
        this.Setup();
        Tema new_tema = new Tema("1111","Test", 10, 15);

        try{
            service.addTema(new_tema);
        }
        catch(Exception e) {
            assertEquals(e.getMessage(),"Saptamana primirii trebuie sa fie intre 1-14.");
        }

    }
    @Test
    public void addAssignementFailDeadlinePrimireSwitch(){
        this.Setup();
        Tema new_tema = new Tema("1111","Test", 5, 8);

        try{
            service.addTema(new_tema);
        }
        catch(Exception e) {
            assertEquals(e.getMessage(),"Primire < Deadline!");
        }

    }

    @Test
    public void addAssignmentIdString(){
        this.Setup();
        Tema new_tema = new Tema("aba","test", 10, 8);

        try{
            service.addTema(new_tema);
        }
        catch(Exception e) {
            assertEquals(e.getMessage(),"Id trebuie sa fie numar!");
        }

    }

    @Test
    public void addAssignmentIdNegativ(){
        this.Setup();
        Tema new_tema = new Tema("-100","test", 10, 8);

        try{
            service.addTema(new_tema);
        }
        catch(Exception e) {
            assertEquals(e.getMessage(),"Id trebuie sa fie pozitiv!");
        }

    }

    @Test
    public void addAssignmentIdStringNumar(){
        this.Setup();
        Tema new_tema = new Tema("10a","test", 10, 8);

        try{
            service.addTema(new_tema);
        }
        catch(Exception e) {
            assertEquals(e.getMessage(),"Id trebuie sa fie numar!");
        }

    }

    // Integration
    @Test
    public void testAddStudentIntegration() {
        this.Setup();
        Student new_student = new Student("2133","Struc",937,"etc@yahoo.com");

        service.addStudent(new_student);
        assertEquals(service.findStudent("2133"),new_student);

        service.deleteStudent("2133");
    }

    @Test
    public void testAddAssignmentIntegration() {

        this.Setup();
        Tema new_tema = new Tema("1234","Test homework", 7, 5);

        service.addTema(new_tema);
        assertEquals(service.findTema("1234"),new_tema);

        service.deleteTema("1234");
    }

    @Test
    public void testAddNotaIntegration() {

        this.Setup();
        Nota new_nota = new Nota("123","4","1",10, LocalDate.now());

        try {
            service.addNota(new_nota,"nu e bine");
            assert(false);
        }
        catch(Exception e){
            assert(true);
        }
    }

    @Test
    public void testAllIntegration() {
        this.Setup();
        try {
            testAddStudentIntegration();
            testAddAssignmentIntegration();
            testAddNotaIntegration();
            assert(true);
        }
        catch(Exception e){
            assert(false);
        }
    }


}
