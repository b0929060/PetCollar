package com.example.petapp;

import java.util.HashMap;
import java.util.Map;

public class GobalVal {
    static final String ip = "172.21.5.153";
    static final String url = "http://"+GobalVal.ip+"/Pet_App/member/";
    public static int userId = -1;

    //private static int CollarId = -1;

    // 設置用戶 id
    public static void setUserId(int id) {
        userId = id;
    }
    // 取得用戶 id
    public static int getUserId() {
        return userId;
    }
    public static Map<String, String> collarPetMap = new HashMap<>();

}

