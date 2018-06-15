package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Match {
    private String firstTeam;
    private String secondTeam;
    private int firstTeamScores;
    private int secondTeamScores;
}
