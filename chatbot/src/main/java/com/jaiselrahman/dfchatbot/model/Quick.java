package com.jaiselrahman.dfchatbot.model;

import com.google.cloud.dialogflow.v2.Intent.Message.QuickReplies;

public class Quick {

        private String title1,title2,title3;
        private QuickReplies quickReplies;

        public String getTitle1() {
        return title1;
    }
        public String getTitle2() {
        return title2;
    }
        public String getTitle3() {
        return title3;
    }

        public void setTitle1(String title1) {
        this.title1 = title1;
    }
        public void setTitle2(String title2) {
        this.title2 = title2;
    }
        public void setTitle3(String title3) {
        this.title3 = title3;
    }

}
