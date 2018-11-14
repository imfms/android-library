package cn.f_ms.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.f_ms.android.ui.exception.ArgumentException;
import cn.f_ms.android.ui.util.BundleExtraPutUtil;

/**
 * 封装参数的基础fragment
 * todo wait add test
 * <p>
 * 提供辅助指定类型的参数的获取，校验及处理功能
 *
 * @author f_ms
 * @date 18-11-13
 */
abstract class AbstractArgumentFragment<Arg> extends AbstractLifecycleFragment {

    /**
     * fragment参数bundleKey
     */
    private static final String BUNDLE_KEY_ARGUMENT
            = "BUNDLE_KEY_FRAGMENT_ARG_ABSTRACT_ARGUMENT_FRAGMENT";

    /**
     * 获取argument对应bundleKey
     *
     * @return argument对应bundleKey
     */
    public static String getArgumentBundleKey() {
        return BUNDLE_KEY_ARGUMENT;
    }

    /**
     * 获取填充指定参数后的bundle
     *
     * @param argument argument
     * @return 填充指定参数后的bundle
     */
    public static <Arg> Bundle getArgumentBundle(Arg argument) {
        return BundleExtraPutUtil.putExtra(new Bundle(), getArgumentBundleKey(), argument);
    }

    /**
     * 创建新fragment
     *
     * @param arg   activity's arg
     * @param <Arg> activity's arg type
     * @return activity's intent
     */
    protected static <Arg, Fragment extends AbstractArgumentFragment<Arg>> Fragment newFragment(Class<Fragment> fragmentClass, Arg arg) {
        try {
            Fragment fragment = fragmentClass.newInstance();
            fragment.setArguments(getArgumentBundle(arg));
            return fragment;
        } catch (Exception e) {
            throw new RuntimeException(
                    String.format(
                            "create %s's instance fail: %s",
                            fragmentClass.getCanonicalName(),
                            e.getMessage()
                    ),
                    e
            );
        }
    }

    /**
     * 页面参数
     */
    private Arg mArgument;

    /**
     * 获取页面参数
     *
     * @return 页面参数
     */
    public final Arg getArgument() {
        return mArgument;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle extras = getArguments();
        Arg arg = null;
        try {
            if (extras != null) {
                arg = (Arg) extras.get(getArgumentBundleKey());
            }
        } catch (ClassCastException e) {
            return onArgumentExistError(new ArgumentException.ArgumentTypeErrorException("argument type cast error on '" + this.getClass().getName() + "': " + e.getMessage(), e), container);
        }

        try {
            mArgument = checkArgument(arg);
            if (!isArgumentTypeRight) {
                throw new IllegalStateException("please call super.checkArgument(Arg) in '" + this.getClass().getName() + "' first");
            }
        } catch (ClassCastException e) {
            if (!isArgumentTypeRight) {
                return onArgumentExistError(new ArgumentException.ArgumentTypeErrorException("argument type cast error on '" + this.getClass().getName() + "': " + e.getMessage(), e), container);
            }
            return onArgumentExistError(e, container);
        } catch (Exception e) {
            return onArgumentExistError(e, container);
        }

        return onCreateView(mArgument, savedInstanceState, container);
    }

    /**
     * onCreateView with argument
     *
     * @param container          view container
     * @param arg                fragment arg
     * @param savedInstanceState savedInstanceState
     * @return fragment view
     */
    public abstract View onCreateView(Arg arg, @Nullable Bundle savedInstanceState, @Nullable ViewGroup container);


    /**
     * 用于检查外部传入参数是否与子类声明真实参数类型匹配
     */
    private boolean isArgumentTypeRight = false;

    /**
     * 检查activity参数是否存在异常
     *
     * @param arg activity arg
     * @throws Exception 当检查到参数存在异常时承载错误信息的exception
     * @return 处理后arg
     */
    protected Arg checkArgument(@Nullable Arg arg) throws Exception {
        isArgumentTypeRight = true;
        return arg;
    }

    /**
     * 当activity参数存在异常时的回调方法
     * <p>
     * 具体回调时机为当{@link #checkArgument(Arg)}抛出ArgumentException时
     *
     * @param exception     异常信息
     * @param viewContainer fragment view container
     * @return show view
     */
    protected View onArgumentExistError(@NonNull Exception exception, ViewGroup viewContainer) {
        throw new RuntimeException(this.getClass().getName() + " found argument error: " + exception.getMessage(), exception);
    }
}
