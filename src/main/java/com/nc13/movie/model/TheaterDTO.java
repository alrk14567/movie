package com.nc13.movie.model;

import lombok.Data;

import java.util.Date;

@Data
public class TheaterDTO {
    private int id;
    private String theaterName;
    private String theaterLocation;
    private String theaterTel;
    private Date entryDate;
    private Date modifyDate;
    private String fileName;
}
