package com.otr.testtask.task2.Repository;

import com.otr.testtask.task2.Exceptions.JdbcConnectException;
import com.otr.testtask.task2.Model.Phones;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    DataSource dataSource;

    private static JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
//        if(jdbcTemplate == null){
//            throw new JDBCConnectionException("Ошибка соединения", new JdbcConnectException());
//        }
    }

    @Override
    public List<Phones> getPhonesByFullName(String name) {

        String query = "SELECT p.num " +
                "FROM users u " +
                "INNER JOIN phones p " +
                "ON u.id = p.user_id " +
                "WHERE u.full_name like '%" + name + "%'";

        List<Phones> list = jdbcTemplate.query(query, (resultSet, i) -> {
                Phones phones = new Phones();
                phones.setNum(resultSet.getString(1));
                return phones;
            });
        return list;
    }
}
