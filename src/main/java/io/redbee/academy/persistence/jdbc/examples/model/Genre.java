package io.redbee.academy.persistence.jdbc.examples.model;

public class Genre {
    private Integer id;
    private String description;
    private String creation_user;

    public Genre(Integer id, String description, String creation_user){
        this.id = id;
        this.description = description;
        this.creation_user = creation_user;
    }
    public String toString() {
        return "{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", creationUser='" + creation_user + '\'' +
                '}';
    }
}
