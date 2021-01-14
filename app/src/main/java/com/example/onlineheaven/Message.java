package com.example.onlineheaven;

import android.content.Context;
import android.text.Html;
import android.widget.Toast;

public class Message {

    public static void longMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void shortMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

}
