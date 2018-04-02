package deu.hlju.dawn.studentattendance.util;


import android.util.Log;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;

public class ScheduleUtil {

    private static Map<String, Integer> sTimeMap;

    static {
        sTimeMap = new HashMap<>();
        sTimeMap.put("1", 480);
        sTimeMap.put("2", 590);
        sTimeMap.put("3", 610);
        sTimeMap.put("4", 720);
        sTimeMap.put("5", 810);
        sTimeMap.put("6", 920);
        sTimeMap.put("7", 940);
        sTimeMap.put("8", 1050);
        sTimeMap.put("9", 1110);
        sTimeMap.put("10", 1220);
    }

    public static RelationRoomPro findMathSchedule(List<RelationRoomPro> relations) {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        int minute = calendar.get(Calendar.MINUTE) + calendar.get(Calendar.HOUR_OF_DAY) * 60;
        if (week == 1) {
            week = 7;
        } else {
            week -= 1;
        }
        for (RelationRoomPro relation : relations) {
            String targetWeek = relation.getWeek();
            int targetStart = sTimeMap.get(relation.getStartNum());
            int targetEnd = sTimeMap.get(relation.getEndNum());
            if (targetWeek.equals(String.valueOf(week)) && targetStart <= minute && targetEnd >= minute) {
                return relation;
            }
        }
        return null;
    }
}
