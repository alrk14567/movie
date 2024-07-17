package com.nc13.movie.model;

import lombok.Data;

import java.util.Date;

@Data
public class MovieDTO {
    private int id;
    private String title;
    private String story;
    private String movieGrade;
    private Date entryDate;
    private Date modifyDate;
    private String fileName;
}
