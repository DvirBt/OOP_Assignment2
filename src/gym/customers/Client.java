package gym.customers;

/**
 * This class represents a Client in the Gym.
 */
public class Client extends Person {

    private String notifications;
    public Client(int id, String name, int balance, Gender gender, String birthday)
    {
        super(id, name, balance, gender, birthday);
        notifications = "";
    }

    public Client(Person person)
    {
        this(person.getId(), person.getName(), person.getBalance(), person.getGender(), person.getBirthday());
    }


    /**
     * This function returns the client current age status.
     * @return true if the client's age is 65+. Otherwise, returns false.
     */
    public boolean isSenior()
    {
        if (this.getAge() >= 65)
            return true;

        return false;
    }

    /**
     * This function gets a text message and adds it to client's notifications.
     * @param message - a text message that the client received.
     */
    public void update(String message)
    {
        //System.out.println(message);
        notifications += message + ", ";
    }

    /**
     * This function returns all the notifications that this client received.
     * @return all the notifications.
     */
    public String getNotifications()
    {
        String print = "[" + notifications;
        print = print.substring(0,print.length()-2) + "]"; // remove the ' ,' in the end
        return print;
    }
}
