package com.example.myapplication4.vo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmVo extends RealmObject {
    @PrimaryKey
    String num;
    String main_category ;
    String category ;
    String sub_category ;
    String content ;

    public RealmVo(){

    }
    public RealmVo(String num, String main_category, String category, String sub_category, String content){
        this.num = num;
        this.main_category = main_category;
        this.category = category;
        this.sub_category = sub_category;
        this.content = content;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMain_category() {
        return main_category;
    }

    public void setMain_category(String main_category) {
        this.main_category = main_category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
