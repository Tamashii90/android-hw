package com.example.wmshw;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MyUtils {
    public static boolean hasEmptyString(String... strings) {
        for (String string : strings) {
            if (string.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
