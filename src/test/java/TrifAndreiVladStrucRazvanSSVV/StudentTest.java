package TrifAndreiVladStrucRazvanSSVV;

import TrifAndreiVladStrucRazvanSSVV.domain.Student;
import TrifAndreiVladStrucRazvanSSVV.repository.*;
import TrifAndreiVladStrucRazvanSSVV.service.Service;
import TrifAndreiVladStrucRazvanSSVV.validation.NotaValidator;
import TrifAndreiVladStrucRazvanSSVV.validation.StudentValidator;
import TrifAndreiVladStrucRazvanSSVV.validation.TemaValidator;
import org.junit.Test;

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
}