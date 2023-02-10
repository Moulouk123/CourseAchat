package com.example.crudapp;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String telephone;
    private String email;
    private String fullname;

    private String iduser ;
    private String role;
    private String image;

    public User(String telephone,String image, String email, String fullname, String iduser,String role) {
        this.telephone = telephone;
        this.email = email;
        this.fullname=fullname;
        this.image=image;

        this.iduser = iduser;
        this.role=role;
    }




    protected User(Parcel in) {
        telephone = in.readString();

        image = in.readString();

        email = in.readString();
        role = in.readString();

        iduser = in.readString();
        fullname = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(telephone);
        dest.writeString(image);


        dest.writeString(email);
        dest.writeString(role);
        dest.writeString(iduser);
        dest.writeString(fullname);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
