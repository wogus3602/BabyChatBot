package com.jaiselrahman.dfchatbot;

public class Itemlist {

    private int imageNum; //이미지
    private String id; //아이디
    private String txt; // 마지막 채팅

    public Itemlist(int imageNum, String id, String txt){//생성자
        this.imageNum = imageNum;
        this.id = id;
        this.txt = txt;
    }

    public String getId(){//외부로 id값을 리턴해서 내보내준다.
        return id;
    }

    public int getimageNum(){//외부로 이미지 값을 리턴해서 보내준다.
        return imageNum;
    }

    public String getTxt(){
        return txt;
    }

    public void setId(String id){//외부에서 받은 id를 내부로 넣어준다.
        this.id=id;
    }

    public void setimageNum(int imageNum){//외부에서 받은 imagenumber를 내부로 넣어준다.
        this.imageNum = imageNum;
    }

    public void setTxt(String txt){
        this.txt = txt;
    }


}