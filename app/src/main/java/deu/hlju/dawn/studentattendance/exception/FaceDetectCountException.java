package deu.hlju.dawn.studentattendance.exception;

import deu.hlju.dawn.studentattendance.base.BaseException;

/**
 * Created by Dwan on 2018/3/27.
 */

public class FaceDetectCountException extends BaseException{
    public FaceDetectCountException() {
        super("请检查网络后重试");
    }
}
