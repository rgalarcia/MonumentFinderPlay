package controllers;

import groovy.json.internal.JsonParserUsingCharacterSource;
import groovy.json.internal.JsonStringDecoder;
import org.junit.Test;
import play.*;
import play.mvc.*;
import java.util.*;
import models.*;


public class Application extends Controller {

    public static void index(String message) {
        if(message != null) {
            render(message);
        } else {
            message = "Welcome to MonumentFinder.";
            render(message);
        }
    }

    public static void register(){
        render();
    }

    public static void profile(String username){

        User us = User.find("byUsername", username).first();
        String param = us.gustos;
        render(username, param);
    }

    public static void profileAndroid(String u){

        User us = User.find("byUsername", u).first();
        String param = us.gustos;
        renderJSON(param);
    }

    public static void api(String param){
        List<Monument> monList;

        if(param != null) {
            monList = Monument.find("byParam", param).fetch();

        }
        else {
            monList = Monument.findAll();
        }
        renderJSON(monList);
    }

    public static void doregister (String u, String e, String p, Integer m, String g)
    {
        User us = new User(u, e, p, m, g).save();
        us.save();
        index("Registered correctly. :)");
    }

    public static void login (String u, String p)
    {
        User us = User.find("byUsernameAndPassword", u, p).first();

        if (us != null) {
            profile(u);
        }
        else
        {
            index("Incorrect username or password.");
        }
    }



    public static void logout(){
        index("Welcome to MonumentFinder.");
    }

    public static void removeUser (String u, String p)
    {
        User us = User.find("byUsernameAndPassword", u, p).first();
        us.delete();
    }


    // Android functions
    public static void AndroidLogin (String u, String p){

        User us = User.find("byUsernameAndPassword", u, p).first();

        if (us != null) {
            renderJSON("OK");
        }
        else
        {
            renderJSON("FAIL");
        }
    }

    public static void AndroidLogout (){
        
    }
}