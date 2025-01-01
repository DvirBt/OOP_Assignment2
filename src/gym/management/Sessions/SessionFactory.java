package gym.management.Sessions;

/**
 * This class represents a Factory of SessionTypes.
 */
public class SessionFactory {

    // Properties
    private SessionType sessionType;

    // Constructor
    public SessionFactory()
    {
        sessionType = null;
    }

    /**
     * This function gets a String and returns the session type related to the String.
     * @param sessionType - a String.
     * @return a session type according to the given String if one's exists. Otherwise, returns null.
     */
    public SessionType createSessionType(String sessionType)
    {
        switch (sessionType)
        {
            case SessionType.Pilates:
                this.sessionType = new Pilates();
                break;

            case SessionType.MachinePilates:
                this.sessionType = new MachinePilates();
                break;

            case SessionType.ThaiBoxing:
                this.sessionType = new ThaiBoxing();
                break;

            case SessionType.Ninja:
                this.sessionType = new Ninja();
                break;

            default: // null
                this.sessionType = null;
        }

        return this.sessionType;
    }
}
