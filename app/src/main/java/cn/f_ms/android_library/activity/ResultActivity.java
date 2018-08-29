package cn.f_ms.android_library.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import java.io.Serializable;

import cn.f_ms.android.ui.AbstractResultActivity;

/**
 * @author f_ms
 * @date 18-4-21
 */
public class ResultActivity extends AbstractResultActivity<ResultActivity.Result> {

    public static Result getResult(Intent intent) {
        return AbstractResultActivity.getResult(intent);
    }

    public static class Result implements Serializable {

        public static final int RESULT_CODE = 2134;

        private int selectPosition;

        public Result(int selectPosition) {
            this.selectPosition = selectPosition;
        }

        public int getSelectPosition() {
            return selectPosition;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String[] items = new String[10];
        for (int i = 0; i < 10; i++) {
            items[i] = String.valueOf(i);
        }

        new AlertDialog.Builder(this)
                .setTitle("please select a position")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(Result.RESULT_CODE, new Result(which));
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                })
                .show();

    }
}
