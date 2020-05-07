package basicStreams;

import java.util.stream.Stream;
//  w ww.  jav  a  2s  .c  o  m
public class IterateStream {
    public static void iterateLong(){
        Stream<Long> tenNaturalNumbers = Stream.iterate(1L, n  ->  n*10)
                .limit(10);
        tenNaturalNumbers.map(n -> n-10000000L).filter(n -> n>0).forEach(System.out::println);
    }

    public static void useLocalMethod(){
        Stream.iterate(3L, n  ->  n+3)
                .filter(IterateStream::isOdd)
                .skip(100)
                .limit(10)
                .forEachOrdered(System.out::println);
    }
    public static boolean isOdd(long number) {
        if (number % 2 == 0) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        iterateLong();
        useLocalMethod();
    }
}