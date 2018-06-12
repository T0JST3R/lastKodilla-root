package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MatchDto {
    private String firstTeam;
    private String secondTeam;
    private int firstTeamScores;
    private int secondTeamScores;
}
