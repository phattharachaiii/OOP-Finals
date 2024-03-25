package LAB8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

interface HalfValueInterface {
    public abstract void printHalf(int n);
}

public class lab8 {
    public static void main(String[] args) {
        q1_halfEachNumber();
    }

    public static void q1_halfEachNumber() {
        List<Integer> nums = Arrays.asList(100, 105);
        HalfValueInterface q0 = new HalfValueInterface() {
            @Override
            public void printHalf(int n) {
                System.out.println(n / 2);
            }
        };
        for (int n : nums) {
            q0.printHalf(n);
        }
        HalfValueInterface halfVal = n -> System.out.println(n / 2);
        for (int n : nums) {
            halfVal.printHalf(n);
        }
        Consumer<Integer> consumer = n -> System.out.println(n / 2);
        for (int n : nums) {
            consumer.accept(n);
        }

        Consumer<Integer> halfMe = n -> System.out.println(n / 2);
        nums.forEach(halfMe);
        nums.forEach(n -> System.out.println(n / 2));

        NumberProcessor np = new NumberProcessor();
        nums.forEach(np::printHalf);
    }


}

class NumberProcessor {
    void printHalf(int n) {
        System.out.println(n / 2);
    }
}
