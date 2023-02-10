package com.example.crudapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Course implements Parcelable {
    private String course_name;
    private String course_price;
    private String course_ID;


    private String course_SuitedFor;

    private String course_image;
    private String course_Link;
    private String course_Description;

public Course()
{}

    public Course( String course_ID,String course_name, String course_price, String course_SuitedFor, String course_image, String course_Link, String course_Description) {
        this.course_name = course_name;
        this.course_price = course_price;
        this.course_ID = course_ID;
        this.course_SuitedFor = course_SuitedFor;
        this.course_image = course_image;
        this.course_Link = course_Link;
        this.course_Description = course_Description;
    }

    protected Course(Parcel in) {
        course_name = in.readString();

            course_price = in.readString();

        course_ID = in.readString();
        course_SuitedFor = in.readString();
        course_image = in.readString();
        course_Link = in.readString();
        course_Description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(course_name);


            dest.writeString(course_price);

        dest.writeString(course_ID);
        dest.writeString(course_SuitedFor);
        dest.writeString(course_image);
        dest.writeString(course_Link);
        dest.writeString(course_Description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_price() {
        return course_price;
    }

    public void setCourse_price(String course_price) {
        this.course_price = course_price;
    }

    public String getCourse_ID() {
        return course_ID;
    }

    public void setCourse_ID(String course_ID) {
        this.course_ID = course_ID;
    }

    public String getCourse_SuitedFor() {
        return course_SuitedFor;
    }

    public void setCourse_SuitedFor(String course_SuitedFor) {
        this.course_SuitedFor = course_SuitedFor;
    }

    public String getCourse_image() {
        return course_image;
    }

    public void setCourse_image(String course_image) {
        this.course_image = course_image;
    }

    public String getCourse_Link() {
        return course_Link;
    }

    public void setCourse_Link(String course_Link) {
        this.course_Link = course_Link;
    }

    public String getCourse_Description() {
        return course_Description;
    }

    public void setCourse_Description(String course_Description) {
        this.course_Description = course_Description;
    }
}
