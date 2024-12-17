package gym.customers;

public enum Gender {
    Male("Male"),
    Female("Female");

    final String gender;
    Gender(String gender)
    {
        this.gender = gender;
    }

    public String getGender()
    {
        return gender;
    }
}
