package LAB8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class lab8_q2 {
    static List<Singer> list;
    static {
        list = new ArrayList<>();
        list.add(new Singer("aba", Style.POP));
        list.add(new Singer("abi", Style.ROCK));
        list.add(new Singer("abo", Style.ROCK));
        list.add(new Singer("abe", Style.POP));

    }

    public static void main(String[] args) {
        q2_forEachSingerName();
        q3_lambda_comparator();
        q4_method_reference_comparator();
    }

    public static void q2_forEachSingerName() {
        System.out.println("Q2.1--------");
        list.stream().map(name -> name.getName()).forEach(System.out::println);// 2.1
        System.out.println("Q2.2--------");
        list.stream().map(Singer::getName).forEach(System.out::println);// 2.2
    }

    public static void q3_lambda_comparator() {

        Comparator<Singer> byStyle1 = new Comparator<>() {
            @Override
            public int compare(Singer o1, Singer o2) {
                return o1.getStyle().compareTo(o2.getStyle());
            } // by Enum .ordinal()

        };
        Collections.sort(list, byStyle1);
        list.forEach(System.out::println);

        System.out.println("Q3----------");

        Comparator<Singer> byStyle2 = Comparator.comparing(n -> n.getStyleString());
        Collections.sort(list, byStyle2);
        list.forEach(System.out::println);
    }

    public static void q4_method_reference_comparator() {
        System.out.println("Q4.1--------");
        Comparator<Singer> byName = Comparator.comparing(Singer::getStyleString);
        Collections.sort(list, byName);
        list.forEach(System.out::println);
        System.out.println("Q4.2--------");
        list.sort(Comparator.comparing(n -> n.getStyleString()));
        list.forEach(System.out::println);

        // Collections.sort(list,Singer::compareByName);
        // list.forEach(System.out::println);

        // list.sort(Singer::compareByName);
        // list.forEach(System.out::println);
    }

}

class Singer {
    String name;
    Style style;

    Singer(String n, Style s) {
        name = n;
        style = s;
    }

    public Style getStyle() {
        return style;
    }

    public String getName() {
        return name;
    }

    public String getStyleString() {
        return style.toString();
    }

    public int compareByName(Singer s) {
        return name.compareTo(s.getName());
    }

    @Override
    public String toString() {
        return "Singer " + "(" + name + " - " + "SingStyle." + style + ")";
    }
}

enum Style {
    POP, ROCK
}
