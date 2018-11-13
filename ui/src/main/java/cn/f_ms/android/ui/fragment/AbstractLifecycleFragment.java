package cn.f_ms.android.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import cn.f_ms.android.ui.activity.AbstractActivity;
import cn.f_ms.android.ui.activity.ActivityLifecycleEventProvider;

/**
 * 开放生命周期的基础fragment
 * todo wait add test
 *
 * @author f_ms
 * @date 18-11-13
 */
abstract class AbstractLifecycleFragment extends Fragment implements FragmentLifecycleEventProvider.Owner,
        FragmentLifecycleEventProvider.StartActivityForResultInvokeProxy, FragmentLifecycleEventProvider.RequestPermissionInvokeProxy {

    private FragmentLifecycleEventProviderProxy mFragmentEventProvider
            = new FragmentLifecycleEventProviderProxy(this, this);

    @Override
    public final FragmentLifecycleEventProvider getFragmentEventProvider() {
        return mFragmentEventProvider;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof AbstractActivity) {
            ((AbstractActivity) getActivity()).getActivityEventProvider()
                    .addOnKeyDownInterceptor(new ActivityLifecycleEventProvider.OnKeyDownInterceptor() {
                        @Override
                        public boolean onKeyDown(int keyCode, KeyEvent event) {
                            return mFragmentEventProvider.onKeyDown(keyCode, event);
                        }
                    });
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentEventProvider.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmentEventProvider.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mFragmentEventProvider.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentEventProvider.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mFragmentEventProvider.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFragmentEventProvider.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFragmentEventProvider.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentEventProvider.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentEventProvider.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFragmentEventProvider.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mFragmentEventProvider.onConfigurationChanged(newConfig);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mFragmentEventProvider.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
