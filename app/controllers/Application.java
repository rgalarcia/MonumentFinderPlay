package controllers;

import groovy.json.internal.JsonParserUsingCharacterSource;
import groovy.json.internal.JsonStringDecoder;
import org.apache.commons.lang.StringUtils;
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

    public static void account(String message, String username){
        User us = User.find("byUsername", username).first();
        String email = us.email;
        int mobil = us.mobil;
        if (message == null) {
            message = "Update your username data here.";
            render(message, username, email, mobil);
        } else {
            render(message, username, email, mobil);
        }
    }

    public static void profileAndroid(String u){

        User us = User.find("byUsername", u).first();
        String param = us.gustos;
        renderJSON(param);
    }

    public static void api(String param){
        List<Monument> monList;
        List<String> paramList = Arrays.asList(param.split(","));

        if(param != null) {
            monList = Monument.find("Param in (?1)", paramList).fetch();
        } else {
            monList = Monument.findAll();
        }
        renderJSON(monList);
    }

    public static void likemonument (String usr, String mon){
        User username = User.find("byUsername", usr).first();
        Monument monument = Monument.find("byName", mon).first();
        List<Monument> listMon = username.lmonuments;
        listMon.add(monument);

        username.lmonuments = listMon;
        username.save();
        profile(usr);
    }

    public static void dislikemonument (String usr, String mon){
        User username = User.find("byUsername", usr).first();
        Monument monument = Monument.find("byName", mon).first();
        List<Monument> listMon = username.lmonuments;
        listMon.remove(monument);

        username.lmonuments = listMon;
        username.save();
        profile(usr);
    }

    public static void isliked(String usr, String mon){
        int isliked = 0;

        User username = User.find("byUsername", usr).first();
        Monument monument = Monument.find("byName", mon).first();

        for (Monument m:username.lmonuments){
            if(m.name.equals(monument.name)){
                isliked = 1;
            }
        }

        renderText(isliked);
    }

    public static void accountmanager (Integer a, String u, String e, String p, String np, Integer m, String g)
    {
        if (a == 0) {
            List<User> usersList = User.find("byUsername", u).fetch();

            if (usersList.size() == 0) {
                if (m != null) {
                    int count = StringUtils.countMatches(g, ",");

                    if (count != 0) {
                        g = g.replaceAll("\\s+", "");
                        User us = new User(u, e, p, m, g).save();
                        us.save();
                    } else {
                        User us = new User(u, e, p, m, g).save();
                        us.save();
                    }
                } else {
                    index("ERROR: Mobile phone is probably too large (max. 9 digits).");
                }

                index("Registered correctly. :)");
            } else {
                index("ERROR: The username already exists. :(");
            }
        } else if (a == 1){
            User us = User.find("byUsernameAndPassword", u, p).first();

            if (us == null) {
                account("ERROR: Username and password do not match. :(", u);
            } else {
                if (m != null) {
                    int count = StringUtils.countMatches(g, ",");

                    if (count != 0) {
                        g = g.replaceAll("\\s+", "");
                    }

                    us.username = u;
                    us.email = e;
                    us.password = np;
                    us.mobil = m;
                    us.gustos = g;
                    us.save();

                    index("User data has been updated. Please log in again. :)");
                } else {
                    account("ERROR: Mobile phone is probably too large (max. 9 digits).", u);
                }
            }

        }
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

    public static void delete (String message, String username){
        if(message == null) {
            message = "Please introduce your password.";
        }
        render(message, username);
    }

    public static void removeUser (String u, String p)
    {
        User us = User.find("byUsernameAndPassword", u, p).first();
        if (us == null) {
            delete("ERROR: The password is incorrect.", u);
        } else {
            us.delete();
            index("Your username was removed.");
        }
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