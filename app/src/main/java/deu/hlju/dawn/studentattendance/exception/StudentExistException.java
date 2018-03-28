package deu.hlju.dawn.studentattendance.exception;

import deu.hlju.dawn.studentattendance.base.BaseException;

/**
 * Created by Dwan on 2018/3/27.
 */

public class StudentExistException extends BaseException{
    public StudentExistException() {
        super("当前学生已存在,请重试");
    }
}
