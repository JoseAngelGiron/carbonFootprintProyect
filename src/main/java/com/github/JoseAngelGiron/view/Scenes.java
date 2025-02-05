package com.github.JoseAngelGiron.view;

public enum Scenes {
    ROOT("view/home"),

    LOGIN("view/login"),
    REGISTER("view/register"),

    MAINPAGE("view/mainPage"),

    REGISTERNEWHABIT("view/registerHabit"),
    DELETEHABIT("view/deleteHabits"),
    UPDATEHABIT("view/updateHabits"),
    YOURHABITS("view/yourHabits"),


    REGISTERNEWPRINT("view/registerNewPrint"),
    DELETEPRINT("view/deletePrint"),
    UPDATEPRINT("view/updatePrint"),
    YOURPRINTS("view/yourPrints"),

    IMPACTFOOTPRINTS("view/checkImpactFootprints"),

    PROFILE("view/userProfile");

    //HOME("view/home");


    private String url;

    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }

}
