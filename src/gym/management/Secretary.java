package gym.management;

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

/* TODO:
    1. Notify and Observation implementation
    2. Add history
    3. Compile and pray
    4. Adjust exceptions THROWABLE and CAUSE
    5. Add prints
 */

public class Secretary extends Person {

    //private final static Secretary secretary = null;
    private static Secretary secretary = null;
    private int id;
    private int salary;
    private Gym gym = null;
    private NotificationService notificationService;
    private final SessionFactory sessionFactory; // added

    private Secretary(Person p, int salary)
    {
        super(p);
        this.salary = salary;
        this.gym = Gym.getInstance();
        this.sessionFactory = new SessionFactory();
        notificationService = new NotificationService();
    }

    public static Secretary getInstance(Person p, int salary)
    {
        secretary = new Secretary(p, salary);
        return secretary;
    }

    public int getSalary()
    {
        return this.salary;
    }
    public void setSalary(int salary)
    {
        this.salary = salary;
    }
    private boolean validClient(Person client)
    {
        if (client.getAge() >= 18)
            return true;

        return false;
    }
    public Client registerClient(Person client) throws InvalidAgeException, DuplicateClientException
    {
        currentSecretary(secretary);
        // client is too young
        if (!validClient(client))
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");

        Client newClient = new Client(client);
        // already exists
        if (gym.getClients().contains(newClient))
            throw new DuplicateClientException("Error: The client is already registered");

        // register
        gym.addClient(newClient);
        String print = "Registered new client: " + client.getName();
        addToHistory(print);
        System.out.println(print);
        return newClient;
    }

    public void unregisterClient(Client client) throws ClientNotRegisteredException
    {
        if (!gym.getClients().contains(client))
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");

        // exists -> proceed to remove
        gym.getClients().remove(client);
        String print = "Unregistered client: " + client.getName();
        addToHistory(print);
        System.out.println();
    }

    public Session addSession(String sessionType, String date, ForumType forumType, Instructor instructor) throws InstructorNotQualifiedException
    {
        // check if the instructor is qualified
        if (!validInstructor(sessionType, instructor))
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");

        SessionType type = sessionFactory.createSessionType(sessionType);

        if (type == null) // EXCEPTION
            throw new NullPointerException("Session type is null!");

        Session session = new Session(type, date, forumType, instructor);
        gym.addSession(session);
        gym.addPayBySession(session); // add session to needToPay sessions
        String print = "Created new session: " + type.getName() + " on " + date.toString() + " with instructor: " + instructor.getName();
        addToHistory(print);
        System.out.println(print);
        //Created new session: Pilates on 2025-01-23T10:00 with instructor: Yuval

        return session;
    }

    private boolean validInstructor(String sessionType, Instructor instructor)
    {
        for (int i = 0; i < instructor.getTypes().size(); i++)
        {
            if (instructor.getTypes().get(i).equals(sessionType))
                return true;
        }

        return false;
    }

    /* TODO:
        1. Add date validation (based on what?)
     */
    private int validRegisterToLesson(Client client, Session session)
    {
        // check if the session is full
        if (session.getClients().size() >= session.getSessionType().getCapacity())
            return 1;

        // check if the client has enough money
        if (client.getBalance() < session.getSessionType().getCost())
            return 2;

        // check forum
        ForumType forum = session.getForumType();
        if (!forum.getAudience().equals("All"))
        {
            if (forum.getAudience().equals("Seniors"))
                if (!client.isSenior())
                    return 3;

            else if (!forum.getAudience().equals(client.getGender().getGender()))
                return 4;
        }

        // check date
        LocalDate currentDate = LocalDate.now();
        String date = session.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDate sessionDate = LocalDate.parse(date, formatter);

        if (sessionDate.isBefore(currentDate)) // missed the session
            return 5;

        // Ok -> return 0
        return 0;
    }
    public void registerClientToLesson(Client client, Session session) throws ClientNotRegisteredException, DuplicateClientException
    {
        currentSecretary(secretary);
        // client already registered to this lesson
        if (session.getClients().contains(client))
            throw new DuplicateClientException("Error: The client is already registered for this lesson");

        // if not registered to the gym
        if (!gym.getClients().contains(client) || client == null)
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");

        String print = "";
        int exception = validRegisterToLesson(client, session);
        if (exception != 0)
        {
            switch (exception)
            {
                case 1: // no spots left
                    print = "Failed registration: No available spots for session";
                    break;
                    //throw new IllegalArgumentException("Failed registration: No available spots for session");
                case 2: // not enough balance
                    print = "Failed registration: Client doesn't have enough balance";
                    break;
                    //throw new IllegalArgumentException("Failed registration: Client doesn't have enough balance");
                case 3: // session for seniors
                    print = "Failed registration: Client doesn't meet the age requirements for this session (Seniors)";
                    break;
                    //throw new IllegalArgumentException("Failed registration: Client doesn't meet the age requirements for this session (Seniors)");
                case 4: // opposite genders
                    print = "Failed registration: Client's gender doesn't match the session's gender requirements";
                    break;
                    //throw new IllegalArgumentException("Failed registration: Client's gender doesn't match the session's gender requirements");
                case 5: // session has passed
                    print = "Failed registration: Session is not in the future";
                    break;
                    //throw new IllegalArgumentException("Failed registration: Session is not in the future");
                default:
            }

            addToHistory(print);
            System.out.println(print);
            throw new IllegalArgumentException(print);
        }

        print = "Registered client: " + client.getName() + " to session: " + session.getSessionType().getName() + " on " + session.getDate().toString() + " for price: " + session.getSessionType().getCost();
        addToHistory(print);
        System.out.println(print);
        //Registered client: Nofar to session: Pilates on 2025-01-23T10:00 for price: 60

        // valid -> register to lesson
        session.addClient(client); // add the client
        //session.setCurrentParticipants(session.getCurrentParticipants()+1); // reduce the capacity
        gym.setBalance(gym.getBalance() + session.getSessionType().getCost()); // add to the gym balance
        client.setBalance(client.getBalance() - session.getSessionType().getCost()); // reduce the cost from the client

    }
    public Instructor hireInstructor(Person person, int salary, ArrayList<String> sessionTypes) throws InstructorNotQualifiedException// was arrayList<SessionType>
    {
        Instructor instructor = new Instructor(person, salary, sessionTypes);
        gym.addInstructor(instructor);
        String print = "Hired new instructor: " + person.getName() + " with salary per hour: " + salary;
        System.out.println(print);
        addToHistory(print);
        return instructor;
    }

    public void paySalaries()
    {
        // pay the secretary
        gym.setBalance(gym.getBalance()-gym.getSecretary().salary);

        ArrayList<Session> needToPaySessions = gym.getPayBySessions();
        // pay by sessions
        for (int i = 0; i < needToPaySessions.size(); i++)
        {
            int paycheck = needToPaySessions.get(i).getInstructor().getSalary();
            needToPaySessions.get(i).getInstructor().paySalary(); // pay to the instructor
            gym.setBalance(gym.getBalance() - paycheck);
        }

        gym.setPayBySessions(new ArrayList<>()); // all salaries are paid -> remove all the sessions from the list
        String print = "Salaries have been paid to all employees";
        addToHistory(print);
    }

    // Observer
    public void notify(Session session, String message)
    {
        session.notifyClients(message);
        String print = "A message was sent to everyone registered for session " + session.getSessionType().getName() + " on " + session.getDate() + " : " + message;
        addToHistory(print);
        //notificationService = new NotificationService(session.getClients(), message);
    }

    private boolean sessionAtDate(Session session, String date)
    {
        String sessionDate = session.getDate().substring(0,10); // get only the date
        if (date.equals(sessionDate))
            return true;

        return false;
    }

    public void notify(String date, String message)
    {
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
                notify(dateSessions.get(i), message);
        }

        String print = "A message was sent to everyone registered for a session on " + date + " : " + message;
        addToHistory(print);
    }

    public void notify(String message)
    {
        notificationService = new NotificationService(gym.getClients(), message);
        String print = "A message was sent to all gym clients: " + message;
        addToHistory(print);
    }

    public void printActions()
    {
        for (int i = 0; i < gym.getHistory().size(); i++)
            System.out.println(gym.getHistory().get(i));
    }

    private void currentSecretary(Secretary secretary)
    {
        Secretary lastSecretary = gym.getLastSecretary();
        if (lastSecretary != gym.getSecretary() && lastSecretary != null)
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions");
    }

    private void addToHistory(String addToHistory)
    {
        gym.addHistory(addToHistory);
    }
}
