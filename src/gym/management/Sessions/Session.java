package gym.management.Sessions;

import gym.customers.Client;
import gym.management.Notification.NotificationService;

import java.util.ArrayList;

public class Session {
    private SessionType sessionType;
    private String date;
    private ForumType forumType;
    private Instructor instructor;
    //private int currentParticipants;
    private ArrayList<Client> clients;
    private NotificationService notificationService;

    public Session(SessionType sessionType, String date, ForumType forumType, Instructor instructor)
    {
        this.sessionType = sessionType;
        this.date = date;
        this.forumType = forumType;
        this.instructor = instructor;
        //currentParticipants = 0;
        clients = new ArrayList<>();
        notificationService = new NotificationService();
    }

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

    public void addClient(Client client)
    {
        this.clients.add(client);
    }

    /*
    public int getCurrentParticipants()
    {
        return currentParticipants;
    }

    public void setCurrentParticipants(int currentParticipants)
    {
        this.currentParticipants = currentParticipants;
    }

       */

    public void notifyClients(String message)
    {
        for (int i = 0; i < clients.size(); i++)
            clients.get(i).update(message);
    }
}
