package gym.management.Notification;

import gym.management.Sessions.Session;

public class Message {

    private final String message;

    public Message(String message)
    {
        this.message = message;
    }

    public void update()
    {
        System.out.println(message);
    }

}
