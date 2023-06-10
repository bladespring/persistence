package com.bladespring.persistence.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bladespring.persistence.entities.Officer;
import com.bladespring.persistence.entities.Rank;

@SpringBootTest
@Transactional
public class OfficerRepositoryTest {
    @Autowired
    private OfficerRepository dao;

    @Autowired
    private JdbcTemplate template;

    // private method to retrieve the current ids in the database
    private List<Integer> getIds() {
        return template.query("select id from officers", (rs, num) -> rs.getInt("id"));
    }

    @Test
    public void testSave() {
        Officer officer = new Officer(Rank.LIEUTENANT, "Wesley", "Crusher");
        officer = dao.save(officer);
        assertNotNull(officer.getId());
    }

    @Test
    public void findOneThatExists() {
        getIds().forEach(id -> {
            Optional<Officer> officer = dao.findById(id);
            assertTrue(officer.isPresent());
            assertEquals(id, officer.get().getId());
        });
    }

    @Test
    public void findOneThatDoesNotExist() {
        Optional<Officer> officer = dao.findById(999);
        assertFalse(officer.isPresent());
    }

    @Test
    public void findAll() {
        List<String> dbNames = dao.findAll().stream()
                .map(Officer::getLastName)
                .collect(Collectors.toList());
        MatcherAssert.assertThat(dbNames,
                Matchers.containsInAnyOrder("Archer", "Janeway", "Kirk", "Picard", "Sisko"));
    }

    @Test
    public void count() {
        assertEquals(5, dao.count());
    }

    @Test
    public void delete() {
        getIds().forEach(id -> {
            Optional<Officer> officer = dao.findById(id);
            assertTrue(officer.isPresent());
            dao.delete(officer.get());
        });
        assertEquals(0, dao.count());
    }

    @Test
    public void existsById() {
        getIds().forEach(id -> assertTrue(dao.existsById(id)));
    }

    @Test 
    public void findAllByRankAndLastNameContaining() {
        List<Officer> officers = dao.findAllByRankAndLastNameContaining(Rank.CAPTAIN, "i");
        officers.forEach(System.out::println);
    }
}
