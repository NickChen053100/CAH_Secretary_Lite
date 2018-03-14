package cah.secretary.Control;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Variables {
    //pad
    //guild id
    public static Guild guild;
    public static String id = "421816521447309324";
    //welcome quiz arraylist
    public static List<String> quizIDs = new ArrayList();
    public static List<Integer> quizScores = new ArrayList();
    //server roles
    public static List<Role> serverRoles;
    //trial role
    private static String trialRole = "Trial"; //role that while the user has, can't see anything other than the landing
    //roles that once assigned, can't self assign other roles
    private static String token = System.getenv("Token");
    private static String[] restrictedRoles = {
            "Accepted", "Committed", "Undergrad"
    };
    //roles that regular users can't self assign
    private static List<String> lockedRoles = asList(
            "Admin",
            "Dyno",
            "UB3R-B0T",
            "Moderator",
            "Partner",
            "Admin Emeritus",
            "Mod Emeritus",
            "Muted",
            "Verified",
            "Committed",
            "Accepted",
            "Admissions",
            "Contributor",
            "Pulitzer Winner",
            "Bot Commander",
            "Bot",
            "ApplyingToCollegeBot",
            "Cyan",
            "(ﾉ◕ヮ◕)ﾉ✧･ﾟ*✧spoo.py✧*･ﾟ✧ヽ(◕ヮ◕)ﾉ",
            "Tatsumaki",
            "MathBot",
            "Botless",
            "IT Guy",
            "@everyone"
    );

    public static String getTrialRole() {
        return trialRole;
    }
    public static String[] getRestrictedRoles() {
        return restrictedRoles;
    }

    public static List<String> getLockedRoles() {
        return lockedRoles;
    }

    public static String getToken() {
        return token;
    }
}
