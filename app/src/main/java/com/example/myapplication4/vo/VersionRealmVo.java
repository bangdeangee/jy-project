package com.example.myapplication4.vo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class VersionRealmVo extends RealmObject {
    String version;

    public VersionRealmVo() {

    }

    public VersionRealmVo(String version) {
        this.version = version;

    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}