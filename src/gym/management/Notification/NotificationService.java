package gym.management.Notification;

/**
 * This class is used as an observer to each session.
 */

import gym.customers.Client;
import java.util.ArrayList;

public class NotificationService implements Observer{

    private ArrayList<Client> clients; // the clients of this observer

    // Constructors
    public NotificationService()
    {
        clients = new ArrayList<>();
    }

    public NotificationService(ArrayList<Client> clients, String message)
    {
        this.clients = new ArrayList<>();
        this.clients.addAll(clients);
        update(message);
    }

    /**
     * This function sends the message to all the clients in the list by using the update method that
     * each client has.
     */
    public void update(String message)
    {
        for (int i = 0; i < clients.size(); i++)
            clients.get(i).update(message);
    }

    public void add(Client client)
    {
        clients.add(client);
    }

    public ArrayList<Client> getClients()
    {
        return this.clients;
    }
}
