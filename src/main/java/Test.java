import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String[] args) {

        Date date = new Date();

        System.out.println(dataToCalendar(date));//打印测试
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf.format(calendarToData(dataToCalendar(date)));
        System.out.println(s);//打印测试
    }
    public static Calendar dataToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 35);
        return calendar;
    }

    public static Date calendarToData(Calendar calendar) {
        Date date = calendar.getTime(); // 从一个 Calendar 对象中获取 Date 对象
        return date;
    }

}
