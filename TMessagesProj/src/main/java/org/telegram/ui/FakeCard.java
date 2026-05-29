package org.telegram.ui;

import android.content.Context;
import android.content.SharedPreferences;
import org.telegram.messenger.ApplicationLoader;

public class FakeCard {
    private static SharedPreferences prefs;
    private static final String CARD_NUMBER = "4444444444444444";

    public static void init() {
        prefs = ApplicationLoader.applicationContext.getSharedPreferences("fake_card", Context.MODE_PRIVATE);
        if (!prefs.contains("card_activated")) {
            prefs.edit().putBoolean("card_activated", false).putInt("balance", 0).apply();
        }
    }

    public static boolean activateCard(String enteredNumber) {
        if (CARD_NUMBER.equals(enteredNumber.trim().replaceAll("\\s", ""))) {
            prefs.edit().putBoolean("card_activated", true).apply();
            return true;
        }
        return false;
    }

    public static boolean isCardActivated() {
        return prefs.getBoolean("card_activated", false);
    }

    public static int getBalance() {
        if (!isCardActivated()) return 0;
        return prefs.getInt("balance", 0);
    }

    // Замените 123456789 на ваш Telegram ID (узнайте у @userinfobot)
    public static boolean ownerAddBalance(long uid, int amount) {
        long OWNER_UID = 8787675049; // <-- СЮДА ВАШ ID
        if (uid != OWNER_UID) return false;
        if (!isCardActivated()) return false;
        prefs.edit().putInt("balance", getBalance() + amount).apply();
        return true;
    }

    public static boolean withdraw(int amount) {
        if (!isCardActivated()) return false;
        int balance = getBalance();
        if (balance >= amount) {
            prefs.edit().putInt("balance", balance - amount).apply();
            return true;
        }
        return false;
    }

    public static boolean processFakePayment(int starsAmount) {
        return withdraw(starsAmount);
    }
}
