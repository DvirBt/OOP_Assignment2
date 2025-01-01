package gym.management.Notification;

/**
 * This class is used as an observer to each session.
 */

import gym.customers.Client;
import java.util.ArrayList;

public class NotificationService {

    private ArrayList<Client> clients; // the list of the clients that will get the message
    private String message; // the message

    // Constructors
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

    /**
     * This function sends the message to all the clients in the list by using the update method that
     * each client has.
     */
    public void update()
    {
        for (int i = 0; i < clients.size(); i++)
            clients.get(i).update(message);
    }
}
