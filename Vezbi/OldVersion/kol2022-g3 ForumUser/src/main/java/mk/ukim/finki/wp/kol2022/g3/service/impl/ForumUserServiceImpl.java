package mk.ukim.finki.wp.kol2022.g3.service.impl;
import mk.ukim.finki.wp.kol2022.g3.model.ForumUser;
import mk.ukim.finki.wp.kol2022.g3.model.ForumUserType;
import mk.ukim.finki.wp.kol2022.g3.model.Interest;
import mk.ukim.finki.wp.kol2022.g3.model.exceptions.InvalidForumUserIdException;
import mk.ukim.finki.wp.kol2022.g3.repository.ForumUserRepository;
import mk.ukim.finki.wp.kol2022.g3.service.ForumUserService;
import mk.ukim.finki.wp.kol2022.g3.service.InterestService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumUserServiceImpl implements ForumUserService {
    private final ForumUserRepository forumUserRepository;
    private final InterestService interestService;
    private final PasswordEncoder passwordEncoder;

    public ForumUserServiceImpl(ForumUserRepository forumUserRepository, InterestService interestService, PasswordEncoder passwordEncoder) {
        this.forumUserRepository = forumUserRepository;
        this.interestService = interestService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<ForumUser> listAll() {
        return this.forumUserRepository.findAll();
    }

    @Override
    public ForumUser findById(Long id) {
        return this.forumUserRepository.findById(id).orElseThrow(InvalidForumUserIdException::new);
    }

    @Override
    public ForumUser create(String name, String email, String password, ForumUserType type, List<Long> interestId, LocalDate birthday) {
        List<Interest> interests= interestId.stream().map(id->this.interestService.findById(id)).collect(Collectors.toList());
        return this.forumUserRepository.save(new ForumUser(name,email,passwordEncoder.encode(password),type,interests,birthday));
    }

    @Override
    public ForumUser update(Long id, String name, String email, String password, ForumUserType type, List<Long> interestId, LocalDate birthday) {
       ForumUser forumUser=findById(id);
       forumUser.setName(name);
       forumUser.setEmail(email);
       forumUser.setPassword(passwordEncoder.encode(password));
       forumUser.setType(type);
       forumUser.setInterests(interestId.stream().map(ids->this.interestService.findById(ids)).collect(Collectors.toList()));
       forumUser.setBirthday(birthday);
        return this.forumUserRepository.save(forumUser);
    }

    @Override
    public ForumUser delete(Long id) {
        ForumUser forumUser=findById(id);
        this.forumUserRepository.delete(forumUser);
        return forumUser;
    }

    //TODO maybe it is wrong check it after
    @Override
    public List<ForumUser> filter(Long interestId, Integer age) {
        if(interestId==null&&age==null){
            return listAll();
        }else if(interestId !=null && age!=null){
            Interest interest=this.interestService.findById(interestId);
            LocalDate birthdayBefore=LocalDate.now().minusYears(age);
            return this.forumUserRepository.findByInterestsContainingAndBirthdayBefore(interest,birthdayBefore);
        }else if(interestId!=null){
            Interest interest=this.interestService.findById(interestId);
            return this.forumUserRepository.findByInterestsContaining(interest);
        }else{
            LocalDate birthdayBefore=LocalDate.now().minusYears(age);
            return this.forumUserRepository.findByBirthdayBefore(birthdayBefore);
        }
    }
}
