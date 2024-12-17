package gym.management.Sessions;

public class SessionFactory {

    private SessionType sessionType;
    public SessionFactory()
    {
        sessionType = null;
    }

    public SessionType createSessionType(String sessionType)
    {
        switch (sessionType)
        {
            case SessionType.Pilates:
                this.sessionType = new PilatesSession();
                break;

            case SessionType.MachinePilates:
                this.sessionType = new MachinePilates();
                break;

            case SessionType.ThaiBoxing:
                this.sessionType = new ThaiBoxing();
                break;

            case SessionType.Ninja:
                this.sessionType = new Ninja();

            default: // null
        }

        return this.sessionType;
    }

    /* TODO:
        1. add exception to instructor and session type
        2. add
     */

//    public Session createSession(String sessionType, String date, ForumType forumType, Instructor instructor)
//    {
//        return createSessionType(sessionType); // get the session type
//    }
}
