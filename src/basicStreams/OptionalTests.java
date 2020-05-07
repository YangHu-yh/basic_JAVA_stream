package basicStreams;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/*from w ww.  jav  a 2s.c o  m*/
public class OptionalTests {

    public static void testBasics(){
        Optional<String> empty  = Optional.empty();
        System.out.println(empty);

        Optional<String> str = Optional.of("java2s.com");
        System.out.println(str);

        String nullableString = "";
        Optional<String> str2 = Optional.of(nullableString);
        System.out.println(str2);
    }

    public static void testIsPresent(){
        // when empty
        System.out.println("When empty Optional str:");
        Optional<String> str = Optional.empty();
        if (str.isPresent()) {
            String value = str.get();
            System.out.println("Optional contains " + value);
        } else {
            System.out.println("Optional is  empty.");
        }
        // test empty in short
        System.out.println("In short form");
        str.ifPresent(value -> System.out.println("Optional contains " + value));

        // When not empty
        System.out.println("When no empty Optional str:");
        str = Optional.of("java2s.com");
        if (str.isPresent()) {
            String value = str.get();
            System.out.println("Optional contains " + value);
        } else {
            System.out.println("Optional is  empty.");
        }
        // test not empty in short
        System.out.println("In short form");
        str.ifPresent(value -> System.out.println("Optional contains " + value));
    }


    public static void testIntOptional(){
        OptionalInt number = OptionalInt.of(2);

        if (number.isPresent()) {
            int value = number.getAsInt();
            System.out.println("Number is " + value);
        } else {
            System.out.println("Number is absent.");
        }
    }

    public static void testOperationalEmptyCase(){
        OptionalInt maxOdd = IntStream.of(10, 20, 30, 31).filter(n -> n % 2 == 1).max();
        if (maxOdd.isPresent()) {
            int value = maxOdd.getAsInt();
            System.out.println("Maximum odd integer is " + value);
        } else {
            System.out.println("Stream is empty.");
        }
    }


    public static void main(String[] args) {
//        System.out.println("testBasics:");
//        testBasics();
//        System.out.println("testIsPresent:");
//        testIsPresent();
//        System.out.println("testIntOptional:");
//        testIntOptional();
        System.out.println("testOperationalEmptyCase:");
        testOperationalEmptyCase();
    }
}