package deu.hlju.dawn.studentattendance.exception;

import deu.hlju.dawn.studentattendance.base.BaseException;

public class FaceDetectCountException extends BaseException{
    public FaceDetectCountException() {
        super("请检查网络后重试");
    }
}
