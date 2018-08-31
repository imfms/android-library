package cn.f_ms.android_library.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.io.Serializable;

import cn.f_ms.android.ui.AbstractArgumentActivity;
import cn.f_ms.android.ui.ArgumentException;

/**
 * @author f_ms
 * @date 18-4-21
 */
public class ArgumentActivity extends AbstractArgumentActivity<ArgumentActivity.Arg> {

    public static class Arg implements Serializable {
        public String displayContent;
    }

    public static Intent newIntent(Activity activity, ArgumentActivity.Arg arg) {
        return newIntent(activity, ArgumentActivity.class, arg);
    }

    @Override
    protected void checkArgument(@Nullable ArgumentActivity.Arg arg) throws ArgumentException {
        super.checkArgument(arg);

        if (arg == null) {
            throw new ArgumentException("arg can't be null");
        }

    }

    @Override
    protected void onCreate(ArgumentActivity.Arg argument, @Nullable Bundle savedInstanceState) {
        super.onCreate(argument, savedInstanceState);

        TextView view = new TextView(this);
        setContentView(view);

        view.setText(argument.displayContent);
    }
}
