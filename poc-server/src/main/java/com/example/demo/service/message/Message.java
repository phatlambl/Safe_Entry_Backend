package com.example.demo.service.message;

import org.springframework.stereotype.Service;

@Service
public class Message {
	
    public final static String LOGIN_SUCCESS = "Login success";
    public final static String LOGIN_FAILURE = "Login failure";
    public final static String INFORMATION_LOGIN_INCORRECT = "Username or password is incorrect";
    public final static int HTTP_OK = 200;
    public final static String ROLE_ADMIN = "ADMIN";
    public final static String ROLE_USER = "USER";
    public final static String USER_NOT_EXIST = "USER NOT EXIST";
    public final static String ROLE = "ROLE";
    public final static String USER_ID = "USER_ID";
    public final static String PERMISSION_DENIED = "PERMISSION_DENIED";
    public final static String SUCCESS = "SUCCESS";
    public final static String SUCCESS_CONFIG = "SUCCESS_CONFIGURATION";
    public final static String FAIL_CONFIG = "FAIL_CONFIGURATION";
    public final static String REGISTER_SUCCESS = "REGISTER_SUCCESS";
    public final static String REGISTER_FAILURE = "REGISTER_FAILURE";
    public final static String ALERT_TEMPERATURE = " is higher than config temperature: ";
    public final static String SUBMIT_FAILURE = "SUBMIT_FAILURE";
    public final static String MISS_INFORMATION = "PLEASE INPUT FILL FULL INFORMATION";
    public final static String ADD_FAILURE = "ADD FAILURE";
    public final static String ADD_SUCCESS = "ADD SUCCESS";
    public final static String UPDATE_SUCCESS = "UPDATE SUCCESS";
    public final static String UPDATE_FAILURE = "UPDATE FAILURE";
    public final static String DUPLICATE_DEVICE ="DUPLICATE DEVICE";
    public final static String IMPORT_SUCCESS = "IMPORT SUCCESS";
    public final static String IMPORT_FAILURE = "IMPORT FAILURE";
    public final static String IMPORT_NOT_CORRECT = "FILE CSV NOT CORRECT";    
    public final static String DELETE_SUCCESS = "DELETE SUCCESS";
    public final static String DELETE_FAILURE = "DELETE FAILURE";
    public final static String NOT_RECOGNIZE = "Not Recognize, Please tap Card or Scan QR";
    
}
