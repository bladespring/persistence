package com.bladespring.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bladespring.persistence.entities.Officer;
import com.bladespring.persistence.entities.Rank;

public interface OfficerRepository extends JpaRepository<Officer, Integer> {
    List<Officer> findAllByRankAndLastNameContaining(Rank rank, String string);
}
