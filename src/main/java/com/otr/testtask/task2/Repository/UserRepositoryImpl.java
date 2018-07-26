package com.otr.testtask.task2.Repository;

import com.otr.testtask.task2.Model.Phones;
import com.otr.testtask.task2.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    public DataSource dataSource;

    private static JdbcTemplate jdbcTemplate;

    private static int countIdTempForPhones = 0;
    private static int countIdTempForUsers = 0;


    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public List<Phones> getPhonesByFullName(String name) {

        String query = "SELECT p.num " +
                "FROM users u " +
                "INNER JOIN phones p " +
                "ON u.id = p.user_id " +
                "WHERE u.full_name like '%" + name + "%'";

        List<Phones> list = jdbcTemplate.query(query, (resultSet, i) -> {
            Phones phones = new Phones();
            phones.setNum(resultSet.getString("num"));
            return phones;
        });

        return list;
    }

    @Override
    @Transactional
    public boolean setNumberForUser(String name, String number) {

        String queryGetUserId = "SELECT u.id " +
                "FROM users u " +
                "WHERE u.full_name like '" + name + "' " +
                "LIMIT 1";

        List<Users> usersId = jdbcTemplate.query(queryGetUserId, (resultSet, i) -> {
            Users user = new Users();
            user.setId(resultSet.getLong("id"));
            return user;
        });

        if (usersId.isEmpty()) {
            return false;
        }

        while (true) {
            try {
                String queryAddNumberForUser = "INSERT INTO phones (id, num, user_id) " +
                        "VALUES (" + countIdTempForPhones + " , '" + number + "','" + usersId.get(0).getId() + "')";
                jdbcTemplate.execute(queryAddNumberForUser);
                break;
            } catch (DuplicateKeyException e) {
                countIdTempForPhones++;
            }
        }

        return true;
    }

    @Override
    @Transactional
    public void deleteUserForName(String name) {
        String queryGetIdForUser = "SELECT u.id " +
                "FROM users u " +
                "WHERE u.full_name like '" + name + "' " +
                "LIMIT 1";

        List<Users> users = jdbcTemplate.query(queryGetIdForUser, (resultSet, i) -> {
            Users user = new Users();
            user.setId(resultSet.getLong("id"));
            return user;
        });

        String queryCascadeDelete = "DELETE FROM phones " +
                "WHERE user_id = " + users.get(0).getId();

        jdbcTemplate.execute(queryCascadeDelete);

        String query = "DELETE FROM users " +
                "WHERE full_name = '" + name + "'";

        jdbcTemplate.update(query);
    }

    @Override
    @Transactional
    public void addUser(String name, String number) {

        String queryGetIdFromUser = "SELECT u.id " +
                "FROM users u " +
                "WHERE u.full_name like '" + name + "'";

        List<Users> checkUser = jdbcTemplate.query(queryGetIdFromUser, (resultSet, i) -> {
            Users user = new Users();
            user.setId(resultSet.getLong("id"));
            return user;
        });

        if (!checkUser.isEmpty()) {
            System.out.println("Пользователь уже существует!");
            return;
        } else {
            while (true) {
                try {
                    String queryAddUser = "INSERT INTO users (id, full_name) " +
                            "VALUES (" + countIdTempForUsers + ", '" + name + "')";
                    jdbcTemplate.update(queryAddUser);
                    break;
                } catch (DuplicateKeyException e) {
                    countIdTempForUsers++;
                }
            }
        }
    }
}
