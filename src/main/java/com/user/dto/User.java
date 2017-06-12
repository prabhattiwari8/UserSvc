package com.user.dto;

import javax.validation.constraints.*;

public class User {

    public interface CreateUserGroup {}
    public interface UpdateUserGroup {}

    public static enum Gender {
        M,
        F
    }

    @NotNull(groups = UpdateUserGroup.class)
    private String uuid;

    @NotNull(groups = {CreateUserGroup.class})
    @Pattern(groups = {UpdateUserGroup.class,CreateUserGroup.class}, regexp = "[a-zA-Z]+")
    private String firstName;

    @Pattern(groups = {UpdateUserGroup.class,CreateUserGroup.class}, regexp = "[a-zA-Z]+")
    private String middleName;

    @NotNull(groups = {CreateUserGroup.class})
    @Pattern(groups = {UpdateUserGroup.class,CreateUserGroup.class}, regexp = "[a-zA-Z]+")
    private String lastName;

    @NotNull(groups = {CreateUserGroup.class})
    @Min(groups = {UpdateUserGroup.class,CreateUserGroup.class}, value = 1)
    private int age;

    @NotNull(groups = {CreateUserGroup.class})
    private Gender gender;

    @NotNull(groups = {CreateUserGroup.class})
    @Pattern(groups = {UpdateUserGroup.class,CreateUserGroup.class}, regexp = "([1-9])(\\d{9})+")
    private String phone;

    private int zip;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
}
