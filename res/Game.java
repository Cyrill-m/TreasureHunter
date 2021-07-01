/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res;

import javafx.beans.property.IntegerProperty;
import javafx.scene.paint.Color;

/**
 *
 * @author Kiryl Matusevich (cyrill-m@mail.ru)
 */
public class Game {
    public static Color CL_FILL_ACT = Color.rgb(226, 241, 248);
    public static Color CL_FILL_PAS = Color.rgb(54, 54, 54);
    public static Color CL_FILL_CHEST = Color.CORAL;
    public static Color CL_FILL_BASE = Color.rgb(69, 90, 100);

    public static Color CL_BORD_ACT = Color.rgb(162, 162, 0);
    public static Color CL_BORD_PAS = Color.rgb(26, 26, 26);

    public static int MONEY = 20;
    //public static IntegerProperty Money;
    public static int CHEST_FIND = 0;
    public static int CHEST_COUNT = 12;
    
    public static int ROW_COUNT = 9;
    public static int COL_COUNT = 9;
    public static int TILE_MAP_SIZE = 48;
    
    public static Boolean GAME_START = false;
    public static Boolean GAME_MODE = false;
    public static Boolean MAP_MODE = false;
    
    public static final String MSG_NOT_MONEY = "You do not have enough money to buy a plot";
    public static final String MSG_END_GAME = "Congratulations! You found all the chests!";
    public static final String MSG_BREAK_GAME = "Not all chests found! Finish the game?";

        
    public static void StartNewGame(int chests) {
        CHEST_COUNT = chests;
        CHEST_FIND = 0;
        GAME_START = true;
    }
    
    public static boolean EndGame() {
        if (CHEST_COUNT == CHEST_FIND) {
            GAME_START = false;
            return true;
        } else return false;
    }
    
    public static Boolean EnoughMoney(int money) {
        return MONEY >= money;
    }
    
    public static Boolean AllChestsFound() {
        return CHEST_COUNT == CHEST_FIND;
    }
    
    public static String GetMoney() {
        return "" + MONEY;
    }
    
    public static String GetChest() {
        return "" + CHEST_COUNT + " / " + CHEST_FIND;
    }

    public static void ChangeMoney(int delta) {
        MONEY += delta;
        
    }

    public static void ChangeChest() {
        CHEST_FIND++;
        
    }    
}


