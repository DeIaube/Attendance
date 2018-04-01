package deu.hlju.dawn.studentattendance.exception;

import deu.hlju.dawn.studentattendance.base.BaseException;

public class RoomNotExistException extends BaseException{
    public RoomNotExistException() {
        super("此教室不存在,请重试");
    }
}
