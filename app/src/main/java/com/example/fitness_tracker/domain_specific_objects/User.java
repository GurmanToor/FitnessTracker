package com.example.fitness_tracker.domain_specific_objects;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The actual User!
 */
public class User {
    /**
     * the username
     */
    private String username;

    /**
     * the password
     */
    private String password;

    /**
     * the firstname
     */
    private String firstName;

    /**
     * the lastname
     */
    private String lastName;

    /**
     * the goal weight
     */
    private BigDecimal goalWeight;

    /**
     * the email address
     */

    private String email;

    /**
     * fasting mode
     */

    private boolean isFasting;

    /**
     * the current weight
     */
    private BigDecimal currentWeight;

    /**
     * The determined profile pic from the user
     */
    private int profilePic;


    /**
     * CONSTRUCTOR
     * @param newUsername the username
     * @otherparams default vals
     */
    public User(final String newUsername) {
        username = newUsername;
        password = null;
        firstName = null;
        lastName = null;
        goalWeight = null;
        email = "";
        profilePic = 0;
        currentWeight = null;
    }

    /**
     * CONSTRUCTOR
     * @param newUsername the username
     * @param newPassword the password
     * @param newFirstName the first name
     * @param newLastName the last name
     * @param newGoalWeight the new goal weight
     */
    public User(final String newUsername, final String newPassword, final String newFirstName,
                final String newLastName, final BigDecimal newGoalWeight, final String email) {
        username = newUsername;
        password = newPassword;
        firstName = newFirstName;
        lastName = newLastName;
        goalWeight = newGoalWeight;
        profilePic = 0;
        this.email = email;
        currentWeight = null;
    }

    /**
     * standard toString
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", goalWeight=" + goalWeight +
                ", email='" + email + '\'' +
                ", profilePic=" + profilePic +
                '}';
    }

    /**
     * equals
     *
     * checks if the given object is equal to the current workout object
     * @param o the object
     * @return if it is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    /********************************************************************************************
     *                               GETTERS!
     * ********************************************************************************************/
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getProfilePicId() {
        return profilePic;
    }

    public boolean isFasting() {
        return this.isFasting;
    }


    public void setProfilePicId(int i) {
        profilePic = i;
    }

    public BigDecimal getGoalWeight() {
        return goalWeight;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGoalWeight(BigDecimal goalWeight) {
        this.goalWeight = goalWeight;
    }

    public void setFasting(boolean isFasting) {
        this.isFasting = isFasting;
    }

    public void setEmail(String email) {this.email = email;}

    public BigDecimal getCurrentWeight() {
        if(currentWeight == null) {
            currentWeight = BigDecimal.valueOf(0);
        }
        return currentWeight;
    }

    public void setCurrentWeight(BigDecimal currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getEmail() {
        if(email.isEmpty()) {
            return "Not Set.";
        }
        return email;
    }


    @Override
    public int hashCode() {
        return Objects.hash(username, password, firstName, lastName, goalWeight, email);
    }
}
