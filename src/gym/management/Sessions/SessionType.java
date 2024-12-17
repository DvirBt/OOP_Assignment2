package gym.management.Sessions;

public class SessionType {

    public static final String Pilates = "Pilates";
    public static final String MachinePilates = "MachinePilates";
    public static final String ThaiBoxing = "ThaiBoxing";
    public static final String Ninja = "Ninja";

    private String name;
    private int cost;
    private int capacity;

    public SessionType()
    {
        name = "";
        cost = 0;
        capacity = 0;
    }

    public SessionType(String name, int cost, int capacity)
    {
        this.name = name;
        this.cost = cost;
        this.capacity = capacity;
    }

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
