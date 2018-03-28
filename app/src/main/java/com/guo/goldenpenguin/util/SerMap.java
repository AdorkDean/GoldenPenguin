package com.guo.goldenpenguin.util;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/8.
 */
public class SerMap  implements Serializable {
    public Map map;
    public  SerMap(){

    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;

    }
}