package com.bladespring.persistence.entities;

public class Officer {
    private Integer id;
    private Rank rank;
    private String firstName, lastName;

    public Officer() {
    }

    public Officer(Rank rank, String firstName, String lastName) {
        this.rank = rank;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Officer(Integer id, Rank rank, String firstName, String lastName) {
        this(rank, firstName, lastName);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Rank getRank() {
        return rank;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
