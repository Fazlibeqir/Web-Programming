package mk.ukim.finki.wp.kol2022.g1.service.impl;

import mk.ukim.finki.wp.kol2022.g1.model.Employee;
import mk.ukim.finki.wp.kol2022.g1.model.EmployeeType;
import mk.ukim.finki.wp.kol2022.g1.model.Skill;
import mk.ukim.finki.wp.kol2022.g1.model.exceptions.InvalidEmployeeIdException;
import mk.ukim.finki.wp.kol2022.g1.repository.EmployeeRepository;
import mk.ukim.finki.wp.kol2022.g1.service.EmployeeService;
import mk.ukim.finki.wp.kol2022.g1.service.SkillService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
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

        List<Skill> skills = skillId.stream().map(id -> this.skillService.findById(id)).collect(Collectors.toList());

        return this.employeeRepository.save(new Employee(name, email, passwordEncoder.encode(password), type,skills, employmentDate));
    }

    @Override
    public Employee update(Long id, String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {
        Employee employee = this.findById(id);
        employee.setName(name);
        employee.setEmail(email);
        employee.setPassword(this.passwordEncoder.encode(password));
        employee.setType(type);
        employee.setEmploymentDate(employmentDate);
        List<Skill> skills = skillId.stream().map(skill -> this.skillService.findById(skill)).collect(Collectors.toList());
        employee.setSkills(skills);
        return this.employeeRepository.save(employee);
    }

    @Override
    public Employee delete(Long id) {
        Employee employee = findById(id);
        this.employeeRepository.delete(employee);
        return employee;
    }

    @Override
    public List<Employee> filter(Long skillId, Integer yearsOfService) {

        if (skillId != null && yearsOfService != null) {
            Skill skill=skillService.findById(skillId);
            LocalDate employmentDate= LocalDate.now().minusYears(yearsOfService);
            return this.employeeRepository.findByEmploymentDateBeforeAndSkillsContaining(employmentDate,skill);
        }
        else if(skillId==null && yearsOfService==null){
            return this.employeeRepository.findAll();
        }else if(skillId!=null){
            Skill skill=skillService.findById(skillId);
            return this.employeeRepository.findBySkillsContaining(skill);
        }else{
            LocalDate employmentDate= LocalDate.now().minusYears(yearsOfService);
            return this.employeeRepository.findByEmploymentDateBefore(employmentDate);

        }
    }
}
