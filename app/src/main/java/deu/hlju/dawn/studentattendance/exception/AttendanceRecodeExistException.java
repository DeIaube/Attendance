package deu.hlju.dawn.studentattendance.exception;

import deu.hlju.dawn.studentattendance.base.BaseException;

public class AttendanceRecodeExistException extends BaseException{

    public AttendanceRecodeExistException() {
        super("当前课程已签到完成");
    }
}
