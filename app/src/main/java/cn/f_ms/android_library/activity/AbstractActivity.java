package cn.f_ms.android_library.activity;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.f_ms.android.ui.LifecycleEventProvider;

/**
 * @author f_ms
 * @date 18-4-25
 */
public class AbstractActivity extends cn.f_ms.android.ui.AbstractActivity {

    private LinearLayout mRootView;
    private LifecycleEventProvider.LifecycleObserver lifecycleObserver;
    private LifecycleEventProvider.OnKeyDownInterceptor onKeyDown;
    private LifecycleEventProvider.OnActivityResultObserver onActivityResult;
    private LifecycleEventProvider.OnConfigurationChangedObserver onConfigurationChanged;
    private LifecycleEventProvider.OnRequestPermissionsResultObserver onRequestPermissionsResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRootView = new LinearLayout(getActivity());
        mRootView.setOrientation(LinearLayout.VERTICAL);
        setContentView(mRootView);

        Button btnStartActivityForResult = new Button(getActivity());
        btnStartActivityForResult.setText("StartActivityForResult");
        mRootView.addView(btnStartActivityForResult);

        btnStartActivityForResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivityEventProvider().getStartActivityFormResultProxy()
                        .startActivityForResult(new Intent(getActivity(), AbstractActivity.class), new Random().nextInt(10000));
            }
        });

        Button btnRequestPermission = new Button(getActivity());
        btnRequestPermission.setText("RequestPermission");
        mRootView.addView(btnRequestPermission);

        btnRequestPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivityEventProvider().getRequestPermissionsProxy()
                        .requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, new Random().nextInt(10000));
            }
        });

        lifecycleObserver = new LifecycleEventProvider.LifecycleObserver() {
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

        onKeyDown = new LifecycleEventProvider.OnKeyDownInterceptor() {
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                addText("onKeyDown", event.toString());
                return false;
            }
        };
        getActivityEventProvider().addOnKeyDownInterceptor(onKeyDown);

        onActivityResult = new LifecycleEventProvider.OnActivityResultObserver() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                addText("onActivityResult", String.format(
                        "requestCode %s resultCode %s data %s", requestCode, resultCode, data
                ));
            }
        };
        getActivityEventProvider().addOnActivityResultObserver(onActivityResult);

        onConfigurationChanged = new LifecycleEventProvider.OnConfigurationChangedObserver() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                addText("onConfigurationChanged", newConfig.toString());
            }
        };
        getActivityEventProvider().addOnConfigurationChangedObserver(onConfigurationChanged);

        onRequestPermissionsResult = new LifecycleEventProvider.OnRequestPermissionsResultObserver() {
            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                addText("onRequestPermissionsResult", String.format(
                        "requestCode %s permissions %s grantResults %s", requestCode, permissions, grantResults
                ));
            }
        };
        getActivityEventProvider().addOnRequestPermissionsResultObserver(onRequestPermissionsResult);


        new Thread(){
            @Override
            public void run() {
                super.run();

                while (true) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivityEventProvider().addLifecyclerObserver(new LifecycleEventProvider.LifecycleObserver() {

                        private List a = new ArrayList(10000);

                        @Override
                        public void onCreate(@Nullable Bundle savedInstanceState) {

                        }

                        @Override
                        public void onPostCreate(@Nullable Bundle savedInstanceState) {

                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onResume() {

                        }

                        @Override
                        public void onPostResume() {

                        }

                        @Override
                        public void onPause() {

                        }

                        @Override
                        public void onStop() {

                        }

                        @Override
                        public void onDestroy() {

                        }
                    });
                }

            }
        }.start();

    }

    private void addText(String tag, String msg) {
        TextView textView = new TextView(getActivity());

        textView.setText(tag + ": " + msg);

        mRootView.addView(textView, 2);
    }
}
