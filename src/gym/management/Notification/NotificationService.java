package gym.management.Notification;

import gym.customers.Client;
import gym.management.Sessions.Session;

import java.util.ArrayList;

public class NotificationService {

    private ArrayList<Client> clients; // clients that will get the message
    private String message; // the message

    public NotificationService()
    {
        clients = new ArrayList<>();
    }

    public NotificationService(ArrayList<Client> clients, String message)
    {
        this.clients = new ArrayList<>();
        this.clients.addAll(clients);
        this.message = message;
        update();
    }

    public NotificationService(String message)
    {

    }

    public void update()
    {
        for (int i = 0; i < clients.size(); i++)
            clients.get(i).update(message);

    }

}
