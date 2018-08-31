package cn.f_ms.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 封装参数的基础activity
 * todo wait add test
 * <p>
 * 提供辅助指定类型的参数的获取，校验及处理功能
 *
 * @author f_ms
 * @date 18-4-21
 */
abstract class AbstractArgumentActivity<Arg> extends AbstractLifecycleActivity {

    /**
     * Activity参数bundleKey
     */
    private static final String BUNDLE_KEY_ACTIVITY_ARGUMENT
            = "BUNDLE_KEY_ACTIVITY_ARG_ABSTRACT_ARGUMENT_ACTIVITY";

    /**
     * 获取argument对应bundleKey
     *
     * @return argument对应bundleKey
     */
    public static String getArgumentBundleKey() {
        return BUNDLE_KEY_ACTIVITY_ARGUMENT;
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
     * 创建新intent
     *
     * @param activity      activity
     * @param activityClass activityClass
     * @param arg           activity's arg
     * @param <Arg>         activity's arg type
     * @return activity's intent
     */
    protected static <Arg> Intent newIntent(Activity activity, Class<? extends Activity> activityClass, Arg arg) {
        return new Intent(activity, activityClass)
                .putExtras(getArgumentBundle(arg));
    }

    /**
     * 页面参数
     */
    private Arg mArgument;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        // no argument
        if (extras == null) {
            callCheckArgument(null);
            return;
        }

        Arg arg;
        try {
            arg = (Arg) extras.get(getArgumentBundleKey());
        } catch (ClassCastException e) {
            onArgumentExistError(new ArgumentException.ArgumentTypeErrorException("argument type cast error on '" + this.getClass().getName() + "': " + e.getMessage(), e));
            return;
        }

        mArgument = arg;
        if (callCheckArgument(arg)) {
            onCreate(mArgument, savedInstanceState);
        }
    }

    /**
     * 获取页面参数
     *
     * @return 页面参数
     */
    protected final Arg getArgument() {
        return mArgument;
    }

    /**
     * 检查activity参数是否存在异常
     *
     * @param arg activity arg
     * @throws ArgumentException 当检查到参数存在异常时承载错误信息的exception
     */
    protected void checkArgument(@Nullable Arg arg) throws ArgumentException {
        isArgumentTypeRight = true;
    }

    /**
     * 当activity参数存在异常时的回调方法
     * <p>
     * 具体回调时机为当{@link #checkArgument(Arg)}抛出ArgumentException时
     *
     * @param exception 异常信息
     */
    protected void onArgumentExistError(@NonNull ArgumentException exception) {
        throw exception;
    }

    /**
     * onCreate with argument
     *
     * @param argument           页面参数
     * @param savedInstanceState 页面销毁前保存的实例状态
     */
    protected void onCreate(@Nullable Arg argument, @Nullable Bundle savedInstanceState) {
    }

    /**
     * 用于检查外部传入参数是否与子类声明真实参数类型匹配
     */
    private boolean isArgumentTypeRight = false;

    private boolean callCheckArgument(@Nullable Arg argument) {
        try {
            checkArgument(argument);
            if (!isArgumentTypeRight) {
                throw new IllegalStateException("please call super.checkArgument(Arg) in '" + this.getClass().getName() + "' first");
            }
            return true;
        } catch (ClassCastException e) {
            if (!isArgumentTypeRight) {
                onArgumentExistError(new ArgumentException.ArgumentTypeErrorException("argument type cast error on '" + this.getClass().getName() + "': " + e.getMessage(), e));
            }
            return false;
        } catch (ArgumentException e) {
            onArgumentExistError(e);
            return false;
        }
    }
}
