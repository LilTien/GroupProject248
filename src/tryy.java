import java.util.Date;

public class tryy {
    public static void main(String[] args) {

        Date myDate = new Date(125,6,17);
        Date newDate = new Date();

        char hello = 'a';
        String concat = "";
        concat += hello;
        System.out.println(concat);

        /*int year = myDate.getYear() - newDate.getYear();
        int month = myDate.getMonth() - newDate.getMonth();
        int day = myDate.getDay() - newDate.getDay();
        System.out.println("Year : " + Math.abs(year));
        System.out.println("Month : " + Math.abs(month));
        System.out.println("Day : " + Math.abs(day));*/
    }
}