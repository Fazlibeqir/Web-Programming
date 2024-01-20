package mk.ukim.finki.wp.sep2022.service.impl;

import mk.ukim.finki.wp.sep2022.model.Match;
import mk.ukim.finki.wp.sep2022.model.MatchLocation;
import mk.ukim.finki.wp.sep2022.model.MatchType;
import mk.ukim.finki.wp.sep2022.model.exceptions.InvalidMatchIdException;
import mk.ukim.finki.wp.sep2022.repository.MatchRepository;
import mk.ukim.finki.wp.sep2022.service.MatchLocationService;
import mk.ukim.finki.wp.sep2022.service.MatchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final MatchLocationService matchLocationService;

    public MatchServiceImpl(MatchRepository matchRepository, MatchLocationService matchLocationService) {
        this.matchRepository = matchRepository;
        this.matchLocationService = matchLocationService;
    }

    @Override
    public List<Match> listAllMatches() {
        return this.matchRepository.findAll();
    }

    @Override
    public Match findById(Long id) {
        return this.matchRepository.findById(id).orElseThrow(InvalidMatchIdException::new);
    }

    @Override
    public Match create(String name, String description, Double price, MatchType type, Long location) {
        MatchLocation matchLocation=this.matchLocationService.findById(location);
        return this.matchRepository.save(new Match(name,description,price,type,matchLocation));
    }

    @Override
    public Match update(Long id, String name, String description, Double price, MatchType type, Long location) {
        MatchLocation matchLocation=this.matchLocationService.findById(location);
        Match match=findById(id);
        match.setName(name);
        match.setDescription(description);
        match.setPrice(price);
        match.setType(type);
        match.setLocation(matchLocation);
        return this.matchRepository.save(match);
    }

    @Override
    public Match delete(Long id) {
        Match match=findById(id);
        matchRepository.delete(match);
        return match;
    }

    @Override
    public Match follow(Long id) {
        Match match=findById(id);
        match.setFollows(match.getFollows()+1);
        return this.matchRepository.save(match);
    }

    @Override
    public List<Match> listMatchesWithPriceLessThanAndType(Double price, MatchType type) {

        if(price==null && type==null){
            return listAllMatches();
        }else if(price!=null && type!=null){
            return this.matchRepository.findMatchByPriceLessThanAndType(price,type);
        }else if ((price!=null)){
            return this.matchRepository.findMatchByPriceLessThan(price);
        }else{
            return this.matchRepository.findMatchByType(type);
        }
    }
}
