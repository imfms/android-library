package cn.f_ms.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 封装返回结果的基础activity
 * todo wait add test
 *
 * @author f_ms
 * @date 18-4-21
 */
abstract class AbstractResultActivity<Arg, Result, State> extends AbstractStateActivity<Arg, State> {

    private static final String BUNDLE_KEY_RESULT = "bundle_key_abstract_result_activity_result";

    /**
     * 获取result对应bundleKey
     *
     * @return result对应bundleKey
     */
    public static String getResultBundleKey() {
        return BUNDLE_KEY_RESULT;
    }

    /**
     * 获取Activity真实类型结果
     *
     * @param resultIntent startActivityForResult's resultIntent
     * @param <Result>     真实结果类型
     * @return 真实类型结果
     * @throws ClassCastException 当提供intent中获取到的结果与指定类型结果不匹配时
     */
    public static <Result> Result getResult(@Nullable Intent resultIntent) throws ClassCastException {
        if (resultIntent == null) {
            return null;
        }
        return getResult(resultIntent.getExtras());
    }

    /**
     * 获取Activity真实类型结果
     *
     * @param resultBundle startActivityForResult's resultIntent's bundle
     * @param <Result>     真实结果类型
     * @return 真实类型结果
     * @throws ClassCastException 当提供intent中获取到的结果与指定类型结果不匹配时
     */
    @SuppressWarnings("unchecked")
    public static <Result> Result getResult(@Nullable Bundle resultBundle) throws ClassCastException {
        if (resultBundle == null) {
            return null;
        }

        // maybe throw ClassCastException
        return (Result) resultBundle.get(getResultBundleKey());
    }

    /**
     * 设置页面返回结果
     *
     * @param resultCode 结果码
     * @param result     结果
     * @see #startActivityForResult(Intent, int)
     * @see #setResult(int, Intent)
     * @see #finish()
     */
    public void setResult(int resultCode, Result result) {
        setResult(
                resultCode,
                new Intent().putExtras(
                        BundleExtraPutUtil.putExtra(new Bundle(), getResultBundleKey(), result)
                )
        );
    }

    /**
     * 携带结果关闭页面
     *
     * @param resultCode 结果码
     * @param result     结果
     * @see #startActivityForResult(Intent, int)
     * @see #setResult(int, Intent)
     * @see #finish()
     */
    public void finish(int resultCode, Result result) {
        setResult(
                resultCode,
                result
        );
        finish();
    }

}
