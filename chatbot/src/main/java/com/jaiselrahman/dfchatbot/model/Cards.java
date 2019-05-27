package com.jaiselrahman.dfchatbot.model;

import com.google.cloud.dialogflow.v2.Intent;

/**
 * Created by jaisel on 12/1/18.
 */

public class Cards {
    private String title, subtitle, imgUrl;
    private Intent.Message.Card.Button buttons;

    public String getImgUrl() {
        return imgUrl;
    }

    public String getSubtitle() { return subtitle; }

    public String getTitle() {
        return title;
    }

    public Intent.Message.Card.Button getbuttons(){ return buttons; }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setButtons(Intent.Message.Card.Button buttons){ this.buttons = buttons; }
}
