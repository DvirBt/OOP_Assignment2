package gym.customers;

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

    public boolean isSenior()
    {
        //int year = (2024 - Integer.parseInt(this.getBirthday().substring(5,9)) - 2000);
        //int age = this.getAge();
        if (this.getAge() >= 65)
            return true;

        return false;
    }

    public void update(String message)
    {
        //System.out.println(message);
        notifications += message + ",";
    }

    public String getNotifications()
    {
        String print = this.getName() + " Notifications: [" + notifications;
        print = print.substring(0,print.length()-2) + "]"; // remove the ',' in the end
        return print;
    }
}
