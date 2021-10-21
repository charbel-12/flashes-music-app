package com.example.mysongapp;

import java.io.File;
import java.util.ArrayList;

public class musicUtils {
static String nameofsongs[];
static ArrayList<File> songsFiles;

    public musicUtils(String[] nameofsongs, ArrayList<File> songsFiles) {
        this.nameofsongs = nameofsongs;
        this.songsFiles = songsFiles;
    }

    public String[] getNameofsongs() {
        return nameofsongs;
    }

    public void setNameofsongs(String[] nameofsongs) {
        this.nameofsongs = nameofsongs;
    }

    public ArrayList<File> getSongsFiles() {
        return songsFiles;
    }

    public void setSongsFiles(ArrayList<File> songsFiles) {
        this.songsFiles = songsFiles;
    }
}
