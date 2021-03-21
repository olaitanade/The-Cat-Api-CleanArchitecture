package com.vastinc.thecatexperience.config;

public class CatUrls {
    public enum DevEnv{
        PRODUCTION,
        STAGING
    }
    public static final DevEnv ENV = DevEnv.PRODUCTION;

    public static String GET_BASEURL(){ return (ENV== DevEnv.PRODUCTION)?Production.BASE_URL:Staging.BASE_URL;}
}
