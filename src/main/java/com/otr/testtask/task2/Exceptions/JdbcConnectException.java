package com.otr.testtask.task2.Exceptions;

import java.sql.SQLException;

public class JdbcConnectException extends SQLException {
    public JdbcConnectException(String s) {
        super(s);
    }
    public JdbcConnectException() { }
}
