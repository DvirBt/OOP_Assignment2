package gym.management.Sessions;

/**
 * This abstract class represents SessionType, which includes the types of sessions that the gym offers.
 */
public abstract class SessionType {

    // Static data members
    public static final String Pilates = "Pilates";
    public static final String MachinePilates = "MachinePilates";
    public static final String ThaiBoxing = "ThaiBoxing";
    public static final String Ninja = "Ninja";

    // Properties
    private String name;
    private int cost;
    private int capacity;

    // Constructor
    public SessionType(String name, int cost, int capacity)
    {
        this.name = name;
        this.cost = cost;
        this.capacity = capacity;
    }

    // Getters
    public String getName()
    {
        return name;
    }

    public int getCost()
    {
        return cost;
    }

    public int getCapacity()
    {
        return capacity;
    }
}
