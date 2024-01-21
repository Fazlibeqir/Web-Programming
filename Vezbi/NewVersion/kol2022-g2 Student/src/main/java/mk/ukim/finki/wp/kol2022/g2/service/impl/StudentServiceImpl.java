package mk.ukim.finki.wp.kol2022.g2.service.impl;

import mk.ukim.finki.wp.kol2022.g2.model.Course;
import mk.ukim.finki.wp.kol2022.g2.model.Student;
import mk.ukim.finki.wp.kol2022.g2.model.StudentType;
import mk.ukim.finki.wp.kol2022.g2.model.exceptions.InvalidStudentIdException;
import mk.ukim.finki.wp.kol2022.g2.repository.StudentRepository;
import mk.ukim.finki.wp.kol2022.g2.service.CourseService;
import mk.ukim.finki.wp.kol2022.g2.service.StudentService;
import org.hibernate.mapping.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService, UserDetailsService {
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

    @Override
    public Student create(String name, String email, String password, StudentType type, List<Long> courseId, LocalDate enrollmentDate) {
        List<Course> courses=courseId.stream().map(id->this.courseService.findById(id)).collect(Collectors.toList());
        Student student=new Student(name,email,passwordEncoder.encode(password),type,courses,enrollmentDate);
        return this.studentRepository.save(student);
    }

    @Override
    public Student update(Long id, String name, String email, String password, StudentType type, List<Long> coursesId, LocalDate enrollmentDate) {
        List<Course> courses=coursesId.stream().map(ids->this.courseService.findById(ids)).collect(Collectors.toList());
        Student student=findById(id);
        student.setName(name);
        student.setEmail(email);
        student.setPassword(passwordEncoder.encode(password));
        student.setType(type);
        student.setCourses(courses);
        student.setEnrollmentDate(enrollmentDate);
        return this.studentRepository.save(student);
    }

    @Override
    public Student delete(Long id) {
        Student student=findById(id);
        this.studentRepository.delete(student);
        return student;
    }

    @Override
    public List<Student> filter(Long courseId, Integer yearsOfStudying) {
        if (courseId==null && yearsOfStudying==null){
            return listAll();
        }else if(courseId!=null && yearsOfStudying!=null){
            Course course=this.courseService.findById(courseId);
            LocalDate years=LocalDate.now().minusYears(yearsOfStudying);
            return this.studentRepository.findByCoursesContainsAndEnrollmentDateBefore(course,years);
        }else if(courseId!=null){
            Course course=this.courseService.findById(courseId);
            return this.studentRepository.findByCoursesContains(course);
        }else {
            LocalDate years=LocalDate.now().minusYears(yearsOfStudying);
            return this.studentRepository.findByEnrollmentDateBefore(years);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student=this.studentRepository.findByEmail(username).orElseThrow();
        GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_"+student.getType());
        List<GrantedAuthority> authorities= Collections.singletonList(authority);
        return new User(
                student.getEmail(),
                student.getPassword(),
                authorities
        );
    }
}
