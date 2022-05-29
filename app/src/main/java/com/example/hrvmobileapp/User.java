package com.example.hrvmobileapp;

public class User {
    public String mail, password, name, surname, height, weight, age;

    public User(){

    }
    public User(String mail, String password, String name, String surname, String height, String weight, String age){
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.age=age;


    }
}
