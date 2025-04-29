package com.I2I.I2IBaceknd.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

abstract public class dao {

    @Autowired
    protected DataSource dataSource;

    @Autowired
    Environment env;

    public List<Map<String, Object>> executeQuery(String query) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForList(query);
    }

    public List<Map<String, Object>> executeQuery(String query, Map<String, Object> param) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.queryForList(query, param);
    }

    public <T> List<T> executeQuery(Class<T> obj, String query, Map<String, Object> param) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.query(query, param, new BeanPropertyRowMapper<>(obj));
    }

    public <T> List<T> executeQuery(Class<T> obj, String query) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(obj));
    }

    @SuppressWarnings("deprecation")
    public <T> T executeQueryForObject(Class<T> obj, String query, Object[] param) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForObject(query, param, new BeanPropertyRowMapper<>(obj));
    }

    public <T> T executeQueryForObject(Class<T> obj, String query) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(obj));
    }

    public int executeUpdate(String query) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.update(query);
    }

    public String execute(String query) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForObject(query, String.class);
    }
    
    public long executeInsert(String query, List<Object> params) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(query, new String[] { "id" });
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int executeUpdate(String query, Map<String, Object> param) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        MapSqlParameterSource paramSource = new MapSqlParameterSource(param);
        return jdbcTemplate.update(query, paramSource);
    }

    public long executeInsert(String query, Map<String, Object> param) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        MapSqlParameterSource paramSource = new MapSqlParameterSource(param);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, paramSource, keyHolder, new String[] { "id" });
        return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : 0;
    }


    public long Save(String query, Map<String, Object> param, String primaryKeyColumn) {
        System.out.println("Save(query,param) Method");
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        MapSqlParameterSource paramSource = new MapSqlParameterSource(param);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int row_affected = jdbcTemplate.update(query, paramSource, keyHolder, new String[] {primaryKeyColumn});
        long return_id = 0;
        if (keyHolder.getKey() != null) 
            return_id = keyHolder.getKey().longValue();
        else
            return_id = row_affected;
    
        return return_id;
    }


  
}
