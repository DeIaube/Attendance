package deu.hlju.dawn.studentattendance.exception;

import deu.hlju.dawn.studentattendance.base.BaseException;


public class FaceSearchNotFindException extends BaseException {
    public FaceSearchNotFindException() {
        super("识别错误,请重试");
    }
}
