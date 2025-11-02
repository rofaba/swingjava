package org.example.model;

import java.util.Objects;

public class Usuario {
    private String id;
    private String email;
    private String password;

    /**
     * Constructor
     * @param id
     * @param email
     * @param password
     */

    public Usuario(String id, String email, String password) {
        this.id = id; this.email = email; this.password = password;
    }

    /**
     * Convierte el objeto Usuario a una representación en formato CSV.
     * *@return
     */
    public String toCsv() { return String.join(",", id, email, password); }

    // getters
    public String getId(){ return id; }
    public String getEmail(){ return email; }
    public String getPassword(){ return password; }

    // setters
    public void setId(String id){ this.id = id; }

    @Override public boolean equals(Object o){
        return o instanceof Usuario u && Objects.equals(id,u.id);
    }
    @Override public int hashCode(){
        return Objects.hash(id);
    }
}