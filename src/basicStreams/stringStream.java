package basicStreams;

import java.util.regex.Pattern;

/*from  w  w  w  .  jav a 2s.  c  o  m*/
public class stringStream {
    public static void string1(){
        String str = "5 123,123,qwe,1,123, 25";
        str.chars()
                .filter(n ->  !Character.isDigit((char)n) &&   !Character.isWhitespace((char)n) && n != ',')
                .forEach(n ->  System.out.print((char)n));
    }

    public static void string2(){
        String str = "XML,CSS,HTML";
        Pattern.compile(",")
                .splitAsStream(str)
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        string1();
        string2();
    }
}