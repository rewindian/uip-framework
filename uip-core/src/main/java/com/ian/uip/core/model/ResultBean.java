package com.ian.uip.core.model;

import com.ian.uip.core.exception.AuthException;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int SUCCESS = 0;

    public static final int FAIL = 1;

    public static final int NEED_LOGIN = 2;

    private String msg = "success";

    private int code = SUCCESS;

    private Object data;

    public ResultBean() {
        super();
    }

    public ResultBean(Object data) {
        super();
        this.data = data;
    }

    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = e instanceof AuthException ? NEED_LOGIN : FAIL;
    }

    public ResultBean(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public ResultBean(int code, String msg, Object data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
