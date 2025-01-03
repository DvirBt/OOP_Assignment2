package gym.customers;

import javax.swing.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;

/**
 * This class represent a Person with basic properties.
 */

public class Person {

    private static int ID = 1111;
    private int id = ID++;
    private String name;
    private final Bank bankAccount; // a bank account
    private final Gender gender;
    private final String birthday;

    public Person(String name, int balance, Gender gender, String birthday)
    {
        this.name = name;
        this.bankAccount = new Bank(id, balance);
        this.gender = gender;
        this.birthday = birthday;
    }

    public Person(int id, String name, Bank bankAccount, Gender gender, String birthday)
    {
        this.id = id;
        this.name = name;
        this.bankAccount = bankAccount;
        this.gender = gender;
        this.birthday = birthday;
    }

    public Person(Person p)
    {
        this(p.id, p.name, p.bankAccount, p.gender, p.birthday);
    }


    // Getters and Setters

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

    public Gender getGender()
    {
        return this.gender;
    }

    public String getBirthday()
    {
        return this.birthday;
    }

    public Bank getBankAccount()
    {
        return this.bankAccount;
    }

    public int getBankAccountBalance()
    {
        return this.bankAccount.getBalanceByID(id);
    }

    public void setBankAccountBalance(int balance)
    {
        this.bankAccount.setBalanceByID(id, balance);
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

    /**
     * A nested static class Bank.
     * This class is using as a connector between a Person balance to Client/Instructor/Secretary balance by his ID
     * by using a HasMap data structure.
     */
    public static class Bank
    {
        HashMap<Integer, Integer> bank = new HashMap<>();

        private Bank(int id, int balance)
        {
            bank.put(id, balance);
        }

        private int getBalanceByID(int id)
        {
            return bank.get(id);
        }

        private void setBalanceByID(int id, int balance)
        {
            bank.put(id, balance);
        }
    }
}
