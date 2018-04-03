package deu.hlju.dawn.studentattendance.exception;

import deu.hlju.dawn.studentattendance.base.BaseException;

public class StudentExistException extends BaseException{

    public StudentExistException() {
        super("此学生已存在,请重试");
    }
}
