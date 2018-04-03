package deu.hlju.dawn.studentattendance.exception;

import deu.hlju.dawn.studentattendance.base.BaseException;

public class StudentNotExistException extends BaseException{

    public StudentNotExistException() {
        super("此学生不存在,请重试");
    }
}
