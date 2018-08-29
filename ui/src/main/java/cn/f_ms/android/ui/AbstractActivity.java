package cn.f_ms.android.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * Desc: activity基本基类
 * todo wait add test
 *
 * @author f_ms
 * @date 18-4-21
 */
public abstract class AbstractActivity extends AppCompatActivity implements LifecycleEventProvider.Owner,
        LifecycleEventProvider.StartActivityForResultInvokeProxy, LifecycleEventProvider.RequestPermissionInvokeProxy {

    private LifecycleEventProviderProxy mActivityEventProvider
            = new LifecycleEventProviderProxy(this, this);

    /**
     * 获取当前activity实例, 主要服务于于内部类中获取activity实例的需求
     *
     * @return 当前activity实例
     */
    protected AbstractActivity getActivity() {
        return this;
    }

    @Override
    public final LifecycleEventProvider getActivityEventProvider() {
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
