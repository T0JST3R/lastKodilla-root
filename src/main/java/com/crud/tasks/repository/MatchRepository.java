package com.crud.tasks.repository;

import com.crud.tasks.domain.Match;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match , Long> {
}
