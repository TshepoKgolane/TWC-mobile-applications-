package com.example.work;

public class Users {
    private String name;
    private String surname;
    private String Username;

    public static String Loggedname;
    public static String Loggedsurname;
    public static String LoggedRole;

    private String material;
    private int measurement;
    private int ProdHowMuchUsed;

    public int getProdHowMuchUsed() {
        return ProdHowMuchUsed;
    }

    public void setProdHowMuchUsed(int prodHowMuchUsed) {
        ProdHowMuchUsed = prodHowMuchUsed;
    }

    public Users(int prodHowMuchUsed) {
        ProdHowMuchUsed = prodHowMuchUsed;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getMeasurement() {
        return measurement;
    }

    public void setMeasurement(int measurement) {
        this.measurement = measurement;
    }

    public Users(String material, int measurement) {
        this.material = material;
        this.measurement = measurement;
    }





    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    private String Password;

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    private String Role;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Users(String name, String surname, String username, String role, String password) {
        this.name = name;
        this.surname = surname;
        this.Role = role;
        this.Password = password;
        Username = username;
    }
}
