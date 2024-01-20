package mk.ukim.finki.wp.jan2023.service.impl;

import jakarta.servlet.http.Part;
import mk.ukim.finki.wp.jan2023.model.Candidate;
import mk.ukim.finki.wp.jan2023.model.Gender;
import mk.ukim.finki.wp.jan2023.model.Party;
import mk.ukim.finki.wp.jan2023.model.exceptions.InvalidCandidateIdException;
import mk.ukim.finki.wp.jan2023.repository.CandidateRepository;
import mk.ukim.finki.wp.jan2023.service.CandidateService;
import mk.ukim.finki.wp.jan2023.service.PartyService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CandidateServiceImpl  implements CandidateService {
    private final PartyService partyService;
    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(PartyService partyService, CandidateRepository candidateRepository) {
        this.partyService = partyService;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public List<Candidate> listAllCandidates() {
        return this.candidateRepository.findAll();
    }

    @Override
    public Candidate findById(Long id) {
        return this.candidateRepository.findById(id).orElseThrow(InvalidCandidateIdException::new);
    }

    @Override
    public Candidate create(String name, String bio, LocalDate dateOfBirth, Gender gender, Long party) {
        Party party1=this.partyService.findById(party);
        return this.candidateRepository.save(new Candidate(name,bio,dateOfBirth,gender,party1));
    }

    @Override
    public Candidate update(Long id, String name, String bio, LocalDate dateOfBirth, Gender gender, Long party) {
        Party party1=this.partyService.findById(party);
        Candidate candidate=findById(id);
        candidate.setName(name);
        candidate.setBio(bio);
        candidate.setDateOfBirth(dateOfBirth);
        candidate.setGender(gender);
        candidate.setParty(party1);
        return this.candidateRepository.save(candidate);
    }

    @Override
    public Candidate delete(Long id) {
        Candidate candidate=findById(id);
        this.candidateRepository.delete(candidate);
        return candidate;
    }

    @Override
    public Candidate vote(Long id) {
        Candidate candidate=findById(id);
        candidate.setVotes(candidate.getVotes()+1);
        return this.candidateRepository.save(candidate);
    }

    @Override
    public List<Candidate> listCandidatesYearsMoreThanAndGender(Integer yearsMoreThan, Gender gender) {
       if(yearsMoreThan==null && gender==null){
           return listAllCandidates();
       }else if(yearsMoreThan!=null && gender !=null){
           LocalDate years=LocalDate.now().minusYears(yearsMoreThan);
           return this.candidateRepository.findByDateOfBirthBeforeAndGender(years,gender);
       }else if(yearsMoreThan!=null){
           LocalDate years=LocalDate.now().minusYears(yearsMoreThan);
           return this.candidateRepository.findByDateOfBirthBefore(years);
       }else{
           return this.candidateRepository.findByGender(gender);
       }
    }
}
