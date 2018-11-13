package cn.f_ms.android.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArraySet;
import android.view.KeyEvent;

import java.util.Iterator;
import java.util.Set;

import cn.f_ms.library.logic.Callback;

/**
 * FragmentLifecycleEventProvider proxy implememnt
 *
 * @author imf_m
 * @date 2018/11/13
 */
public class FragmentLifecycleEventProviderProxy implements FragmentLifecycleEventProvider {

    /**
     * RequestPermissionInvokeProxy
     */
    private final RequestPermissionInvokeProxy mRequestPermissionInvokeProxy;

    /**
     * StartActivityForResultInvokeProxy
     */
    private StartActivityForResultInvokeProxy mStartActivityForResultInvokeProxy;

    private LifecycleCurrentStateProvider.State mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.BEFORE_ATTACH;

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
    public FragmentLifecycleEventProviderProxy(StartActivityForResultInvokeProxy startActivityForResultProxy, RequestPermissionInvokeProxy requestPermissionProxy) {

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
     * onAttach proxy, please call me when event happend
     */
    public final void onAttach(final Context context) {
        if (mLifecycleObservers != null) {
            lifecycleObserverDo(new Callback<LifecycleObserver>() {
                @Override
                public void onCallback(LifecycleObserver lifecycleObserver) {
                    lifecycleObserver.onAttach(context);
                }
            });
        }
        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.ATTACHED;
    }

    /**
     * onCreate proxy, please call me when event happend
     */
    public final void onCreate(@Nullable final Bundle savedInstanceState) {
        if (mLifecycleObservers != null) {
            lifecycleObserverDo(new Callback<LifecycleObserver>() {
                @Override
                public void onCallback(LifecycleObserver lifecycleObserver) {
                    lifecycleObserver.onCreate(savedInstanceState);
                }
            });
        }
        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.CREATED;
    }

    /**
     * onActivityCreated proxy, please call me when event happend
     */
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        if (mLifecycleObservers != null) {
            lifecycleObserverDo(new Callback<LifecycleObserver>() {
                @Override
                public void onCallback(LifecycleObserver lifecycleObserver) {
                    lifecycleObserver.onActivityCreated(savedInstanceState);
                }
            });
        }
        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.ACTIVITY_CREATED;
    }

    /**
     * onStart proxy, please call me when event happend
     */
    public final void onStart() {
        if (mLifecycleObservers != null) {
            lifecycleObserverDo(new Callback<LifecycleObserver>() {
                @Override
                public void onCallback(LifecycleObserver lifecycleObserver) {
                    lifecycleObserver.onStart();
                }
            });
        }
        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.STARTED;
    }

    /**
     * onResume proxy, please call me when event happend
     */
    public final void onResume() {

        if (mLifecycleObservers != null) {
            lifecycleObserverDo(new Callback<LifecycleObserver>() {
                @Override
                public void onCallback(LifecycleObserver lifecycleObserver) {
                    lifecycleObserver.onResume();
                }
            });
        }

        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.RESUMED;
    }

    /**
     * onPause proxy, please call me when event happend
     */
    public final void onPause() {
        if (mLifecycleObservers != null) {
            lifecycleObserverDo(new Callback<LifecycleObserver>() {
                @Override
                public void onCallback(LifecycleObserver lifecycleObserver) {
                    lifecycleObserver.onPause();
                }
            });
        }
        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.PAUSED;
    }

    /**
     * onStop proxy, please call me when event happend
     */
    public final void onStop() {
        if (mLifecycleObservers != null) {
            lifecycleObserverDo(new Callback<LifecycleObserver>() {
                @Override
                public void onCallback(LifecycleObserver lifecycleObserver) {
                    lifecycleObserver.onStop();
                }
            });
        }
        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.STOPED;
    }

    /**
     * onDestroyView proxy, please call me when event happend
     */
    public void onDestroyView() {
        if (mLifecycleObservers != null) {
            lifecycleObserverDo(new Callback<LifecycleObserver>() {
                @Override
                public void onCallback(LifecycleObserver lifecycleObserver) {
                    lifecycleObserver.onDestroyView();
                }
            });
        }
        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.VIEW_DESTROYED;
    }

    /**
     * onDestroy proxy, please call me when event happend
     */
    public final void onDestroy() {
        if (mLifecycleObservers != null) {
            lifecycleObserverDo(new Callback<LifecycleObserver>() {
                @Override
                public void onCallback(LifecycleObserver lifecycleObserver) {
                    lifecycleObserver.onDestroy();
                }
            });
        }
        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.DESTORYED;
    }

    /**
     * onDetach proxy, please call me when event happend
     */
    public void onDetach() {
        if (mLifecycleObservers != null) {
            lifecycleObserverDo(new Callback<LifecycleObserver>() {
                @Override
                public void onCallback(LifecycleObserver lifecycleObserver) {
                    lifecycleObserver.onDetach();
                }
            });
        }
        mActivityCurrentLifecycleState = LifecycleCurrentStateProvider.State.DETACHED;
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
    public final void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        if (mOnRequestPermissionsResultObservers == null) {
            return;
        }

        observersDo(mOnRequestPermissionsResultObservers, new Callback<OnRequestPermissionsResultObserver>() {
            @Override
            public void onCallback(OnRequestPermissionsResultObserver onRequestPermissionsResultObserver) {
                onRequestPermissionsResultObserver.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        });
    }

    /**
     * onActivityResult proxy, please call me when event happend
     */
    public final void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (mOnActivityResultObservers == null) {
            return;
        }

        observersDo(mOnActivityResultObservers, new Callback<OnActivityResultObserver>() {
            @Override
            public void onCallback(OnActivityResultObserver onActivityResultObserver) {
                onActivityResultObserver.onActivityResult(requestCode, resultCode, data);
            }
        });
    }

    /**
     * onConfigurationChanged proxy, please call me when event happend
     */
    public final void onConfigurationChanged(final Configuration newConfig) {
        if (mOnConfigurationChangedObservers == null) {
            return;
        }

        observersDo(mOnConfigurationChangedObservers, new Callback<OnConfigurationChangedObserver>() {
            @Override
            public void onCallback(OnConfigurationChangedObserver onConfigurationChangedObserver) {
                onConfigurationChangedObserver.onConfigurationChanged(newConfig);
            }
        });
    }

    private void lifecycleObserverDo(Callback<LifecycleObserver> callback) {
        observersDo(mLifecycleObservers, callback);
    }

    private <Observer> void observersDo(Iterable<Observer> observerIterable, Callback<Observer> callback) {

        Iterator<Observer> iterator = observerIterable.iterator();

        while (iterator.hasNext()) {

            Observer observer = iterator.next();
            if (observer == null) {
                iterator.remove();
                continue;
            }

            callback.onCallback(observer);
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
