package cn.f_ms.android_library.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Random;

import cn.f_ms.android.ui.exception.ArgumentException;
import cn.f_ms.android.ui.activity.ActivityLifecycleEventProvider;
import cn.f_ms.android_library.R;

/**
 * @author f_ms
 * @date 18-4-25
 */
public class AbstractActivity extends cn.f_ms.android.ui.activity.AbstractActivity<AbstractActivity.Arg, AbstractActivity.Result, AbstractActivity.State> {

    public static class Arg implements Serializable {
        public String arg;
    }

    public static class Result implements Serializable {

        public static final int RESULT_CODE = 34;

        public String[] logs;
    }

    static class State implements Serializable {
        private String[] logs;
    }

    public static Intent newIntent(Activity activity, Arg arg) {
        return newIntent(activity, AbstractActivity.class, arg);
    }

    public static Result getResult(Intent intent) {
        return cn.f_ms.android.ui.activity.AbstractActivity.getResult(intent);
    }

    public static Result getResult(Bundle bundle) {
        return cn.f_ms.android.ui.activity.AbstractActivity.getResult(bundle);
    }

    private LinearLayout llEventsContainer;
    private ActivityLifecycleEventProvider.LifecycleObserver lifecycleObserver;
    private ActivityLifecycleEventProvider.OnKeyDownInterceptor onKeyDown;
    private ActivityLifecycleEventProvider.OnActivityResultObserver onActivityResult;
    private ActivityLifecycleEventProvider.OnConfigurationChangedObserver onConfigurationChanged;
    private ActivityLifecycleEventProvider.OnRequestPermissionsResultObserver onRequestPermissionsResult;

    @Override
    protected Arg checkArgument(@Nullable Arg arg) throws Exception {
        super.checkArgument(arg);
        if (arg == null
                || TextUtils.isEmpty(arg.arg)) {
            throw new ArgumentException("arg can't be empty");
        }
        return arg;
    }

    @Override
    protected void onCreate(@Nullable Arg argument, @Nullable State savedInstanceState, @Nullable Bundle savedInstanceStateBundle) {
        super.onCreate(argument, savedInstanceState, savedInstanceStateBundle);

        setContentView(R.layout.ui_activity_abstract);

        llEventsContainer = findViewById(R.id.llEventsContainer);

        this.<TextView>findViewById(R.id.tvArgument).setText("argument: " + argument.arg);

        findViewById(R.id.btnStartActivityForResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), AbstractActivity.class), new Random().nextInt(10000));
            }
        });

        findViewById(R.id.btnRequestPermission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivityEventProvider().getRequestPermissionsProxy()
                        .requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, new Random().nextInt(10000));
            }
        });

        findViewById(R.id.btnFinishWithResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result result = new Result();
                result.logs = getEventLogs();
                finish(Result.RESULT_CODE, result);
            }
        });

        lifecycleObserver = new ActivityLifecycleEventProvider.LifecycleObserver() {
            @Override
            public void onCreate(@Nullable Bundle savedInstanceState) {
                addText("lifecycle", "onCreate");
            }

            @Override
            public void onPostCreate(@Nullable Bundle savedInstanceState) {
                addText("lifecycle", "onPostCreate");
            }

            @Override
            public void onStart() {
                addText("lifecycle", "onStart");
                addText("lifecycle-current-state", getActivityEventProvider().getLifecycleCurrentStateProvider().getState().toString());
            }

            @Override
            public void onResume() {
                addText("lifecycle", "onResume");
                addText("lifecycle-current-state", getActivityEventProvider().getLifecycleCurrentStateProvider().getState().toString());
            }

            @Override
            public void onPostResume() {
                addText("lifecycle", "onPostResume");
                addText("lifecycle-current-state", getActivityEventProvider().getLifecycleCurrentStateProvider().getState().toString());
            }

            @Override
            public void onPause() {
                addText("lifecycle", "onPause");
                addText("lifecycle-current-state", getActivityEventProvider().getLifecycleCurrentStateProvider().getState().toString());
            }

            @Override
            public void onStop() {
                addText("lifecycle", "onStop");
                addText("lifecycle-current-state", getActivityEventProvider().getLifecycleCurrentStateProvider().getState().toString());
            }

            @Override
            public void onDestroy() {
                addText("lifecycle", "onDestroy");
                addText("lifecycle-current-state", getActivityEventProvider().getLifecycleCurrentStateProvider().getState().toString());
            }
        };
        getActivityEventProvider().addLifecyclerObserver(lifecycleObserver);

        onKeyDown = new ActivityLifecycleEventProvider.OnKeyDownInterceptor() {
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                addText("onKeyDown", event.toString());
                return false;
            }
        };
        getActivityEventProvider().addOnKeyDownInterceptor(onKeyDown);

        onActivityResult = new ActivityLifecycleEventProvider.OnActivityResultObserver() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                addText("onActivityResult", String.format(
                        "requestCode %s resultCode %s data %s", requestCode, resultCode, data
                ));
            }
        };
        getActivityEventProvider().addOnActivityResultObserver(onActivityResult);

        onConfigurationChanged = new ActivityLifecycleEventProvider.OnConfigurationChangedObserver() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                addText("onConfigurationChanged", newConfig.toString());
            }
        };
        getActivityEventProvider().addOnConfigurationChangedObserver(onConfigurationChanged);

        onRequestPermissionsResult = new ActivityLifecycleEventProvider.OnRequestPermissionsResultObserver() {
            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                addText("onRequestPermissionsResult", String.format(
                        "requestCode %s permissions %s grantResults %s", requestCode, permissions, grantResults
                ));
            }
        };
        getActivityEventProvider().addOnRequestPermissionsResultObserver(onRequestPermissionsResult);
    }

    @Override
    protected void onArgumentExistError(@NonNull Exception exception) {
        new AlertDialog.Builder(getActivity())
                .setTitle("argument error")
                .setMessage(exception.getMessage())
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                })
                .show();
    }

    @Nullable
    @Override
    protected State onSaveInstanceState() {
        State state = new State();

        String[] eventLogs = getEventLogs();

        state.logs = eventLogs;

        return state;
    }

    @NonNull
    private String[] getEventLogs() {
        if (llEventsContainer == null) {
            return new String[0];
        }

        String[] eventLogs = new String[llEventsContainer.getChildCount()];

        for (int i = 0; i < llEventsContainer.getChildCount(); i++) {
            eventLogs[i] = ((TextView)llEventsContainer.getChildAt(i)).getText().toString();
        }
        return eventLogs;
    }

    @Override
    protected void onRestoreInstanceState(State savedInstanceState, Bundle savedInstanceStateBundle) {
        super.onRestoreInstanceState(savedInstanceState, savedInstanceStateBundle);

        if (savedInstanceState != null
                && savedInstanceState.logs != null) {
            for (String log : savedInstanceState.logs) {
                addText(log);
            }
        }
    }

    private void addText(String tag, String msg) {
        addText(tag + ": " + msg);
    }

    private void addText(String content) {
        TextView textView = new TextView(getActivity());
        textView.setText(content);

        llEventsContainer.addView(textView, 0);
    }
}
