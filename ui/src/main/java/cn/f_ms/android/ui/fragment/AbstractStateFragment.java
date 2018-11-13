package cn.f_ms.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import cn.f_ms.android.ui.util.BundleExtraPutUtil;


/**
 * 封装保存自身状态的基础fragment
 * todo wait add test
 * <p>
 * 主要应对于当fragment由于系统原因被销毁后页面状态丢失的问题
 *
 * @author f_ms
 * @date 18-11-13
 */
abstract class AbstractStateFragment<Arg, State> extends AbstractArgumentFragment<Arg> {

    /**
     * 保存状态值的bundleKey
     */
    private static String BUNDLE_KEY_SAVE_INSTANCE
            = "BUNDLE_KEY_SAVE_INSTANCE_ABSTRACT_STATE_FRAGMENT";

    private State mInstanceState;

    /**
     * 获取保存实例状态值的bundleKey
     *
     * @return 保存实例状态值的bundleKey
     */
    public static String getSaveInstanceStateBundleKey() {
        return BUNDLE_KEY_SAVE_INSTANCE;
    }

    /**
     * 重载onCreate, 替换实例状态参数为状态类型
     *
     * @param savedInstanceState       实例状态
     * @param savedInstanceStateBundle 实例状态原始数据
     * @param argument                 argument
     * @param viewContainer            view container
     */
    protected View onCreateView(@Nullable Arg argument, @Nullable State savedInstanceState, @Nullable Bundle savedInstanceStateBundle, ViewGroup viewContainer) {
        return null;
    }

    /**
     * 当需要保存实例状态时
     *
     * @return 实例状态
     */
    @Nullable
    protected State onSaveInstanceState() {
        return null;
    }

    /**
     * 当恢复实例状态时
     * 详情参见{@link #onViewStateRestored(Bundle)}
     *
     * @param savedInstanceState 恢复的实例状态
     */
    protected void onRestoreInstanceState(State savedInstanceState, Bundle savedInstanceStateBundle) {
    }

    @Override
    public View onCreateView(Arg arg, @Nullable Bundle savedInstanceState, @Nullable ViewGroup container) {

        State intanceState = null;
        if (savedInstanceState != null) {
            intanceState = (State) savedInstanceState.get(getSaveInstanceStateBundleKey());
        }

        mInstanceState = intanceState;
        return onCreateView(arg, mInstanceState, savedInstanceState, container);
    }

    /**
     * 获取实例状态
     *
     * @return 实例状态
     */
    protected State getInstanceState() {
        return mInstanceState;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleExtraPutUtil.putExtra(outState, getSaveInstanceStateBundleKey(), onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        onRestoreInstanceState(getInstanceState(), savedInstanceState);
    }
}
