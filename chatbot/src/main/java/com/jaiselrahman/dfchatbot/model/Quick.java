package com.jaiselrahman.dfchatbot.model;

import com.google.cloud.dialogflow.v2.Intent.Message.QuickReplies;

public class Quick {

    private String title1, title2;
    private QuickReplies quickReplies;

    public String getTitle1() {
        return title1;
    }
    public String getTitle2() {
        return title2;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }
    public void setTitle2(String title2) {
        this.title2 = title2;
    }


}
