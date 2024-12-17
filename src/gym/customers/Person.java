package gym.customers;

import javax.swing.*;
import java.time.LocalDate;
import java.time.Month;

public class Person {

    private static int ID = 1111;
    private int id = ID++;
    private String name;
    private int balance;
    private final Gender gender;
    private final String birthday;

    public Person(String name, int balance, Gender gender, String birthday)
    {
        this.name = name;
        this.balance = balance;
        this.gender = gender;
        this.birthday = birthday;
    }

    public Person(int id, String name, int balance, Gender gender, String birthday)
    {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.gender = gender;
        this.birthday = birthday;
    }

    public Person(Person p)
    {
        this(p.id, p.name, p.balance, p.gender, p.birthday);
    }

    public int getId()
    {
        return this.id;
    }
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getBalance()
    {
        return this.balance;
    }

    public void setBalance(int balance)
    {
        this.balance = balance;
    }

    public Gender getGender()
    {
        return this.gender;
    }

    public String getBirthday()
    {
        return this.birthday;
    }

    private Month getMonth(int month)
    {
        switch (month)
        {
            case 1: return Month.JANUARY;
            case 2: return Month.FEBRUARY;
            case 3: return Month.MARCH;
            case 4: return Month.APRIL;
            case 5: return Month.MAY;
            case 6: return Month.JUNE;
            case 7: return Month.JULY;
            case 8: return Month.AUGUST;
            case 9: return Month.SEPTEMBER;
            case 10: return Month.OCTOBER;
            case 11: return Month.NOVEMBER;
            case 12: return Month.DECEMBER;
            default: return null;
        }
    }
    public int getAge()
    {
        LocalDate localDate = LocalDate.now();
        int year = Integer.parseInt(birthday.substring(6,10));
        int month = Integer.parseInt(birthday.substring(3, 5));
        int day = Integer.parseInt(birthday.substring(0,2));

        int age = localDate.getYear() - year - 1;
        if (localDate.getMonthValue() == month && localDate.getDayOfMonth() == day)
            age += 1;

        return age;

        //Month monthOfBirth = getMonth(month);
        //Date dateOfBirth = new Date(year, month, day); // month -> monthOfBirth


        //return (2024 - Integer.parseInt(this.getBirthday().substring(5,9)));
    }


    // Secretary functions

}
