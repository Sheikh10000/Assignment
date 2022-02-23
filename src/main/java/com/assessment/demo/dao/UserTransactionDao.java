package com.assessment.demo.dao;

import com.assessment.demo.exception.UserTransactionExecption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Repository
@Slf4j
public class UserTransactionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Integer insertIntoUserTransactionInfo(Integer userId, Date transactionDate) throws UserTransactionExecption {
        String sqlToInsert = "insert into USER_TRANS_INFO(USER_ID,TRANS_DT) values (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlToInsert, new String[]{"TRANS_INFO_ID"});
            ps.setInt(1, userId);
            ps.setTimestamp(2, new Timestamp(transactionDate.getTime()));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();

    }

    public void insertIntoUserTransactionAmt(Integer integer, Double transactionAmt) throws UserTransactionExecption {
        String sqlToInsert = "insert into USER_TRANS_AMT(USER_ID,TRANS_AMT) values (:userId,:amt)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", integer);
        mapSqlParameterSource.addValue("amt", transactionAmt);
        int update = namedParameterJdbcTemplate.update(sqlToInsert, mapSqlParameterSource);

    }

    public void calculateTotalAmt(Integer userId, Double transactionAmt) throws UserTransactionExecption {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        mapSqlParameterSource.addValue("amt", transactionAmt);

        log.info("checking if total amount exists for this user");
        String userTotAmt = "select count(TOT_AMT) from USER_TRANS_TOT_AMT where user_id =:userId";
        Integer userTotAmtCnt = namedParameterJdbcTemplate.queryForObject(userTotAmt, mapSqlParameterSource, Integer.class);
        if (userTotAmtCnt == 0) {
            String sqlToInsert = "insert into USER_TRANS_TOT_AMT(USER_ID,TOT_AMT) values (:userId,:amt)";
            namedParameterJdbcTemplate.update(sqlToInsert, mapSqlParameterSource);
        } else {
            String sqltoUpdt = "update USER_TRANS_TOT_AMT SET TOT_AMT = TOT_AMT+:amt where user_id = :userId";
            namedParameterJdbcTemplate.update(sqltoUpdt, mapSqlParameterSource);
        }

    }

    public Double getTotalAmtForUser(Integer userId) {
        String totAmtQry = "select TOT_AMT from USER_TRANS_TOT_AMT where user_id =:userId";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        Double totalAmt = namedParameterJdbcTemplate.queryForObject(totAmtQry, mapSqlParameterSource, Double.class);
        if (Objects.isNull(totalAmt)) return Double.MIN_VALUE;
        return totalAmt;

    }

    public Date getRecentTransDate(Integer userId) {
        String recentTrnDate = "select TRANS_DT from USER_TRANS_TOT_AMT uta join USER_TRANS_AMT utm on uta.user_id = utm.user_id " +
                "join USER_TRANS_INFO uti on uti.user_id = utm.user_id  " +
                "where uta.user_id = :userId        order by TRANS_DT desc limit 1 ";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        Date recentTransDate = namedParameterJdbcTemplate.queryForObject(recentTrnDate, mapSqlParameterSource, Date.class);
        if (Objects.isNull(recentTransDate)) return new Date();
        return recentTransDate;


    }

    public Double getLastTransactionAmt(Integer userId) {
        String lastTransAmtQry = "select  TRANS_AMT  from USER_TRANS_TOT_AMT uta join USER_TRANS_AMT utm on uta.user_id = utm.user_id\n" +
                "        join USER_TRANS_INFO uti on uti.user_id = utm.user_id\n" +
                "        where uta.user_id = :userId\n" +
                "        order by TRANS_AMT_ID desc limit 1";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        Double lastTrnsAmt = namedParameterJdbcTemplate.queryForObject(lastTransAmtQry, mapSqlParameterSource, Double.class);
        if (Objects.isNull(lastTrnsAmt)) return Double.MIN_VALUE;
        return lastTrnsAmt;
    }
}
