package com.example.android.snoopyea.utils;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;


public class LanguageHelper {
    public static void chengeLanguage(Resources res, String locate) {
        Configuration config = new Configuration(res.getConfiguration());

        switch (locate)
        {
            case "ru":
                config.locale = new Locale("ru");
                break;
            default:
                config.locale = Locale.ENGLISH;
                break;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());

    }
}
