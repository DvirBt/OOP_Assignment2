package gym.management.Sessions;

/**
 * This class represents a Session in the Gym.
 */

import gym.customers.Client;
import gym.management.Notification.NotificationService;

import java.util.ArrayList;

public class Session {

    // Data members
    private SessionType sessionType;
    private String date;
    private ForumType forumType;
    private Instructor instructor;
    private ArrayList<Client> clients;
    private NotificationService notificationService;

    // Constructor
    public Session(SessionType sessionType, String date, ForumType forumType, Instructor instructor)
    {
        this.sessionType = sessionType;
        this.date = date;
        this.forumType = forumType;
        this.instructor = instructor;
        clients = new ArrayList<>();
        notificationService = new NotificationService();
    }

    // Getters and Setters
    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType)
    {
        this.sessionType = sessionType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public ForumType getForumType()
    {
        return this.forumType;
    }

    public void setForumType(ForumType forumType)
    {
        this.forumType = forumType;
    }

    public Instructor getInstructor()
    {
        return this.instructor;
    }

    public void setInstructor(Instructor instructor)
    {
        this.instructor = instructor;
    }

    public ArrayList<Client> getClients()
    {
        return clients;
    }

    public void setClients(ArrayList<Client> clients)
    {
        this.clients = new ArrayList<>();
        this.clients.addAll(clients);
    }

    /**
     * This function adds a client to the list of clients.
     * @param client - a client that will be added to the list.
     */
    public void addClient(Client client)
    {
        this.clients.add(client);
    }

    /**
     * This function sends a given message to the clients of this session.
     * @param message - a String message.
     */
    public void notifyClients(String message)
    {
        for (int i = 0; i < clients.size(); i++)
            clients.get(i).update(message);
    }
}
