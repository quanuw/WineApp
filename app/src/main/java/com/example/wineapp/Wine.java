package com.example.wineapp;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * Wine
 *
 * This class encapsulates the information stored per type of wine in the database.
 */
public class Wine implements Parcelable {
    public enum Color {
        RED,
        WHITE,
        AMBER,
        ROSEE,
        PINK
    }

    private int id;
    private String name;
    private String brand;
    private Color color;
    private double cost;
    private String grape_type; // could make enum?
    private double rating;

    /**
     * Bare minimum constructor; other fields set to null
     *
     * @param id Unique primary key from database
     * @param name Name of the wine
     */
    Wine(int id, String name) {
        this.id(id);
        this.name(name);
    }

    /**
     * Full constructor
     *
     * @param name Name of the wine
     * @param brand Brand name of the wine
     * @param color Color of the wine
     * @param cost Price of wine per bottle
     * @param grape_type Type of grape in wine
     * @param rating 0.0-10.0 score for the wine
     */
    Wine(int id, String name, String brand, Wine.Color color, double cost, String grape_type, double rating) {
        this.id(id);
        this.name(name);
        this.brand(brand);
        this.color(color);
        this.cost(cost);
        this.grape_type(grape_type);
        this.rating(rating);
    }

    /**
     * DB cursor constructor
     * @param c Cursor (from DB query result)
     */
    Wine(Cursor c) {
        // TODO: implement; this might be useful
        // TODO: make sure cursor has at least one result and take the first one (if multiple)? Need to decide how it will work.

        // e.g. super(id, name, brand, color);
    }

    /**
     * ID getter
     * @return unique id of this wine
     */
    public int id() {
        return this.id;
    }

    /**
     * ID setter; Set by insert from DataManager
     * @param id new id
     */
    public void id(int id) {
        this.id = id;
    }

    /**
     * Name getter
     * @return current name of wine
     */
    public String name() {
        return this.name;
    }

    /**
     * Name setter
     * @param name New wine name
     */
    public void name(String name) {
        this.name = name;
    }

    /**
     * Brand getter
     * @return brand name of this wine
     */
    public String brand() {
        return this.brand;
    }

    /**
     * Brand setter
     * @param brand new brand of wine
     */
    public void brand(String brand) {
        this.brand = brand;
    }

    /**
     * Color getter
     */
    public Wine.Color color() {
        return this.color;
    }

    /**
     * Color setter
     * @param color color of wine
     */
    public void color(Wine.Color color) {
        this.color = color;
    }

    /**
     * Cost getter
     * @return cost per bottle of wine
     */
    public double cost() {
        return this.cost;
    }

    /**
     * Cost setter
     * @param cost new cost per bottle of wine
     */
    public void cost(double cost) {
        this.cost = cost;
    }

    /**
     * Grape type getter
     * @return
     */
    public String grape_type() {
        return this.grape_type;
    }

    /**
     * Grape type setter
     * @param grape_type type of grape
     */
    public void grape_type(String grape_type) {
        this.grape_type = grape_type;
    }

    /**
     * Rating type getter
     * @return
     */
    public double rating() {
        return this.rating;
    }

    /**
     * Rating type setter and checks valid nums
     * @param rating type of grape
     */
    public void rating(double rating) {
        double minNum = 0.0;
        double maxNum = 10.0;
        if(rating < minNum || rating > maxNum){
            // TODO: Throw Exception when value not between 0-10
            this.rating = (rating > maxNum) ? maxNum : (rating < minNum) ? minNum : rating; 
        }
        else{
            this.rating = rating;
        }
    }

    /**
     * Convert Wine object to string; mainly for debug
     * @return string of wine debug info
     */
    public String toString() {
        return String.format(
                Locale.ENGLISH,
                "[ Wine # %d | name (%s), brand (%s), color (%s), cost (%.2f), grape_type (%s), rating (%.2f) ]",
                this.id(),
                this.name(),
                this.brand(),
                this.color(),
                this.cost(),
                this.grape_type(),
                this.rating()
        );
    }

    // ------------------------------------------------------------------------
    // Parcelable interface implementation
    // ------------------------------------------------------------------------
    public static final Parcelable.Creator<Wine> CREATOR = new Parcelable.Creator<Wine>() {
        public Wine createFromParcel(Parcel in) {
            return new Wine(in);
        }

        public Wine[] newArray(int size) {
            return new Wine[size];
        }
    };

    public int describeContents() {
        // return 0; most of the time
        // return CONTENTS_FILE_DESCRIPTOR; if parcel contains a file handle
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id());
        out.writeString(this.name());
        out.writeString(this.brand());
        out.writeInt(this.color().ordinal());
        out.writeDouble(this.cost());
        out.writeString(this.grape_type());
        out.writeDouble(this.rating());
    }

    /**
     * Private parcel constructor
     */
    private Wine(Parcel in) {
        this.id(in.readInt());
        this.name(in.readString());
        this.brand(in.readString());
        this.color(Color.values()[in.readInt()]);
        this.cost(in.readDouble());
        this.grape_type(in.readString());
        this.rating(in.readDouble());
    }
}
