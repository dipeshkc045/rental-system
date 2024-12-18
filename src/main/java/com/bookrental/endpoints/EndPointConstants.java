package com.bookrental.endpoints;

public class EndPointConstants {

    private EndPointConstants() {

    }

    public static final String SAVE = "/save";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete/{id}";
    public static final String GET_ALL = "/get-all";
    public static final String GET_BY_ID = "/get-by-id/{id}";


    public static final String AUTHOR = "/author";
    public static final String BOOK = "/book";
    public static final String CATEGORY = "/category";
    public static final String USER = "/user";
    public static final String ROLE = "/role";
    public static final String USER_ROLE = "/user-role";

}
