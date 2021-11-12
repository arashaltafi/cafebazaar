package com.arash.altafi.caffebazar.utility;

public class TokenContainer {

    private static String token;

    public static void updateToken(String token)
    {
        TokenContainer.token = token;
    }

    public static String getToken()
    {
        return token;
    }

}