package cn.f_ms.android.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.view.KeyEvent;

/**
 * Fragment Event Provider
 * <p>
 * 用于提供给'需要fragment各事件'的非fragment组件
 * @author imf_m
 */
public interface FragmentLifecycleEventProvider {

    /**
     * Provider FragmentLifecycleEventProvider
     */
    interface Owner {

        /**
         * get an FragmentLifecycleEventProvider
         * @return FragmentLifecycleEventProvider
         */
        FragmentLifecycleEventProvider getFragmentEventProvider();
    }

    /**
     * fragment lifecycle current state provider
     */
    interface LifecycleCurrentStateProvider {

        /**
         * Lifecycle State
         */
        enum State {
            /**
             * before attach
             */
            BEFORE_ATTACH,
            /**
             * after attach
             */
            ATTACHED,
            /**
             * after onCreate
             */
            CREATED,
            /**
             * after onActivityCreated
             */
            ACTIVITY_CREATED,
            /**
             * after onStart
             */
            STARTED,
            /**
             * after onResume
             */
            RESUMED,
            /**
             * after paused
             */
            PAUSED,
            /**
             * after onStop
             */
            STOPED,
            /**
             * after onDestroyView
             */
            VIEW_DESTROYED,
            /**
             * after onDestory
             */
            DESTORYED,
            /**
             * after onDetach
             */
            DETACHED
        }

        /**
         * get fragment lifecycle current state
         * @return fragment lifecycle current state
         */
        State getState();
    }

    /**
     * Request permission proxy
     */
    interface RequestPermissionInvokeProxy {

        /**
         * call real fragment's requestPermissions method
         * see detail in {@link android.app.Activity#requestPermissions(String[], int)}
         */
        void requestPermissions(@NonNull String[] permissions, int requestCode);
    }

    /**
     * StartActivityForResult proxy
     */
    interface StartActivityForResultInvokeProxy {

        /**
         * call real activity's startActivityFormResult method
         * see detail in {@link android.app.Activity#startActivityForResult(Intent, int)}
         */
        void startActivityForResult(@RequiresPermission Intent intent, int requestCode);
    }

    /**
     * OnKeyDownInterceptor
     * <p>
     * see detail in {@link android.app.Activity#onKeyDown(int, KeyEvent)}
     */
    interface OnKeyDownInterceptor {
        /**
         * see detail in {@link android.app.Activity#onKeyDown(int, KeyEvent)}
         */
        boolean onKeyDown(int keyCode, KeyEvent event);
    }

    /**
     * OnRequestPermissionsResultObserver
     * <p>
     * see detail in {@link android.app.Activity#onRequestPermissionsResult(int, String[], int[])}
     */
    interface OnRequestPermissionsResultObserver {
        /**
         * see detail in {@link android.app.Activity#onRequestPermissionsResult(int, String[], int[])}
         */
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    }

    /**
     * OnActivityResultObserver
     * <p>
     * see detail in {@link android.app.Activity#onActivityResult(int, int, Intent)}
     */
    interface OnActivityResultObserver {
        /**
         * see detail in {@link android.app.Activity#onActivityResult(int, int, Intent)}
         */
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    /**
     * OnConfigurationChangedObserver
     * <p>
     * see detail in {@link android.app.Activity#onConfigurationChanged(Configuration)}
     */
    interface OnConfigurationChangedObserver {
        /**
         * see detail in {@link android.app.Activity#onConfigurationChanged(Configuration)}
         */
        void onConfigurationChanged(Configuration newConfig);
    }

    /**
     * Fragment Lifecycle Observer
     */
    interface LifecycleObserver {

        /**
         * see detail in {@link android.support.v4.app.Fragment#onAttach(Context)}
         */
        void onAttach(Context context);

        /**
         * see detail in {@link android.support.v4.app.Fragment#onCreate(Bundle)}
         */
        void onCreate(@Nullable Bundle savedInstanceState);

        /**
         * see detail in {@link android.support.v4.app.Fragment#onActivityCreated(Bundle)}
         */
        void onActivityCreated(@Nullable Bundle savedInstanceState);

        /**
         * see detail in {@link android.support.v4.app.Fragment#onStart()}
         */
        void onStart();

        /**
         * see detail in {@link android.support.v4.app.Fragment#onResume()}
         */
        void onResume();

        /**
         * see detail in {@link android.support.v4.app.Fragment#onPause()}
         */
        void onPause();

        /**
         * see detail in {@link android.support.v4.app.Fragment#onStop()}
         */
        void onStop();

        /**
         * see detail in {@link android.support.v4.app.Fragment#onDestroy()}
         */
        void onDestroy();

        /**
         * see detail in {@link android.support.v4.app.Fragment#onDestroy()}
         */
        void onDestroyView();

        /**
         * see detail in {@link android.support.v4.app.Fragment#onDetach()}
         */
        void onDetach();
    }

    abstract class SimpleLifecycleObserver implements LifecycleObserver {
        @Override
        public void onAttach(Context context) {

        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {

        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        }

        @Override
        public void onStart() {

        }

        @Override
        public void onResume() {

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

        @Override
        public void onDestroyView() {

        }

        @Override
        public void onDetach() {

        }
    }

    /**
     * get fragment's lifecycle's current state provider
     * @return activity's lifecycle's current state provider
     */
    LifecycleCurrentStateProvider getLifecycleCurrentStateProvider();

    /**
     * subscribe activity's lifecycler event
     * <p>
     * see detail in {@link android.app.Activity#onCreate(Bundle)}
     * {@link android.app.Activity#onPostCreate(Bundle)}
     * {@link android.app.Activity#onStart()}
     * {@link android.app.Activity#onResume()}
     * {@link android.app.Activity#onPostResume()}
     * {@link android.app.Activity#onPause()}
     * {@link android.app.Activity#onStop()}
     * {@link android.app.Activity#onDestroy()}
     *
     * @param lifecycleObserver lifecycleObserver
     */
    void addLifecyclerObserver(LifecycleObserver lifecycleObserver);

    /**
     * unsubscribe activity's lifecycler event
     * <p>
     * see detail in
     * {@link android.app.Activity#onCreate(Bundle)}
     * {@link android.app.Activity#onPostCreate(Bundle)}
     * {@link android.app.Activity#onStart()}
     * {@link android.app.Activity#onResume()}
     * {@link android.app.Activity#onPostResume()}
     * {@link android.app.Activity#onPause()}
     * {@link android.app.Activity#onStop()}
     * {@link android.app.Activity#onDestroy()}
     *
     * @param lifecycleObserver lifecycleObserver
     */
    void removeLifecyclerObserver(LifecycleObserver lifecycleObserver);

    /**
     * add activity's onKeyDown event interceptor
     * <p>
     * see detail in {@link android.app.Activity#onKeyDown(int, KeyEvent)}
     *
     * @param onKeyDownInterceptor onKeyDownInterceptor
     */
    void addOnKeyDownInterceptor(OnKeyDownInterceptor onKeyDownInterceptor);

    /**
     * remove activity's onKeyDown event interceptor
     * <p>
     * see detail in {@link android.app.Activity#onKeyDown(int, KeyEvent)}
     *
     * @param onKeyDownInterceptor onKeyDownInterceptor
     */
    void removeOnKeyDownInterceptor(OnKeyDownInterceptor onKeyDownInterceptor);

    /**
     * subscribe activity's onRequestPermissionsResult event
     * <p>
     * see detail in {@link android.app.Activity#onRequestPermissionsResult(int, String[], int[])}
     *
     * @param onRequestPermissionsResultObserver onRequestPermissionsResultObserver
     */
    void addOnRequestPermissionsResultObserver(OnRequestPermissionsResultObserver onRequestPermissionsResultObserver);

    /**
     * unsubscribe activity's onRequestPermissionsResult event
     * <p>
     * see detail in {@link android.app.Activity#onRequestPermissionsResult(int, String[], int[])}
     *
     * @param onRequestPermissionsResultObserver onRequestPermissionsResultObserver
     */
    void removeOnRequestPermissionsResultObserver(OnRequestPermissionsResultObserver onRequestPermissionsResultObserver);

    /**
     * subscribe activity's onActivityResult event
     * <p>
     * see detail in {@link android.app.Activity#onActivityResult(int, int, Intent)}
     *
     * @param onActivityResultObserver onActivityResultObserver
     */
    void addOnActivityResultObserver(OnActivityResultObserver onActivityResultObserver);

    /**
     * unsubscribe activity's onActivityResult event
     * <p>
     * see detail in {@link android.app.Activity#onActivityResult(int, int, Intent)}
     *
     * @param onActivityResultObserver onActivityResultObserver
     */
    void removeOnActivityResultObserver(OnActivityResultObserver onActivityResultObserver);

    /**
     * subscribe activity's onConfigurationChanged event
     * <p>
     * see detail in {@link android.app.Activity#onConfigurationChanged(Configuration)}
     *
     * @param onConfigurationChangedObserver onConfigurationChangedObserver
     */
    void addOnConfigurationChangedObserver(OnConfigurationChangedObserver onConfigurationChangedObserver);

    /**
     * unsubscribe activity's onConfigurationChanged event
     * <p>
     * see detail in {@link android.app.Activity#onConfigurationChanged(Configuration)}
     *
     * @param onConfigurationChangedObserver onConfigurationChangedObserver
     */
    void removeOnConfigurationChangedObserver(OnConfigurationChangedObserver onConfigurationChangedObserver);

    /**
     * get request permissions proxy
     *
     * @return requestPermissionProxy
     */
    RequestPermissionInvokeProxy getRequestPermissionsProxy();

    /**
     * get startActivityForResult proxy
     *
     * @return startActivityForResult proxy
     */
    StartActivityForResultInvokeProxy getStartActivityFormResultProxy();
}