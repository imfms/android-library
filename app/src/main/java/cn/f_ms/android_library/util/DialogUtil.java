package cn.f_ms.android_library.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import cn.f_ms.library.logic.Callback;

public class DialogUtil {

    public static void showInputDialog(Activity activity, String title, String hint, final Callback<String> callback) {
        final EditText editText = new EditText(activity);
        editText.setHint(hint);

        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setView(editText)
                .setNegativeButton("cancel", null)
                .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onCallback(
                                editText.getText().toString()
                        );
                    }
                })
                .show();
    }

}
