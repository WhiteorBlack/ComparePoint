package com.blm.comparepoint.bean;

import java.util.List;

/**
 * Created by 41508 on 2017/3/16.
 */

public class Bean_GameConfig extends BaseBean {
    public List<GameConfig> Data;

    public static class GameConfig {
        public float Ratio;
        public int Number;
        public String Desc;
    }
}
