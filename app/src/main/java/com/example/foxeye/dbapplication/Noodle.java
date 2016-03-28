package com.example.foxeye.dbapplication;

import java.io.Serializable;

public class Noodle implements Serializable {
    public static final  String TABLE = "Noodle";

    public static final String KEY_ID = "id";
    public static final String KEY_name = "name";
    public static final String KEY_rank = "rank";
    public static final String KEY_comment = "comment";
    public static final String KEY_date = "date";
    public static final String KEY_image ="image";

    public int noodle_ID;
    public String name;
    public int rank;
    public String comment;
    public int date;
    public byte[] image;

}