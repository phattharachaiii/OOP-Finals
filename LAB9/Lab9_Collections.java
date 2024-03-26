package LAB9;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Lab9_Collections {
    static Employee yindee = new Employee("yindee");
    static Employee preeda = new Employee("preeda");
    static Employee pramote = new Employee("pramote");
    public static void main(String[] args) {
        // ex1();
        // ex2();
        // ex3();
        // q6();
        // q7();
        // q8();
        // q9();
        // q10();
        // q11();
        q12();
        q13();
        q14();
        q15();
    }

    static void ex1() {
        // hasCode() vs System.identityHashCode()
        Employee emp1 = new Employee("yindee");
        Employee emp2 = new Employee("yindee");
        int id1 = emp1.hashCode();
        int id2 = emp2.hashCode();
        String s1 = Integer.toHexString(id1);
        String s2 = Integer.toHexString(id2);
        System.out.println(s1);
        System.out.println(s2 + " " + s1.compareTo(s2));
    }

    static void ex2() {
        // java creates a copy of the reference when
        // an obj is passed to a method.
        // changes to an obj will be effected.
        Employee emp1 = new Employee("preeda");
        System.out.println(emp1);
        ex2_1(emp1);
        System.out.println(emp1);
    }

    private static void ex2_1(Employee e) {
        e.setName("aba abi abo abe");
    }

    static void ex3() {
        // changes to an obj will be effected when
        // passing as a parameter but not a collection
        ArrayList<Employee> singers = new ArrayList<>();
        singers.add(new Employee("aba"));
        singers.add(new Employee("abi"));
        singers.add(new Employee("abo"));
        singers.add(new Employee("abe"));
        ex3_1(singers);
        System.out.println(singers.get(0));
        // cha cha cha
        System.out.println(singers);
        // not empty because it's list who changes
    }

    private static void ex3_1(List<Employee> list) {
        list.get(0).setName("cha cha cha");
        // changes takes effect
        list = new ArrayList<>();
        // changes takes "no" effect on collection
    }

    static void ex4() {
        // what if we do need to change the referenced
        // object to another
        ArrayList<Employee> singers = new ArrayList<>();
        singers = ex4_1();
        System.out.println(singers);
    }

    private static ArrayList<Employee> ex4_1( /*
                                               * List<Employee> list
                                               */ ) {
        ArrayList<Employee> list = new ArrayList<>();
        // whether list is passed in or newly allocated,
        // it's a local reference.
        list.add(new Employee("aba"));
        list.add(new Employee("abi"));
        return (ArrayList<Employee>) list;
    }

    static void ex5() {
        // collection clone is shallow copy
        ArrayList<Employee> singers = new ArrayList<>();
        singers.add(new Employee("aba"));
        singers.add(new Employee("abi"));
        singers.add(new Employee("abo"));
        singers.add(new Employee("abe"));
        @SuppressWarnings("unchecked") // by clone()
        List<Employee> tmp = (ArrayList<Employee>) singers.clone();
        tmp.get(0).setName("cha");
        System.out.println(singers);
        // by new ArrayList<>();
        tmp = new ArrayList<>(singers);
        tmp.get(1).setName("cha cha");
        System.out.println(singers);
        // by subList()
        tmp = singers.subList(0, singers.size() - 1);
        tmp.get(2).setName("cha cha cha");
        System.out.println(tmp);
    }

    static void q6() {
        List<Employee> singers1 = new ArrayList<Employee>(
                Arrays.asList(new Employee("aba"), new Employee("abi")));
        // Arrays.asList() returns fixed sized ...but
        // ArrayList is not fixed, hence can call addAll()
        List<Employee> singers2 = new ArrayList<>();
        singers2.add(new Employee("abo"));
        singers2.add(new Employee("abe"));
        List<Employee> join = q6_1(singers1, singers2);
        System.out.println(singers1);
        System.out.println(singers2);
        System.out.println(join);
        singers1.addAll(singers2);
        System.out.println(singers1);
    }

    private static List<Employee> q6_1(List<Employee> l1, List<Employee> l2) {
        ArrayList<Employee> list = new ArrayList<>();
        list.addAll(l1);
        list.addAll(l2);
        return list;
    }

    static void q7() {
        List<Employee> singers = Arrays.asList(new Employee("aba"), new Employee("abi"));
        System.out.println(singers);
        singers = q7_1(singers);
        System.out.println(singers);
    }

    private static List<Employee> q7_1(List<Employee> list) {
        return new ArrayList<Employee>(Arrays.asList(list.get(0)));
    }

    static void q8() {
        // q8 - q11 yindee preeda pramote from Lab9_xxyyyy
        List<Employee> list1 = Arrays.asList(yindee, pramote);
        List<Employee> list2 = Arrays.asList(pramote, preeda);
        Set<Employee> empSet = new HashSet<>(list1); //ไม่ให้มีตัวซ้ำ
        empSet.addAll(list2);
        List<Employee> q8_ans = new ArrayList<>(empSet);
        System.out.println(q8_ans);
    }

    static void q9() {
        Set<Employee> empSet1 = new HashSet<>(Arrays.asList(yindee, preeda, pramote));
        Set<Employee> empSet2 = new HashSet<>(Arrays.asList(yindee, pramote));
        empSet1.retainAll(empSet2);//ที่มีตัวซ้ำจาก2Set
        System.out.println(empSet1);
    }

    static void q10() {
        Set<Employee> empSet1 = new HashSet<>(Arrays.asList(yindee, preeda, pramote));
        Set<Employee> empSet2 = new HashSet<>(Arrays.asList(yindee, pramote));
        empSet1.removeAll(empSet2);//เก็บเฉพาะที่อยู่ในempSet1ไม่อยู่ในEmpset2
        System.out.println(empSet1);
    }

    static void q11() {
        Set<Employee> empSet = new HashSet<>(Arrays.asList(yindee, preeda, pramote, preeda));
        Employee[] q11_ans = new Employee[empSet.size()];
        empSet.toArray(q11_ans);
        for (Employee e : q11_ans) {
            System.out.print(e + " ");
            System.out.println();
        }
    }

    static void q12() {
        int n = 3;
        Map<String, Employee> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(TestCollections.empList.get(i).getName(), TestCollections.empList.get(i));
        }
        System.out.println(map);
    }

    static void q13() {
        int n = 3;
        Map<String, Employee> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(TestCollections.empList.get(i).getName(), TestCollections.empList.get(i));
        }
        ArrayList<String> keySet = new ArrayList<>();
        keySet.addAll(map.keySet());
        System.out.println(keySet);
    }

    static void q14() {
        PriorityQueue<Employee> pq = new PriorityQueue<>((e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()));
        pq.addAll(TestCollections.empList);
        List<Employee> q14_ans = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            q14_ans.add(pq.poll());
        }
        System.out.println(q14_ans);
    }

    static void q15() {
        int sum = 0;
        int cnt = 0;
        Iterator<Employee> it;
        Long pqStart = System.nanoTime(); // System.currentTimeMillis();
        sum = 0;
        PriorityQueue<Employee> pq = new PriorityQueue<>((e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()));
        pq.addAll(TestCollections.empList);
        it = pq.iterator();
        while (it.hasNext()) {
            sum += it.next().getSalary();
            cnt++;
        }
        System.out.println("PQ (" + sum + ") takes "
                + String.format("%,d", System.nanoTime() - pqStart));
        Long listStart = System.nanoTime();
        ArrayList<Employee> clone = new ArrayList<>();
        sum = 0;
        clone.addAll(TestCollections.empList);
        Collections.sort(clone, (e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()));
        it = clone.iterator();
        while (it.hasNext()) {
            sum += it.next().getSalary();
            cnt--;
        }
        System.out.println("ArrayList (" + sum + ") takes "
                + String.format("%,d", System.nanoTime() - listStart));
        if (cnt != 0)
            System.out.println("error on number of elements");
    }
    //sort faster than PQ 
}

class TestCollections {
    static ArrayList<Employee> empList;
    static {
        empList = new ArrayList<>();

        try (Scanner input = new Scanner(Paths.get("names.csv"))) {
            input.nextLine(); // skip first row
            while (input.hasNext()) {
                String row = input.nextLine().trim();
                empList.add(new Employee(row));
            }
            System.out.println("There are " + empList.size() + " employees.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
