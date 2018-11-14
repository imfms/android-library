package cn.f_ms.android.ui.viewholder;

import android.view.View;
import android.view.ViewGroup;

/**
 * 视图控制器接口
 *
 * @author f_ms
 * @date 18-11-14
 */
public interface ViewHolder {

    /**
     * 是否已经初始化
     *
     * @return true == 已经初始化, false == 暂未初始化
     */
    boolean isInit();

    /**
     * 生成view, 不可重复调用
     *
     * @param container view container
     * @return 生成后的view
     * @throws IllegalStateException 重复调用
     */
    View initView(ViewGroup container);

    /**
     * 获取已生成的view
     * 需先调用{@link #initView(ViewGroup)}
     *
     * @return 已生成的view
     * @throws IllegalStateException 还未生成view, {@link #initView(ViewGroup)}
     */
    View contentView() throws IllegalStateException;

}
