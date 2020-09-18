package com.parkit.parkingsystem.constants;

public class DBConstants {

    public static final String GET_NEXT_PARKING_SPOT = "select min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = ?";
    public static final String UPDATE_PARKING_SPOT = "update parking set available = ? where PARKING_NUMBER = ?";

    public static final String SAVE_TICKET = "insert into ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME, DISCOUNT) values(?,?,?,?,?,?)";
    public static final String UPDATE_TICKET = "update ticket set PRICE=?, OUT_TIME=? where ID=?";
    public static final String GET_TICKET = "select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, t.DISCOUNT, p.TYPE from ticket t,parking p where p.parking_number = t.parking_number and t.VEHICLE_REG_NUMBER=? order by t.IN_TIME  limit 1";

    public static final String SAVE_USER = "insert into users(RECCURING, VEHICLE_REG_NUMBER, NAME) values(?,?,?)";
    public static final String UPDATE_USER = "update users set RECURRING=? where ID=?";
    public static final String GET_USER = "select u.RECCURING, u.VEHICLE_REG_NUMBER, u.NAME from USER u order by u.NAME limit 1";
    public static final String READ_RECURRING = "select u.RECURING from USER u where u.id = ?";
}