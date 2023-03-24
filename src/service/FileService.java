package service;

import domain.*;
import repository.*;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class FileService {
    private StudentFileRepository studentFileRepo;
    private TemaFileRepository temaFileRepo;
    private NotaFileRepository notaFileRepo;

    public FileService(StudentFileRepository studentFileRepo, TemaFileRepository temaFileRepo,NotaFileRepository notaFileRepo) {
        this.studentFileRepo = studentFileRepo;
        this.temaFileRepo = temaFileRepo;
        this.notaFileRepo = notaFileRepo;
    }

    public Iterable<Student> findAllStudents() { return studentFileRepo.findAll(); }

    public Iterable<Tema> findAllTeme() { return temaFileRepo.findAll(); }

    public Iterable<Nota> findAllNote() { return notaFileRepo.findAll(); }

    public int saveStudent(String id, String nume, int grupa) {
        Student student = new Student(id, nume, grupa);
        Student result = studentFileRepo.save(student);

        if (result == null) {
            return 1;
        }
        return 0;
    }

    public int saveTema(String id, String descriere, int deadline, int startline) {
        Tema tema = new Tema(id, descriere, deadline, startline);
        Tema result = temaFileRepo.save(tema);

        if (result == null) {
            return 1;
        }
        return 0;
    }

    public int saveNota(String idStudent, String idTema, double valNota, int predata, String feedback) {
        if (studentFileRepo.findOne(idStudent) == null || temaFileRepo.findOne(idTema) == null) {
            return -1;
        }
        else {
            int deadline = temaFileRepo.findOne(idTema).getDeadline();

            if (predata - deadline > 2) {
                valNota =  1;
            } else {
                valNota =  valNota - 2.5 * (predata - deadline);
            }
            Nota nota = new Nota(new Pair(idStudent, idTema), valNota, predata, feedback);
            Nota result = notaFileRepo.save(nota);

            if (result == null) {
                return 1;
            }
            return 0;
        }
    }

    public int deleteStudent(String id) {
        Student result = studentFileRepo.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int deleteTema(String id) {
        Tema result = temaFileRepo.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int updateStudent(String id, String numeNou, int grupaNoua) {
        Student studentNou = new Student(id, numeNou, grupaNoua);
        Student result = studentFileRepo.update(studentNou);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int updateTema(String id, String descriereNoua, int deadlineNou, int startlineNou) {
        Tema temaNoua = new Tema(id, descriereNoua, deadlineNou, startlineNou);
        Tema result = temaFileRepo.update(temaNoua);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int extendDeadline(String id, int noWeeks) {
        Tema tema = temaFileRepo.findOne(id);

        if (tema != null) {
            LocalDate date = LocalDate.now();
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int currentWeek = date.get(weekFields.weekOfWeekBasedYear());

            if (currentWeek >= 39) {
                currentWeek = currentWeek - 39;
            } else {
                currentWeek = currentWeek + 12;
            }

            if (currentWeek <= tema.getDeadline()) {
                int deadlineNou = tema.getDeadline() + noWeeks;
                return updateTema(tema.getID(), tema.getDescriere(), deadlineNou, tema.getStartline());
            }
        }
        return 0;
    }

    public void createStudentFile(String idStudent, String idTema) {
        Nota nota = notaFileRepo.findOne(new Pair(idStudent, idTema));

        notaFileRepo.createFile(nota);
    }
}
