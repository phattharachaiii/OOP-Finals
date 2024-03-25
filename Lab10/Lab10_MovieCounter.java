package Lab10;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Lab10_MovieCounter {
    public static void main(String[] args) {
        MovieCounter mc = new MovieCounter();
        mc.q1();
        mc.q2();
        mc.q3();
        mc.q4();
        mc.q5();
        mc.q6();
        mc.q7();
        mc.q8();
        mc.q9();
        mc.hint10();
        mc.hint11();
    }
}

class MovieCounter {
    ArrayList<CSMovie> mList = new ArrayList<>();
    Set<String> uniqueTitle = new HashSet<>();

    MovieCounter() {
        String row;
        int rowCount = 1;
        int incompleteCount = 0;
        String title;
        String rating;
        String genre;
        Integer year;
        String skipped_released;
        Double score;
        Integer vote;
        String director;
        String skipped_writer;
        String star;
        String country;
        Integer budget;
        Long gross;
        String company;
        Integer runtime;
        try (Scanner input = new Scanner(Paths.get("Lab10/movies.csv"))) {
            input.nextLine();
            while (input.hasNext()) {
                row = input.nextLine();
                rowCount++;
                String[] token = row.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (token.length < 15) {
                    incompleteCount++;
                    System.out.println(rowCount + " " + incompleteCount + " is incomplete");
                    continue;
                }
                title = token[0];
                rating = token[1];
                genre = token[2];
                year = Integer.parseInt(token[3]);
                score = Double.parseDouble(parseDouble(token[5]));
                vote = (int) (Double.parseDouble(parseDouble(token[6])));
                director = token[7];
                star = token[9];
                country = token[10];
                budget = (int) (Double.parseDouble(parseDouble(token[11])));
                gross = (long) (Double.parseDouble(parseDouble(token[12])));
                company = token[13];
                runtime = (int) (Double.parseDouble(parseDouble(token[14])));

                if (!uniqueTitle.contains(title)) {
                    mList.add(new CSMovie(title, rating, genre, year, score,
                            vote, director, star, country, budget,
                            gross, company, runtime));
                }
                uniqueTitle.add(title);
            }
            System.out.println("There are " + incompleteCount + " rows of incompleted data");
            System.out.println("From " + rowCount + " rows. (" + uniqueTitle.size() + ") unique titles.");
        } catch (IOException e) {
            System.out.println("From IO error ");
            e.printStackTrace();
        }
    }

    private String parseDouble(String str) {
        if (str.isEmpty())
            return ".0";
        return str;
    }

    void q1() {
        System.out.println("----- < Q1 > -----");
        // Double avgscore = (mList.stream().mapToDouble(e -> e.getScore()).sum()) /
        // mList.size();
        // System.out.println(avgscore);
        mList.stream()
                .mapToDouble(CSMovie::getScore)
                .average()
                .ifPresent(System.out::println); // เป็นOptionalจึงใช้ifpresentในการแสดงผล
    }

    void q2() {
        System.out.println("----- < Q2 > -----");
        mList.stream()
                .filter(e -> e.getVotes() > 1900000)
                .map(CSMovie::getTitle) // method referance
                .forEach(System.out::println);
        // .map(e -> e.getTitle()) //ramda
        // .forEach(System.out::println);
    }

    void q3() {
        System.out.println("----- < Q3 > -----");
        mList.stream()
                .max(Comparator.comparing(CSMovie::getGross))
                .map(grossmovie -> grossmovie.getTitle()) // เอาแค่ชื่อจากgrossที่มากที่สุด
                .ifPresent(System.out::println);
    }

    void q4() {
        System.out.println("----- < Q4 > -----");
        mList.stream()
                .map(CSMovie::getGenre)
                // .map(e -> e.getGenre())
                .distinct()// ไม่เอาอันซ้ำ
                .forEach(System.out::println);
    }

    void q5() {
        System.out.println("----- < Q5 > -----");
        mList.stream()
                // .sorted(Comparator.comparing(CSMovie::getRuntime).reversed())
                // //เรียงจากมากไปน้อย โดยใช้reverse
                .sorted(Comparator.comparing(CSMovie::getRuntime)) // เรียงจากน้อยไปมาก
                .limit(5) // 5อันดับแรก
                .map(e -> String.format("%-55s --> %s", e.getTitle(), e.getRuntime())) // เอาแค่Title และruntime
                .forEach(System.out::println);// แสดงผล

    }

    void q6() {
        System.out.println("----- < Q6 > -----");
        mList.stream()
                .max(Comparator.comparing(CSMovie::getBudget)) // หาCSmovie ที่มีgetBudget มากที่สุด
                .map(e -> String.format("%-55s --> %s", e.getTitle(), e.getBudget())) // แสดงผลTitle กับ budget
                .ifPresent(System.out::println); // แสดงผล

        mList.stream()
                .min(Comparator.comparing(CSMovie::getBudget))// หาCSmovie ที่มีgetBudget น้อยที่สุด
                .map(e -> String.format("%-55s --> %s", e.getTitle(), e.getBudget()))
                .ifPresent(System.out::println);
    }

    void q7() {
        System.out.println("----- < Q7 > -----");
        Map<String, List<CSMovie>> movieByGenre = mList.stream().collect(Collectors.groupingBy(CSMovie::getGenre)); // สร้างkeyจากGenreโดยใช้map
        movieByGenre.forEach((keyGenre, valueMovie) -> {
            System.out.println(keyGenre);
            // เรียงลำดับภาพยนตร์ในแต่ละ genre ตามคะแนน (score) จากมากไปน้อย
            valueMovie.stream()
                    .sorted(Comparator.comparing(CSMovie::getScore).reversed())
                    .limit(3)
                    .forEach(movie -> System.out.println(movie.getTitle() + " - Score:  " + movie.getScore()));
            // .map(e -> String.format("Movie name = %-20s ---> Score =
            // %s",e.getTitle(),e.getScore()))
            // .forEach(System.out::println);
        });
    }

    void q8() {
        System.out.println("----- < Q8 > -----");
        mList.stream()
                .filter(m -> m.getGenre().equals("Action"))
                .sorted(Comparator.comparing(CSMovie::getScore).reversed().thenComparing(CSMovie::getTitle))
                .limit(3)
                .forEach(movie -> System.out.println(movie.getTitle() + " - Score:  " + movie.getScore()));
    }

    void q9() {
        System.out.println("----- < Q9 > -----");
        mList.stream()
                .collect(Collectors.groupingBy(CSMovie::getGenre, // เก็บโดยgenre กับ gross
                        Collectors.summingLong(CSMovie::getGross)))
                .forEach((genre, totalGross) -> System.out.println(String.format("%-20s %s", genre, totalGross)));

    }

    void hint10() {
        System.out.println("----- < Q10 > -----");
        Map<String, Long> moviesByCompany = mList.stream()
                .collect(Collectors.groupingBy(CSMovie::getCompany, Collectors.counting()));
        moviesByCompany.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> System.out.println(String.format("%-30s %s",
                        entry.getKey(), entry.getValue())));
    }

    void hint11() {
        System.out.println("----- < Q11 > -----");
        Function<String, Integer> numWords = entry -> {
            int count = 0;
            String[] tokens = entry.split("");
            for (String string : tokens) {
                if (string.charAt(0) == 'a')
                    count++;
            }
            return count;
        };
        Optional<String> opt = mList.stream().map(e1 -> e1.getTitle()).max(Comparator.comparing(numWords));
        System.out.println(opt.get());
    }
}
