package com.github.JoseAngelGiron.view;

public enum Scenes {
    ROOT("view/home"),

    LOGIN("view/login"),
    REGISTER("view/register"),

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
