package gym.management.Notification;

import gym.customers.Client;

/**
 * This Interface represents an Observer
 */
public interface Observer {

    public void add(Client client);
    public void update(String message);
}
