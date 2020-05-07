package basicStreams;

import java.util.Arrays;
import java.util.List;
/*from w  w  w.  j a va 2 s .co  m*/
public class OrderedList {

    public static void toOrder(List<Integer> numbers){
        numbers.stream().parallel()
                .sorted()
                .forEachOrdered(System.out::println);
    }

    public static void unOrder(List<Integer> numbers){
        numbers.stream().parallel()
                .sorted()
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3,7,9,3,1,2,1,2,3,4,5);
        System.out.println("To ordered list:");
        toOrder(numbers);
        System.out.println("Still not ordered and non-deterministic by parallel:");
        unOrder(numbers);
    }
}