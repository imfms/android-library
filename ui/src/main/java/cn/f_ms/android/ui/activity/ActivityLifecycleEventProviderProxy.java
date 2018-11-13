package cn.f_ms.android.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArraySet;
import android.view.KeyEvent;

import java.util.Iterator;
import java.util.Set;

/**
 * ActivityLifecycleEventProvider proxy implememnt
 *
 * @author imf_m
 * @date 2018/4/23
 */
public class ActivityLifecycleEventProviderProxy implements ActivityLifecycleEventProvider {

    /**
     * RequestPermissionInvokeProxy
     */
    private final RequestPermissionInvokeProxy mRequestPermissionInvokeProxy;

    /**
     * StartActivityForResultInvokeProxy
     */
    private StartActivityForResultInvokeProxy mStartActivityForResultInvokeProxy;

    private LifecycleCurrentStateProvider.State mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.BEFORE_CREATE;

    private ArraySet<LifecycleObserver> mLifecycleObservers;
    private ArraySet<OnKeyDownInterceptor> mOnKeyDownIterceptors;
    private ArraySet<OnActivityResultObserver> mOnActivityResultObservers;
    private ArraySet<OnConfigurationChangedObserver> mOnConfigurationChangedObservers;
    private ArraySet<OnRequestPermissionsResultObserver> mOnRequestPermissionsResultObservers;

    /**
     * get a new instance
     *
     * @param requestPermissionProxy      requestPermissionProxy
     * @param startActivityForResultProxy startActivityForResultProxy
     */
    public ActivityLifecycleEventProviderProxy(StartActivityForResultInvokeProxy startActivityForResultProxy, RequestPermissionInvokeProxy requestPermissionProxy) {

        if (startActivityForResultProxy == null) {
            throw new IllegalArgumentException("startActivityForResultProxy can't be null");
        }

        if (requestPermissionProxy == null) {
            throw new IllegalArgumentException("requestPermissionProxy can't be null");
        }

        mStartActivityForResultInvokeProxy = startActivityForResultProxy;
        mRequestPermissionInvokeProxy = requestPermissionProxy;
    }

    @Override
    public LifecycleCurrentStateProvider getLifecycleCurrentStateProvider() {
        return new LifecycleCurrentStateProvider() {
            @Override
            public State getState() {
                return mActivityCurrentLifecycleState;
            }
        };
    }

    @Override
    public void addLifecyclerObserver(LifecycleObserver lifecycleObserver) {
        if (lifecycleObserver == null) {
            return;
        }
        if (mLifecycleObservers == null) {
            mLifecycleObservers = new ArraySet<>();
        }
        mLifecycleObservers.add((lifecycleObserver));
    }

    @Override
    public void removeLifecyclerObserver(LifecycleObserver lifecycleObserver) {
        removeObserver(mLifecycleObservers, lifecycleObserver);
    }

    @Override
    public void addOnKeyDownInterceptor(OnKeyDownInterceptor onKeyDownInterceptor) {
        if (onKeyDownInterceptor == null) {
            return;
        }
        if (mOnKeyDownIterceptors == null) {
            mOnKeyDownIterceptors = new ArraySet<>();
        }
        mOnKeyDownIterceptors.add((onKeyDownInterceptor));
    }

    @Override
    public void removeOnKeyDownInterceptor(OnKeyDownInterceptor onKeyDownInterceptor) {
        removeObserver(mOnKeyDownIterceptors, onKeyDownInterceptor);
    }

    @Override
    public void addOnRequestPermissionsResultObserver(OnRequestPermissionsResultObserver onRequestPermissionsResultObserver) {

        if (onRequestPermissionsResultObserver == null) {
            return;
        }
        if (mOnRequestPermissionsResultObservers == null) {
            mOnRequestPermissionsResultObservers = new ArraySet<>();
        }
        mOnRequestPermissionsResultObservers.add((onRequestPermissionsResultObserver));
    }

    @Override
    public void removeOnRequestPermissionsResultObserver(OnRequestPermissionsResultObserver onRequestPermissionsResultObserver) {
        removeObserver(mOnRequestPermissionsResultObservers, onRequestPermissionsResultObserver);
    }

    @Override
    public void addOnActivityResultObserver(OnActivityResultObserver onActivityResultObserver) {
        if (onActivityResultObserver == null) {
            return;
        }
        if (mOnActivityResultObservers == null) {
            mOnActivityResultObservers = new ArraySet<>();
        }
        mOnActivityResultObservers.add((onActivityResultObserver));
    }

    @Override
    public void removeOnActivityResultObserver(OnActivityResultObserver onActivityResultObserver) {
        removeObserver(mOnActivityResultObservers, onActivityResultObserver);
    }

    @Override
    public void addOnConfigurationChangedObserver(OnConfigurationChangedObserver onConfigurationChangedObserver) {
        if (onConfigurationChangedObserver == null) {
            return;
        }
        if (mOnConfigurationChangedObservers == null) {
            mOnConfigurationChangedObservers = new ArraySet<>();
        }
        mOnConfigurationChangedObservers.add((onConfigurationChangedObserver));
    }

    @Override
    public void removeOnConfigurationChangedObserver(OnConfigurationChangedObserver onConfigurationChangedObserver) {
        removeObserver(mOnConfigurationChangedObservers, onConfigurationChangedObserver);
    }


    /**
     * get request permissions proxy
     *
     * @return requestPermissionProxy
     */
    @Override
    public final RequestPermissionInvokeProxy getRequestPermissionsProxy() {
        return mRequestPermissionInvokeProxy;
    }

    /**
     * get startActivityForResult proxy
     *
     * @return startActivityForResult proxy
     */
    @Override
    public final StartActivityForResultInvokeProxy getStartActivityFormResultProxy() {
        return mStartActivityForResultInvokeProxy;
    }

    /**
     * onCreate proxy, please call me when event happend
     */
    public final void onCreate(@Nullable Bundle savedInstanceState) {

        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.CREATED;

        if (mLifecycleObservers == null) {
            return;
        }

        Iterator<LifecycleObserver> iterator = mLifecycleObservers.iterator();
        while (iterator.hasNext()) {
            LifecycleObserver observer = iterator.next();
            if (observer == null) {
                iterator.remove();
                continue;
            }

            observer.onCreate(savedInstanceState);
        }
    }

    /**
     * onPostCreate proxy, please call me when event happend
     */
    public final void onPostCreate(@Nullable Bundle savedInstanceState) {
        if (mLifecycleObservers == null) {
            return;
        }

        Iterator<LifecycleObserver> iterator = mLifecycleObservers.iterator();
        while (iterator.hasNext()) {
            LifecycleObserver observer = iterator.next();
            if (observer == null) {
                iterator.remove();
                continue;
            }

            observer.onPostCreate(savedInstanceState);
        }
    }

    /**
     * onStart proxy, please call me when event happend
     */
    public final void onStart() {

        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.STARTED;

        if (mLifecycleObservers == null) {
            return;
        }

        Iterator<LifecycleObserver> iterator = mLifecycleObservers.iterator();
        while (iterator.hasNext()) {
            LifecycleObserver observer = iterator.next();
            if (observer == null) {
                iterator.remove();
                continue;
            }

            observer.onStart();
        }
    }

    /**
     * onResume proxy, please call me when event happend
     */
    public final void onResume() {

        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.RESUMED;

        if (mLifecycleObservers == null) {
            return;
        }

        Iterator<LifecycleObserver> iterator = mLifecycleObservers.iterator();
        while (iterator.hasNext()) {
            LifecycleObserver observer = iterator.next();
            if (observer == null) {
                iterator.remove();
                continue;
            }

            observer.onResume();
        }
    }

    /**
     * onPostResume proxy, please call me when event happend
     */
    public final void onPostResume() {
        if (mLifecycleObservers == null) {
            return;
        }

        Iterator<LifecycleObserver> iterator = mLifecycleObservers.iterator();
        while (iterator.hasNext()) {
            LifecycleObserver observer = iterator.next();
            if (observer == null) {
                iterator.remove();
                continue;
            }

            observer.onPostResume();
        }
    }

    /**
     * onPause proxy, please call me when event happend
     */
    public final void onPause() {

        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.PAUSED;

        if (mLifecycleObservers == null) {
            return;
        }

        Iterator<LifecycleObserver> iterator = mLifecycleObservers.iterator();
        while (iterator.hasNext()) {
            LifecycleObserver observer = iterator.next();
            if (observer == null) {
                iterator.remove();
                continue;
            }

            observer.onPause();
        }
    }

    /**
     * onStop proxy, please call me when event happend
     */
    public final void onStop() {

        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.STOPED;

        if (mLifecycleObservers == null) {
            return;
        }

        Iterator<LifecycleObserver> iterator = mLifecycleObservers.iterator();
        while (iterator.hasNext()) {
            LifecycleObserver observer = iterator.next();
            if (observer == null) {
                iterator.remove();
                continue;
            }

            observer.onStop();
        }
    }

    /**
     * onDestroy proxy, please call me when event happend
     */
    public final void onDestroy() {

        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.DESTORYED;

        if (mLifecycleObservers == null) {
            return;
        }

        Iterator<LifecycleObserver> iterator = mLifecycleObservers.iterator();
        while (iterator.hasNext()) {
            LifecycleObserver observer = iterator.next();
            if (observer == null) {
                iterator.remove();
                continue;
            }

            observer.onDestroy();
        }
    }

    /**
     * onKeyDown proxy, please call me when event happend
     */
    public final boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mOnKeyDownIterceptors == null) {
            return false;
        }

        for (int i = mOnKeyDownIterceptors.size() - 1; i >= 0; i--) {
            OnKeyDownInterceptor observerWeak = mOnKeyDownIterceptors.valueAt(i);
            if (observerWeak == null) {
                continue;
            }
            OnKeyDownInterceptor onKeyDownInterceptor = observerWeak;
            if (onKeyDownInterceptor == null) {
                mOnKeyDownIterceptors.remove(observerWeak);
                continue;
            }

            if (onKeyDownInterceptor.onKeyDown(keyCode, event)) {
                return true;
            }
        }

        return false;
    }

    /**
     * onRequestPermissionsResult proxy, please call me when event happend
     */
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mOnRequestPermissionsResultObservers == null) {
            return;
        }

        Iterator<OnRequestPermissionsResultObserver> iterator = mOnRequestPermissionsResultObservers.iterator();
        while (iterator.hasNext()) {
            OnRequestPermissionsResultObserver observer = iterator.next();
            if (observer == null) {
                iterator.remove();
                continue;
            }

            observer.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * onActivityResult proxy, please call me when event happend
     */
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mOnActivityResultObservers == null) {
            return;
        }

        Iterator<OnActivityResultObserver> iterator = mOnActivityResultObservers.iterator();
        while (iterator.hasNext()) {
            OnActivityResultObserver observer = iterator.next();
            if (observer == null) {
                iterator.remove();
                continue;
            }

            observer.onActivityResult(requestCode, resultCode, data);
        }
    }


    /**
     * onConfigurationChanged proxy, please call me when event happend
     */
    public final void onConfigurationChanged(Configuration newConfig) {
        if (mOnConfigurationChangedObservers == null) {
            return;
        }

        Iterator<OnConfigurationChangedObserver> iterator = mOnConfigurationChangedObservers.iterator();
        while (iterator.hasNext()) {
            OnConfigurationChangedObserver observer = iterator.next();
            if (observer == null) {
                iterator.remove();
                continue;
            }

            observer.onConfigurationChanged(newConfig);
        }
    }

    private <T> void removeObserver(Set<T> list, T observer) {

        if (list == null
                || observer == null) {
            return;
        }

        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T sourceObserver = iterator.next();
            if (sourceObserver == null) {
                iterator.remove();
                continue;
            }
            if (sourceObserver == observer) {
                iterator.remove();
                break;
            }
        }
    }
}
