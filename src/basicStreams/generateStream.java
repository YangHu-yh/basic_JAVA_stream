package basicStreams;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
/*  ww w  .ja  v  a 2s  . c  om*/
public class generateStream {
    public static void randomGenerate1(){
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

    }

    public static void generateLocalMethod(){
        Stream.generate(generateStream::next)
                .limit(5)
                .forEach(System.out::println);

    }

    static int i=0;
    private static int next(){
        i = i+1+i*i;
        return i;
    }

    public static void randomGenerate2(){
        new Random().ints()
                .filter(n -> n < 10000 && n > 0)
                .limit(5)
                .forEach(System.out::println);

    }

    public static void randomGenerate3(){
        IntStream.generate(new Random()::nextInt)
                .filter(n -> n < 10000 && n > 0)
                .limit(5)
                .forEach(System.out::println);
    }

    public static void generateZeros(){
        IntStream.generate(() -> 0)
                .limit(10)
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        System.out.println("Random number generated:");
        randomGenerate1();
        System.out.println("Generate number using locally defined function:");
        generateLocalMethod();
        System.out.println("Generate random integers:");
        randomGenerate2();
        System.out.println("Randomly generate integers with IntStream");
        randomGenerate3();
        System.out.println("Generate a stream of zeros");
        generateZeros();
    }
}