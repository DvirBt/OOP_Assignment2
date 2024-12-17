package gym.management.Sessions;

import gym.Gym;
import gym.customers.Client;

import java.util.ArrayList;

public enum ForumType {
    All("All"),
    Seniors("Seniors"),
    Male("Male"),
    Female("Female");

    final String audience;
    ForumType(String audience)
    {
        this.audience = audience;
    }

    public String getAudience()
    {
        return this.audience;
    }
    private ArrayList<Client> createList(ForumType forumType)
    {
        ArrayList<Client> all = Gym.getInstance().getClients();

        if (audience.equals("All")) // All
            return all;

        else
        {
            ArrayList<Client> temp = new ArrayList<>();

            if (audience.equals("Male")) // Male
            {
                for (int i = 0; i < all.size(); i++)
                {
                    if (all.get(i).getGender().name().equals("Male"))
                        temp.add(all.get(i));
                }
            }

            else if (audience.equals("Female")) // Female
            {
                for (int i = 0; i < all.size(); i++)
                {
                    if (all.get(i).getGender().name().equals("Female"))
                        temp.add(all.get(i));
                }
            }

            else // seniors
            {
                for (int i = 0; i < all.size(); i++)
                {
                    if (all.get(i).isSenior()) // 65+
                        temp.add(all.get(i));
                }
            }

            return temp;
        }
    }
}
