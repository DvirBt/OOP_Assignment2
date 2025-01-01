package gym;

/**
 * This class represents the Gym.
 * Only one instance of gym can be created - singleton.
 */

import gym.customers.Client;
import gym.customers.Person;
import gym.management.Secretary;
import gym.management.Sessions.Instructor;
import gym.management.Sessions.Session;

import java.util.ArrayList;

public class Gym {

    // Singleton - only one appearance is required
    private final static Gym gym = new Gym();

    // properties
    private Secretary secretary;
    private String name;
    private ArrayList<Client> clients;
    private int balance = 0;
    private ArrayList<Instructor> instructors;
    private ArrayList<Session> payBySessions;
    private ArrayList<Session> sessions;
    private ArrayList<String> history;
    private ArrayList<Secretary> formerSecretaries;

    private Gym()
    {
        secretary = null;
        name = "Gym";
        clients = new ArrayList<>();
        balance = 0;
        instructors = new ArrayList<>();
        payBySessions = new ArrayList<>();
        sessions = new ArrayList<>();
        history = new ArrayList<>();
        formerSecretaries = new ArrayList<>();
    }

    // Getters and Setters
    public static Gym getInstance()
    {
        return gym;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Secretary getSecretary()
    {
        if (secretary == null)
            throw new NullPointerException("No secretary!");

        return secretary;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setSecretary(Person person, int salary)
    {
        if (secretary != null)
            formerSecretaries.add(secretary);

        secretary =  Secretary.getInstance(person, salary);
        String print = "A new secretary has started working at the gym: " + secretary.getName();
        history.add(print);
    }

    public ArrayList<Client> getClients()
    {
        return this.clients;
    }

    public void setClients(ArrayList<Client> clients)
    {
        this.clients = new ArrayList<>();
        this.clients.addAll(clients);
    }

    public ArrayList<Instructor> getInstructors()
    {
        return instructors;
    }

    public void setInstructors(ArrayList<Instructor> instructors)
    {
        this.instructors = new ArrayList<>();
        this.instructors.addAll(instructors);
    }

    public void addInstructor(Instructor instructor)
    {
        instructors.add(instructor);
    }

    public ArrayList<Session> getPayBySessions()
    {
        return this.payBySessions;
    }

    public void setPayBySessions(ArrayList<Session> payBySessions)
    {
        this.payBySessions = new ArrayList<>();
        this.payBySessions.addAll(payBySessions);
    }

    public ArrayList<Session> getSessions()
    {
        return this.sessions;
    }

    public void setSessions(ArrayList<Session> sessions)
    {
        this.sessions = new ArrayList<>();
        this.sessions.addAll(sessions);
    }

    public ArrayList<String> getHistory()
    {
        return history;
    }

    public void setHistory(ArrayList<String> history)
    {
        this.history = new ArrayList<>();
        this.history.addAll(history);
    }

    public void addHistory(String history)
    {
        this.history.add(history);
    }

    public void addClient(Client client)
    {
        this.clients.add(client);
    }

    public void addPayBySession(Session session)
    {
        this.payBySessions.add(session);
    }

    public void addSession(Session session)
    {
        sessions.add(session);
    }

    public String toString()
    {
        String toString = "";
        toString += "Gym Name: " + name + "\n";
        Secretary currentSecretary = gym.getSecretary();
        toString += "Gym Secretary: ID: " + secretary.getId() + " | Name: " + currentSecretary.getName() + " | Gender: " + currentSecretary.getGender().name() +
                " | Birthday: " + currentSecretary.getBirthday() + " | Age: " + currentSecretary.getAge() +
                " | Balance: " + currentSecretary.getBalance() + " | Role: Secretary | Salary per Month: " + currentSecretary.getSalary() + "\n";
        toString += "Gym Balance: " + gym.balance + "\n\n";
        toString += "Clients Data: \n";

        for (int i = 0; i < clients.size(); i++)
        {
            Client currentClient = clients.get(i);
            toString += "ID: " + currentClient.getId() +" | Name: " + currentClient.getName() + " | Gender: " + currentClient.getGender().name() +
                    " | Birthday: " + currentClient.getBirthday() + " | Age: " + currentClient.getAge() +
                    " | Balance: " + currentClient.getBalance() + "\n";
        }

        toString += "\nEmployees Data:\n";
        for (int i = 0; i < instructors.size(); i++)
        {
            Instructor currentInstructor = instructors.get(i);
            toString += "ID: " + currentInstructor.getId() + " | Name: " + currentInstructor.getName() + " | Gender: " + currentInstructor.getGender().name() +
                    " | Birthday: " + currentInstructor.getBirthday() + " | Age: " + currentInstructor.getAge() +
                    " | Balance: " + currentInstructor.getBalance() + " | Role: Instructor | Salary per Hour: " + currentInstructor.getSalary() +
                    " | Certified Classes: ";
                    for (int j = 0; j < currentInstructor.getTypes().size(); j++)
                    {
                        if (j+1 == currentInstructor.getTypes().size())
                            toString += currentInstructor.getTypes().get(j) + "\n";

                        else
                            toString += currentInstructor.getTypes().get(j) + ", ";
                    }
        }

        // Session Type: Pilates | Date: 23-01-2025 10:00 | Forum: All | Instructor: Yuval | Participants: 1/30
        toString += "\nSessions Data:\n";
        for (int i = 0; i < sessions.size(); i++)
        {
            Session currentSession = sessions.get(i);
            toString += "Session Type: " + currentSession.getSessionType().getName() + " | Date: " + currentSession.getDate() +
                    " | Forum: " + currentSession.getForumType().getAudience() + " | Instructor: " + currentSession.getInstructor().getName() +
                    " | Participants: " + currentSession.getClients().size() + "/" + currentSession.getSessionType().getCapacity() + "\n";
        }

        return toString;
    }


}
