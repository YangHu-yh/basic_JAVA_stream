package basicStreams;

import java.util.Arrays;
import java.util.List;
/*from w  w  w.  j a va 2 s .co  m*/
public class ReduceSum {

    public static int reduceNorm(List<Integer> numbers){
        int sum = 0;
        for (int n : numbers) {
            if (n % 2 == 0) {
                int square = n * n;
                sum = sum + square;
            }
        }
        return sum;
    }

    public static int reduceStream(List<Integer> numbers){
        return numbers.stream()
                .filter(n -> n%2==0)
                .map(n -> n*n)
                .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(reduceNorm(numbers));
        System.out.println(reduceStream(numbers));
    }
}