package basicStreams;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

//from  w  w  w  . j  ava  2 s .co  m
public class employeeExample {
    public static void femaleIncrease(List<Employee> persons){
        persons.stream()
                .filter(Employee::isFemale)
                .forEach(p -> p.setIncome(p.getIncome() * 1.10));
    }

    public static void poorIncrease(List<Employee> persons){
        persons.stream()
                .filter(p -> p.getIncome()<2000)
                .forEach(p -> p.setIncome(p.getIncome() * 1.20));
    }

    private static void higherIncomeNames(int lowerLimit) {
        Employee.persons()
                .stream()
                .filter(Employee::isMale)  // two filters can be combined as .filter(p -> p.isMale && p.getIncome() > lowerLimit)
                .filter(p ->  p.getIncome() > lowerLimit)
                .map(Employee::getName)
                .forEach(System.out::println);
    }

    public static void sumAllIncomeExpenses(){
        double sum = Employee.persons()
                .stream()
                // or more precisely use: .mapToDouble(Employee::getIncome)
                .map(Employee::getIncome)
                .reduce(0.0, Double::sum);
        System.out.println("The total expenses on employees income: "+sum+".\n");
    }

    public static void reduceMapExample(){
        System.out.println("Finding sum of all Income Expenses in sequential stream:\n");
        double sum = Employee
                .persons()
                .stream()
                .reduce(
                        0.0,
                        (Double partialSum, Employee p) -> {
                            double accumulated = partialSum + p.getIncome();
                            System.out.println(Thread.currentThread().getName()
                                    + "  - Accumulator: partialSum  = " + partialSum
                                    + ",  person = " + p + ", accumulated = " + accumulated);
                            return accumulated;
                        },
                        (a, b) -> {
                            double combined = a + b;
                            System.out.println(Thread.currentThread().getName()
                                    + "  - Combiner:  a  = " + a + ", b  = " + b
                                    + ", combined  = " + combined);
                            return combined;
                        });
        System.out.println("Total is:"+sum+".\n");

        System.out.println("Finding sum of all Income Expenses in parallel stream:\n");
        sum = Employee
                .persons()
                .parallelStream()
                .reduce(
                        0.0,
                        (Double partialSum, Employee p) -> {
                            double accumulated = partialSum + p.getIncome();
                            System.out.println(Thread.currentThread().getName()
                                    + "  - Accumulator: partialSum  = " + partialSum
                                    + ",  person = " + p + ", accumulated = " + accumulated);
                            return accumulated;
                        },
                        (a, b) -> {
                            double combined = a + b;
                            System.out.println(Thread.currentThread().getName()
                                    + "  - Combiner:  a  = " + a + ", b  = " + b
                                    + ", combined  = " + combined);
                            return combined;
                        });
        System.out.println("Total is:"+sum+".\n");
    }

    public static void highestEarnerByOptional() {
        Optional<Employee> person = Employee
                .persons()
                .stream()
                .reduce((p1, p2) -> p1.getIncome() > p2.getIncome() ? p1 : p2);
                // or use maxa() methods of the specific stream (apply to min() too)
//                 .max(Comparator.comparingDouble(Employee::getIncome));

        if (person.isPresent()) {
            System.out.println("Highest earner: " + person.get());
        } else {
            System.out.println("Could not  get   the   highest earner.");

        }

        // or use max() method of the DOubleStream
        OptionalDouble income =
                Employee.persons()
                        .stream()
                        .mapToDouble(Employee::getIncome)
                        .max();
        if (income.isPresent()) {
            System.out.println("Highest income:   " + income.getAsDouble());
        } else {
            System.out.println("Could not  get   the   highest income.");
        }
    }

    public static void countNumberOfEmployees(){
        long personCount = Employee.persons().stream().count();
        System.out.println("Number of employees: " + personCount);

        // or use map()
        personCount = Employee.persons()
                .stream()
                .mapToLong(p ->  1L)
                .sum();
        System.out.println("Use map() to count number of employees:");
        System.out.println(personCount);

        // or use map() and reduce()
        personCount = Employee.persons()
                .stream()
                .map(p  ->  1L)
                .reduce(0L,  Long::sum);
        System.out.println("Use map() and reduce() to count number of employees:");
        System.out.println(personCount);

        // or use reduce()
        personCount = Employee.persons()
                .stream()
                .reduce(0L, (partialCount,  person) ->  partialCount + 1L,  Long::sum);
        System.out.println("Use reduce() to count number of employees:");
        System.out.println(personCount);
    }

    public static void employeeStatistics(){
        DoubleSummaryStatistics incomeStats = Employee.persons()
                .stream()
                .map(Employee::getIncome)
                .collect(DoubleSummaryStatistics::new,
                        DoubleSummaryStatistics::accept,
                        DoubleSummaryStatistics::combine);

                // or use Collectors.summarizingDouble instead of .map(...).collect(...);
//                .collect(Collectors.summarizingDouble(Employee::getIncome));
        System.out.println(incomeStats);
    }

    public static void groupByGenderDob(){
        Map personsByGenderAndDobMonth
                = Employee.persons()
                .stream()
                .collect(Collectors.groupingBy(Employee::getGender,
                        Collectors.groupingBy(p ->  p.getDob().getMonth(),
                                Collectors.mapping(Employee::getName,Collectors.joining(" & ")))));

        System.out.println(personsByGenderAndDobMonth);
    }

    public static void groupByGenderIncomeStats(){    Map<Employee.Gender, DoubleSummaryStatistics>  incomeStatsByGender = Employee.persons()
            .stream()//from  w  w  w . ja  v a 2  s  .c om
            .collect(Collectors.groupingBy(Employee::getGender, Collectors.summarizingDouble(Employee::getIncome)));

        System.out.println(incomeStatsByGender);
    }

    public static void main(String[] args) {
        List<Employee> persons = Employee.persons();
        System.out.println("Before increasing the income:\n" + persons);
        femaleIncrease(persons);
        System.out.println("After increasing the income:\n" + persons);
        poorIncrease(persons);
        System.out.println("After increase income again:\n" + persons);

        System.out.println("Employees with income > 5000:\n");
        higherIncomeNames(5000);

        sumAllIncomeExpenses();

        reduceMapExample();

        System.out.println("Find the highest earner by 'Optional':");
        highestEarnerByOptional();

        countNumberOfEmployees();

        System.out.println("\nStatistics of employees:");
        employeeStatistics();

        System.out.println("\nGrouping by gender, then group by dob:");
        groupByGenderDob();

        System.out.println("\nGrouping by gender, then show income stats:");
        groupByGenderIncomeStats();
    }
}





class Employee {
    public static enum Gender {
        MALE, FEMALE
    }

    private long id;
    private String name;
    private Gender gender;
    private LocalDate dob;
    private double income;

    public Employee(long id, String name, Gender gender, LocalDate dob,
                    double income) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.income = income;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean isMale() {
        return this.gender == Gender.MALE;
    }

    public boolean isFemale() {
        return this.gender == Gender.FEMALE;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public static List<Employee> persons() {
        Employee p1 = new Employee(1, "Jake", Gender.MALE, LocalDate.of(1971,
                Month.JANUARY, 1), 2343.0);
        Employee p2 = new Employee(2, "Jack", Gender.MALE, LocalDate.of(1972,
                Month.JULY, 21), 7100.0);
        Employee p3 = new Employee(3, "Jane", Gender.FEMALE, LocalDate.of(1973,
                Month.MAY, 29), 5455.0);
        Employee p4 = new Employee(4, "Jode", Gender.MALE, LocalDate.of(1974,
                Month.OCTOBER, 16), 1800.0);
        Employee p5 = new Employee(5, "Jeny", Gender.FEMALE, LocalDate.of(1975,
                Month.DECEMBER, 13), 1234.0);
        Employee p6 = new Employee(6, "Jason", Gender.MALE, LocalDate.of(1976,
                Month.JULY, 9), 3211.0);

        List<Employee> persons = Arrays.asList(p1, p2, p3, p4, p5, p6);

        return persons;
    }

    @Override
    public String toString() {
        String str = String.format("(%s, %s,  %s,  %s,  %.2f)\n", id, name, gender,
                dob, income);
        return str;
    }
}