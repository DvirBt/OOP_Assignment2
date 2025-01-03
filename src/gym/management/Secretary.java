package gym.management;

/**
 * This class represents the Secretary of the Gym.
 * Only the current Secretary is allowed to make actions and changes.
 * Therefore, in each main method, the program checks if 'This' object is the current gym's secretary.
 */

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InstructorNotQualifiedException;
import gym.Exception.InvalidAgeException;
import gym.Gym;
import gym.customers.Person;
import gym.customers.Client;
import gym.management.Notification.NotificationService;
import gym.management.Sessions.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Secretary extends Person {

    // Singleton and data members
    private static Secretary secretary = null;
    private int salary;
    private Gym gym = null;
    private final SessionFactory sessionFactory = new SessionFactory();

    // Private constructor
    private Secretary(Person p, int salary)
    {
        super(p);
        this.salary = salary;
        this.gym = Gym.getInstance();
    }

    // Create a new instance
    public static Secretary getInstance(Person p, int salary)
    {
        secretary = new Secretary(p, salary);
        return secretary;
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

    // Functions

    /**
     * This function registers a person if he meets the requirements of the gym.
     * @param client - a person that might be a potential client.
     * @return the new client if he meets the requirements. Otherwise, throws the relevant exception.
     * @throws InvalidAgeException - if the person is under 18 years old.
     * @throws DuplicateClientException - if the person is already a client.
     */
    public Client registerClient(Person client) throws InvalidAgeException, DuplicateClientException
    {
        currentSecretary();
        // client is too young
        if (client.getAge() < 18)
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");

        // already exists
        for (int i = 0; i < gym.getClients().size(); i++)
            if (gym.getClients().get(i).getId() == client.getId())
                throw new DuplicateClientException("Error: The client is already registered");

        // register
        Client newClient = new Client(client);
        gym.addClient(newClient);
        String print = "Registered new client: " + client.getName();
        addToHistory(print);
        return newClient;
    }

    /**
     * This function unregisters a client if he belongs to the gym.
     * @param client - a client to unregister.
     * @throws ClientNotRegisteredException - if the client is not registered to the gym.
     */
    public void unregisterClient(Client client) throws ClientNotRegisteredException
    {
        currentSecretary();
        // client is not registered to the gym
        if (!gym.getClients().contains(client))
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");

        // exists -> proceed to remove
        gym.getClients().remove(client);
        String print = "Unregistered client: " + client.getName();
        addToHistory(print);
   }

    /**
     * This function creates and returns a new session.
     * @param sessionType - the type of the session.
     * @param date - the date of the session.
     * @param forumType - the forum type of the session.
     * @param instructor - the instructor of the session.
     * @return a new session if all the given data members are valid. Otherwise, throws the relevant error.
     * @throws InstructorNotQualifiedException - if the instructor is not qualified to teach this session type.
     */
    public Session addSession(String sessionType, String date, ForumType forumType, Instructor instructor) throws InstructorNotQualifiedException
    {
        currentSecretary();
        // check if the instructor is qualified
        if (!validInstructor(sessionType, instructor))
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");

        // Factory
        SessionType type = sessionFactory.createSessionType(sessionType);

        Session session = new Session(type, date, forumType, instructor);
        gym.addSession(session);
        String print = "Created new session: " + type.getName() + " on " + printDate(date) + " with instructor: " + instructor.getName();
        addToHistory(print);

        return session;
    }

    /**
     * This function gets a String and returns it in a different format.
     * @param date - the String representing a date.
     * @return this date in a different format (YEAR-MONTH-DAYTHOURS:MINUTES)
     */
    private String printDate(String date)
    {
        String day = date.substring(0,2);
        String month = date.substring(3,6);
        String year = date.substring(6,10) + "-";
        String time = date.substring(11);

        return year+month+day+"T"+time;
    }

    /**
     * This function checks if a given instructor is qualified to teach a given session type.
     * @param sessionType - the type of the session.
     * @param instructor - the instructor that supposes to teach this session type.
     * @return true if the instructor can teach this session type. Otherwise, returns false.
     */
    private boolean validInstructor(String sessionType, Instructor instructor)
    {
        for (int i = 0; i < instructor.getTypes().size(); i++)
        {
            if (instructor.getTypes().get(i).equals(sessionType))
                return true;
        }

        return false;
    }

    /**
     * This function checks if an error occurs if a client will be registered to a session.
     * @param client - the client who's trying to register to the lesson.
     * @param session - the session.
     * @return true if at least one error occurs. Otherwise, returns false.
     */

    private boolean errors(Client client, Session session) {
        boolean error = false;
        // check date
        LocalDate currentDate = LocalDate.now();
        String date = session.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDate sessionDate = LocalDate.parse(date, formatter);

        if (sessionDate.isBefore(currentDate)) // missed the session
        {
            addToHistory("Failed registration: Session is not in the future");
            error = true;
        }

        // check forum
        ForumType forum = session.getForumType();
        if (!forum.getAudience().equals("All")) {
            if (forum.getAudience().equals("Seniors"))
            {
                if (!client.isSenior())
                {
                    addToHistory("Failed registration: Client doesn't meet the age requirements for this session (Seniors)");
                    error = true;
                }
            }

            else if (!forum.getAudience().equals(client.getGender().getGender()))
            {
                addToHistory("Failed registration: Client's gender doesn't match the session's gender requirements");
                error = true;
            }
        }

        // check if the session is full
        if (session.getClients().size() >= session.getSessionType().getCapacity())
        {
            addToHistory("Failed registration: No available spots for session");
            error = true;
        }

        // check if the client has enough money
        if (client.getBankAccountBalance() < session.getSessionType().getCost())
        {
            addToHistory("Failed registration: Client doesn't have enough balance");
            error = true;
        }

        return error;
    }

    /**
     * This function registers a client to a lesson if it is possible.
     * @param client - the client to register.
     * @param session - the session.
     * @throws ClientNotRegisteredException - the client is not registered to the Gym.
     * @throws DuplicateClientException - the client is already registered to the session.
     */
    public void registerClientToLesson(Client client, Session session) throws ClientNotRegisteredException, DuplicateClientException
    {
        currentSecretary();
        // client already registered to this lesson
        if (session.getClients().contains(client))
            throw new DuplicateClientException("Error: The client is already registered for this lesson");

        // if not registered to the gym
        if (!gym.getClients().contains(client) || client == null)
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");

        if (errors(client, session)) // if an error occurs
            return; // return to the main


        String print = "Registered client: " + client.getName() + " to session: " + session.getSessionType().getName() + " on " + printDate(session.getDate()) + " for price: " + session.getSessionType().getCost();
        addToHistory(print);

        // valid -> register to lesson
        session.addClient(client); // add the client
        gym.setBalance(gym.getBalance() + session.getSessionType().getCost()); // add to the gym balance
        client.setBankAccountBalance(client.getBankAccountBalance() - session.getSessionType().getCost()); // reduce the cost from the client
    }

    /**
     * This function hires an instructor.
     * @param person - the person who's going to be an instructor.
     * @param salary - his salary.
     * @param sessionTypes - the types of sessions that he is allowed to teach.
     * @return the new Instructor.
     */
    public Instructor hireInstructor(Person person, int salary, ArrayList<String> sessionTypes)
    {
        currentSecretary();
        Instructor instructor = new Instructor(person, salary, sessionTypes);
        gym.addInstructor(instructor);
        String print = "Hired new instructor: " + person.getName() + " with salary per hour: " + salary;
        addToHistory(print);
        return instructor;
    }

    /**
     * This function pays all the employees of the Gym.
     */
    public void paySalaries()
    {
        currentSecretary();
        // pay the secretary
        gym.setBalance(gym.getBalance()-gym.getSecretary().salary);
        gym.getSecretary().setBankAccountBalance(gym.getSecretary().getBankAccountBalance() + gym.getSecretary().getSalary());

        ArrayList<Session> needToPaySessions = gym.getSessions();
        // pay by sessions
        for (int i = 0; i < gym.getSessions().size(); i++)
        {
            int paycheck = needToPaySessions.get(i).getInstructor().getSalary();
            needToPaySessions.get(i).getInstructor().paySalary(); // pay to the instructor
            gym.setBalance(gym.getBalance() - paycheck);
        }

        String print = "Salaries have been paid to all employees";
        addToHistory(print);
    }

    // Using Observer

    /**
     * This function notifies a given message to all the clients of the session.
     * @param session - the session.
     * @param message - the message.
     */
    public void notify(Session session, String message)
    {
        currentSecretary();
        session.notifyClients(message);
        String print = "A message was sent to everyone registered for session " + session.getSessionType().getName() + " on " + printDate(session.getDate()) + " : " + message;
        addToHistory(print);
    }

    /**
     * This function checks if a session occurs at a given date.
     * @param session - the session.
     * @param date - the date.
     * @return true if the session occurs at that day. Otherwise, returns false.
     */
    private boolean sessionAtDate(Session session, String date)
    {
        String sessionDate = session.getDate().substring(0,10); // get only the date
        if (date.equals(sessionDate))
            return true;

        return false;
    }

    /**
     * This function notifies all the clients who are registered to a session at a given date.
     * @param date - the date.
     * @param message - the message.
     */
    public void notify(String date, String message)
    {
        currentSecretary();
        ArrayList<Session> dateSessions = new ArrayList<>();

        for (int i = 0; i < gym.getSessions().size(); i++)
        {
            Session currentSession = gym.getSessions().get(i);
            if (sessionAtDate(currentSession, date))
                dateSessions.add(currentSession);
        }

        if (!dateSessions.isEmpty())
        {
            for (int i = 0; i < dateSessions.size(); i++)
                dateSessions.get(i).notifyClients(message);
        }

        String newDate, day, month, year;
        day = date.substring(0,2);
        month = date.substring(3,6);
        year = date.substring(6,10) + "-";
        newDate = year+month+day;

        String print = "A message was sent to everyone registered for a session on " + newDate+ " : " + message;
        addToHistory(print);
    }

    /**
     * This function notifies all the clients of the Gym a given message.
     * @param message - the message.
     */
    public void notify(String message)
    {
        currentSecretary();
        new NotificationService(gym.getClients(), message);
        String print = "A message was sent to all gym clients: " + message;
        addToHistory(print);
    }

    /**
     * This function prints all the history actions that are done by all the secretaries.
     */
    public void printActions()
    {
        currentSecretary();
        for (int i = 0; i < gym.getHistory().size(); i++)
            System.out.println(gym.getHistory().get(i));
    }

    /**
     * This function checks if this object is the current secretary of the Gym.
     * @throws NullPointerException - if this secretary is not the current secretary of the Gym.
     */
    private void currentSecretary()
    {
        if (gym.getSecretary() != null)
            if (this.getId() != gym.getSecretary().getId())
                throw new NullPointerException("Error: Former secretaries are not permitted to perform actions");
    }

    /**
     * This function adds a given String to the history actions list.
     * @param addToHistory - a String to add to the history.
     */
    private void addToHistory(String addToHistory)
    {
        currentSecretary();
        gym.addHistory(addToHistory);
    }
}
