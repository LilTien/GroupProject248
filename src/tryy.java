import java.util.Date;
import java.util.Scanner;

public class tryy {
    public static void main(String[] args) {


        System.out.println(interpret("G()(al)"));


    }
    public static String interpret(String command) {
        String ans = command.replace("()", "o");
        char first , second = ')';
        ans = ans.replace(Character.toString('('), "");
        ans = ans.replace(Character.toString(')'), "");
        return ans;
    }
}
