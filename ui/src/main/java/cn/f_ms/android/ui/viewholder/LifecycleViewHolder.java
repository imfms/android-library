package cn.f_ms.android.ui.viewholder;

import android.view.ViewGroup;

/**
 * 把控自己生命周期的视图控制器
 *
 * @author f_ms
 * @date 18-11-14
 */
public interface LifecycleViewHolder extends ViewHolder {

    /**
     * 视图是否被依附/使用
     *
     * @return true == 是, false == 否
     */
    boolean isAttach();

    /**
     * 当视图被依附/使用
     * 此方法在 {@link #initView(ViewGroup)} 后被调用
     *
     * @throws IllegalStateException 在{@link #initView(ViewGroup)}被前调用
     */
    void onAttach();

    /**
     * 当视图被取消依附/使用
     */
    void onDetach();

}
