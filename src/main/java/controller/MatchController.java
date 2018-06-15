package controller;

import domain.MatchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/match")
public class MatchController {
    @RequestMapping(method = RequestMethod.GET , value = "getMatches")
    public List<MatchDto> getMatches(){
        return new ArrayList<>();
    }
    @RequestMapping(method = RequestMethod.GET, value = "getMatch")
    public MatchDto getMatch(Long id){
        return new MatchDto("Polska" , "Senegal" , 1, 0);
    }
     @RequestMapping(method = RequestMethod.DELETE , value = "deleteMatch")
    public void deleteMatch(Long matchId){

    }
    @RequestMapping(method = RequestMethod.PUT , value = "updateMatch")
    public MatchDto updateMatch(){
        return new MatchDto("Polska" , "Senegal" , 1, 0);
    }
    @RequestMapping(method = RequestMethod.POST , value = "createMatch")
    public void createMatch(MatchDto match){

    }
}
