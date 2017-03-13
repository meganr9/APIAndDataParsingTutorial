package com.meagain.apianddataparsingtutorial;

/**
 * Created by Megan on 2/13/2017.
 */
public class Person {
    private String id, name, department;
    private int age;

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
