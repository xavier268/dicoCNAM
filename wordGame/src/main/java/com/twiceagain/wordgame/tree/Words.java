/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.wordgame.tree;

/**
 * Blocked words, and supplémentary words.
 *
 * @author xavier
 */
public class Words {

    /**
     * Chek validity of word read from dictionnary.
     *
     * @param w
     * @return
     */
    public static boolean valid(String w) {
        return !(w == null
                || "pc".equals(w)
                || "bd".equals(w)
                || "dl".equals(w)
                || "km".equals(w));
    }

}
