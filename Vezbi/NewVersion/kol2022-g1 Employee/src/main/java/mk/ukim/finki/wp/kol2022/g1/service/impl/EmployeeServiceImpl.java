package mk.ukim.finki.wp.kol2022.g1.service.impl;

import mk.ukim.finki.wp.kol2022.g1.model.Employee;
import mk.ukim.finki.wp.kol2022.g1.model.EmployeeType;
import mk.ukim.finki.wp.kol2022.g1.model.Skill;
import mk.ukim.finki.wp.kol2022.g1.model.exceptions.InvalidEmployeeIdException;
import mk.ukim.finki.wp.kol2022.g1.repository.EmployeeRepository;
import mk.ukim.finki.wp.kol2022.g1.service.EmployeeService;
import mk.ukim.finki.wp.kol2022.g1.service.SkillService;
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
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final SkillService skillService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, SkillService skillService) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.skillService = skillService;
    }

    @Override
    public List<Employee> listAll() {
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return this.employeeRepository.findById(id).orElseThrow(InvalidEmployeeIdException::new);
    }

    @Override
    public Employee create(String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {
        String encodedPass= passwordEncoder.encode(password);
        List<Skill> skills=skillId.stream().map(id->this.skillService.findById(id)).collect(Collectors.toList());
        Employee employee=new Employee(name,email,encodedPass,type,skills,employmentDate);
        return this.employeeRepository.save(employee);
    }

    @Override
    public Employee update(Long id, String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {
        String encodedPass= passwordEncoder.encode(password);
        List<Skill> skills=skillId.stream().map(ids->this.skillService.findById(ids)).collect(Collectors.toList());
        Employee employee=findById(id);
        employee.setName(name);
        employee.setEmail(email);
        employee.setPassword(encodedPass);
        employee.setType(type);
        employee.setSkills(skills);
        employee.setEmploymentDate(employmentDate);

        return this.employeeRepository.save(employee);
    }

    @Override
    public Employee delete(Long id) {
        Employee employee=findById(id);
        this.employeeRepository.delete(employee);
        return employee;
    }

    @Override
    public List<Employee> filter(Long skillId, Integer yearsOfService) {
        if(skillId==null && yearsOfService==null){
            return listAll();
        }else if(skillId!=null && yearsOfService!=null){
            Skill skill=this.skillService.findById(skillId);
            LocalDate years=LocalDate.now().minusYears(yearsOfService);
            return this.employeeRepository.findBySkillsContainsAndEmploymentDateBefore(skill,years);
        }else if(skillId!=null){
            Skill skill=this.skillService.findById(skillId);
            return this.employeeRepository.findBySkillsContains(skill);
        }else {
            LocalDate years=LocalDate.now().minusYears(yearsOfService);
            return this.employeeRepository.findByEmploymentDateBefore(years);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee=this.employeeRepository.findByEmail(username);
        GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_"+employee.getType());
        List<GrantedAuthority> authorities=Collections.singletonList(authority);
        return new User(
                employee.getEmail(),
                employee.getPassword(),
                authorities
        );
    }
}
