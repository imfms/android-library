package cn.f_ms.android.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * 开放生命周期的基础activity
 * todo wait add test
 *
 * @author f_ms
 * @date 18-4-21
 */
abstract class AbstractLifecycleActivity extends AppCompatActivity implements ActivityLifecycleEventProvider.Owner,
        ActivityLifecycleEventProvider.StartActivityForResultInvokeProxy, ActivityLifecycleEventProvider.RequestPermissionInvokeProxy {

    private ActivityLifecycleEventProviderProxy mActivityEventProvider
            = new ActivityLifecycleEventProviderProxy(this, this);

    @Override
    public final ActivityLifecycleEventProvider getActivityEventProvider() {
        return mActivityEventProvider;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityEventProvider.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mActivityEventProvider.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mActivityEventProvider.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mActivityEventProvider.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        mActivityEventProvider.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mActivityEventProvider.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mActivityEventProvider.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mActivityEventProvider.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mActivityEventProvider.onKeyDown(keyCode, event)) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mActivityEventProvider.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mActivityEventProvider.onConfigurationChanged(newConfig);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        mActivityEventProvider.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
