package cn.f_ms.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 支持保存自身状态的activity
 * todo wait add test
 * <p>
 * 主要应对于当activity由于系统原因被销毁后页面状态丢失的问题
 *
 * @author f_ms
 * @date 18-4-22
 */
public abstract class AbstractStateActivity<StateType> extends AbstractActivity {

    /**
     * 保存状态值的bundleKey
     */
    private static String BUNDLE_KEY_SAVE_INSTANCE
            = "BUNDLE_KEY_SAVE_INSTANCE_ABSTRACT_STATE_ACTIVITY";

    private StateType mInstanceState;

    /**
     * 获取保存实例状态值的bundleKey
     *
     * @return 保存实例状态值的bundleKey
     */
    public static String getSaveInstanceStateBundleKey() {
        return BUNDLE_KEY_SAVE_INSTANCE;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StateType intanceState = null;
        if (savedInstanceState != null) {
            intanceState = (StateType) savedInstanceState.get(getSaveInstanceStateBundleKey());
        }
        if (intanceState == null) {
            intanceState = getInitInstanceState();
        }

        mInstanceState = intanceState;
        onCreate(mInstanceState, savedInstanceState);
    }

    /**
     * 获取实例状态
     *
     * @return 实例状态
     */
    protected StateType getInstanceState() {
        return mInstanceState;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleExtraPutUtil.putExtra(outState, getSaveInstanceStateBundleKey(), onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onRestoreInstanceState(getInstanceState(), savedInstanceState);
    }

    /**
     * 重载onCreate, 替换实例状态参数为状态类型
     *
     * @param instanceState      实例状态
     * @param savedInstanceState 实例状态原始数据
     */
    protected void onCreate(@Nullable StateType instanceState, @Nullable Bundle savedInstanceState) {}

    /**
     * 获取初始化实例状态
     *
     * @return 初始实例状态
     */
    @Nullable
    protected StateType getInitInstanceState() {
        return null;
    }

    /**
     * 当需要保存实例状态时
     *
     * @return 实例状态
     */
    @Nullable
    protected StateType onSaveInstanceState() {
        return null;
    }

    /**
     * 当恢复实例状态时
     * 详情参见{@link #onRestoreInstanceState(Bundle)}
     *
     * @param instanceState 恢复的实例状态
     */
    protected void onRestoreInstanceState(StateType instanceState, Bundle savedInstanceState) {}
}
