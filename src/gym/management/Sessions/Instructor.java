package gym.management.Sessions;
import gym.customers.Person;

import java.util.ArrayList;

/**
 * This class represents an Instructor in the Gym.
 */
public class Instructor extends Person {

    private int salary;
    private ArrayList<String> types = new ArrayList<>();

    public Instructor(Person p, int salary, ArrayList<String> types)
    {
        super(p);
        this.salary = salary;
        this.types = new ArrayList<>();
        this.types.addAll(types);
    }

    // Getters and Setters
    public int getSalary()
    {
        return this.salary;
    }

    public void setSalary(int salary)
    {
        this.salary = salary;
    }

    public ArrayList<String> getTypes()
    {
        return this.types;
    }

    public void setTypes(ArrayList<String> types)
    {
        this.types = new ArrayList<>();
        this.types.addAll(types);
    }

    /**
     * This function pays the instructor by adding his current salary to his balance.
     */
    public void paySalary()
    {
        setBalance(getBalance() + salary);
    }
}
