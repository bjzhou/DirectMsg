package org.bjzhou.directmsg.utils;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    @SuppressLint("SimpleDateFormat")
	@SuppressWarnings("deprecation")
	public static String formatDate(String datestr) {
		Date date = new Date(datestr);
        SimpleDateFormat sdf;
        Calendar now = Calendar.getInstance();
        Calendar msg = Calendar.getInstance();
        msg.setTime(date);
        
        if (now.get(Calendar.DAY_OF_YEAR) == msg.get(Calendar.DAY_OF_YEAR)) {
            sdf = new SimpleDateFormat("HH:mm");
        } else if (now.get(Calendar.YEAR) == msg.get(Calendar.YEAR)) {
            sdf = new SimpleDateFormat("M月d日");
        } else {
            sdf = new SimpleDateFormat("yyyy年M月d日");
        }
        return sdf.format(date);
        
    }

}
