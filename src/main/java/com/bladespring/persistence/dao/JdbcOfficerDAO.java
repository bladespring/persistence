package com.bladespring.persistence.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.bladespring.persistence.entities.Officer;
import com.bladespring.persistence.entities.Rank;

@SuppressWarnings("ConstantConditions")
@Repository
public class JdbcOfficerDAO implements OfficerDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertOfficer;

    @Autowired
    public JdbcOfficerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        insertOfficer = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("officers")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Officer save(Officer officer) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("rank", officer.getRank());
        parameters.put("first_name", officer.getFirstName());
        parameters.put("last_name", officer.getLastName());
        Integer newId = (Integer) insertOfficer.executeAndReturnKey(parameters);
        officer.setId(newId);
        return officer;
    }

    @Override
    public Optional<Officer> findById(Integer id) {
        if (!existsById(id))
            return Optional.empty();
        return Optional.of(jdbcTemplate.queryForObject(
                "SELECT * FROM officers WHERE id=?", officerMapper, id));
    }

    @Override
    public List<Officer> findAll() {
        return jdbcTemplate.query("SELECT * FROM officers", officerMapper);
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("select count(*) from officers",
                Long.class);
    }

    @Override
    public void delete(Officer officer) {
        jdbcTemplate.update("DELETE FROM officers WHERE id=?", officer.getId());
    }

    @Override
    public boolean existsById(Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS(SELECT 1 FROM officers WHERE id=?)",
                Boolean.class, id);
    }

    private RowMapper<Officer> officerMapper = (ResultSet rs, int rowNum) -> new Officer(
            rs.getInt("id"),
            Rank.valueOf(rs.getString("rank")),
            rs.getString("first_name"),
            rs.getString("last_name"));
}
