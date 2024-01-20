package mk.ukim.finki.wp.kol2022.g2.service.impl;

import mk.ukim.finki.wp.kol2022.g2.model.Course;
import mk.ukim.finki.wp.kol2022.g2.model.Student;
import mk.ukim.finki.wp.kol2022.g2.model.StudentType;
import mk.ukim.finki.wp.kol2022.g2.model.exceptions.InvalidStudentIdException;
import mk.ukim.finki.wp.kol2022.g2.repository.StudentRepository;
import mk.ukim.finki.wp.kol2022.g2.service.CourseService;
import mk.ukim.finki.wp.kol2022.g2.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final CourseService courseService;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository, CourseService courseService, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Student> listAll() {
        return this.studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return this.studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);
    }

    //TODO passwordEncoder
    @Override
    public Student create(String name, String email, String password, StudentType type, List<Long> courseId, LocalDate enrollmentDate) {
       List<Course> courses = courseId.stream().map(this.courseService::findById).collect(Collectors.toList());
        return this.studentRepository.save(new Student(name,email,passwordEncoder.encode(password),type,courses,enrollmentDate));
    }

    @Override
    public Student update(Long id, String name, String email, String password, StudentType type, List<Long> coursesId, LocalDate enrollmentDate) {
        Student student= findById(id);
        if (student==null)
            throw new InvalidStudentIdException();
        student.setName(name);
        student.setEmail(email);
        student.setPassword(passwordEncoder.encode(password));
        student.setType(type);
        student.setCourses(coursesId.stream().map(this.courseService::findById).collect(Collectors.toList()));
        student.setEnrollmentDate(enrollmentDate);
        return this.studentRepository.save(student);
    }

    @Override
    public Student delete(Long id) {
        Student student=findById(id);
        if (student==null)
        {
            throw  new InvalidStudentIdException();
        }
        this.studentRepository.delete(student);
        return student;
    }

    @Override
    public List<Student> filter(Long courseId, Integer yearsOfStudying) {
        if(courseId==null && yearsOfStudying==null){
            return listAll();
        }else if(courseId != null && yearsOfStudying !=null){
            Course course=this.courseService.findById(courseId);
            LocalDate enrollmentBefore=LocalDate.now().minusYears(yearsOfStudying);
            return this.studentRepository.findAllByEnrollmentDateBeforeAndCoursesContaining(enrollmentBefore,course);
        }else if(courseId !=null){
            Course course =this.courseService.findById(courseId);
            return this.studentRepository.findAllByCoursesContaining(course);
        }else{
            LocalDate enrollmentBefore=LocalDate.now().minusYears(yearsOfStudying);
            return this.studentRepository.findAllByEnrollmentDateBefore(enrollmentBefore);
        }
    }
}
