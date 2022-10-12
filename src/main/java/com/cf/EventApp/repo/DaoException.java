package com.cf.EventApp.repo;

public class DaoException extends Exception {

    public DaoException(String msg) {
        super(msg);
    }

    public DaoException(String msg, Throwable inner) {
        super(msg, inner);
    }

}
