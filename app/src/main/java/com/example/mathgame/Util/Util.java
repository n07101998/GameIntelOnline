package com.example.mathfastgame.Util;

import com.example.mathfastgame.Model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Util {
    public static ArrayList<User> arrUser=new ArrayList<>();
    public static void SortPoint(){
        Collections.sort(Util.arrUser, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (o1.getPoint()>o2.getPoint()){
                    return -1;
                }else if (o1.getPoint()<o2.getPoint()){
                    return 1;
                }
                return 0;
            }
        });
    }
}
