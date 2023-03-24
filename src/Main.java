

import console.*;
import domain.*;
import repository.*;
import service.*;
import validation.*;

public class Main {
    public static void main(String[] args) {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        //XML Repos
        StudentXMLRepository studentXMLRepository = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository temaXMLRepository = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository notaXMLRepository = new NotaXMLRepository(notaValidator, "note.xml");

        // Repos
        StudentRepository studentRepository = new StudentRepository(studentValidator);
        TemaRepository temaRepository = new TemaRepository(temaValidator);
        NotaRepository notaRepository = new NotaRepository(notaValidator);


        // File Repos
        StudentFileRepository studentFileRepository = new StudentFileRepository(studentValidator, "studenti.txt");
        TemaFileRepository temaFileRepository = new TemaFileRepository(temaValidator, "teme.txt");
        NotaFileRepository notaFileRepository = new NotaFileRepository(notaValidator, "note.txt");


//        Service service = new Service(studentRepository, temaRepository, notaRepository);
//        FileService fileService = new FileService(studentFileRepository, temaFileRepository, notaFileRepository);
        XMLService xmlService = new XMLService(studentXMLRepository, temaXMLRepository, notaXMLRepository);
        UI consola = new UI(xmlService);
        consola.run();

        //PENTRU GUI
        // de avut un check: daca profesorul introduce sau nu saptamana la timp
        // daca se introduce nota la timp, se preia saptamana din sistem
        // altfel, se introduce de la tastatura
    }
}
