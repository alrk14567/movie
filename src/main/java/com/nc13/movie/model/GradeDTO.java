package com.nc13.movie.model;

import lombok.Data;

import java.util.Date;

@Data
public class GradeDTO {
    private int id;
    private int writerId;
    private int movieId;
    private int grade;
    private String review;
    private Date entryDate;
    private Date modifyDate;
    private String nickname;
}
