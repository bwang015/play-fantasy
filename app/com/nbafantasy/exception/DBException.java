package com.nbafantasy.exception;

/**
 * Created by bwang on 9/14/17.
 */
public class DBException extends RuntimeException {

    public DBException(){}

    public DBException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
