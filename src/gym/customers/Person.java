package gym.customers;

import javax.swing.*;
import java.time.LocalDate;
import java.time.Month;

/**
 * This class represent a Person with basic properties.
 */

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

    /**
     * This function calculates and returns the person's age.
     * @return the age of the person relate to this day.
     */
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
    }
}
