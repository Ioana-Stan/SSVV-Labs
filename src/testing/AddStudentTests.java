package testing;

import org.junit.*;
import domain.Student;
import repository.StudentRepository;
import validation.StudentValidator;

public class AddStudentTests {

    private StudentRepository studentRepo;

    @Before
    public void setUp(){
        studentRepo = new StudentRepository(new StudentValidator());
    }

    @Test
    public void testNewAdd(){
        Student student1 = new Student("22", "vali", 936);
        Student student2 = new Student("21", "iuoana", 936);
        studentRepo.save(student1);
        studentRepo.save(student2);
        Assert.assertEquals(student1, studentRepo.findOne("22"));
        Assert.assertNotEquals(student1, studentRepo.findOne("21"));
    }

    @Test
    public void testSameAdd(){

        Student student1 = new Student("22", "vali", 936);
        Student result = studentRepo.save(student1);
        studentRepo.save(student1);
        Assert.assertNull(result);
    }

}
