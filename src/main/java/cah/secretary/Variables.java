package cah.secretary;

import java.util.List;

import static java.util.Arrays.asList;

public class Variables {
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
