package deu.hlju.dawn.studentattendance.exception;

import deu.hlju.dawn.studentattendance.base.BaseException;

public class NetworkException extends BaseException {

    public NetworkException() {
        super("请检查网络后重试");
    }
}
