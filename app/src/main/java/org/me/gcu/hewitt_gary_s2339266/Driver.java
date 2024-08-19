package org.me.gcu.hewitt_gary_s2339266;

public class Driver {
    private  String givenName;
    private  String familyName;
    private String dateOfBirth;

    private String nationality;
    private String constructor;

    public Driver(String givenName, String familyName, String dateOfBirth, String nationality, String constructor) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.constructor = constructor;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public String getNationality(){
        return nationality;
    }
    public String getConstructor(){
        return constructor;
    }
    public String getFullName() {
        return givenName + " " + familyName;
    }
}
//s2339266