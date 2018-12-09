package cn.f_ms.android.ui.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.f_ms.library.check.CheckNull;

/**
 * 基本视图控制器基类
 *
 * @author f_ms
 * @date 18-11-14
 */
public abstract class AbstractViewHolder implements ViewHolder {

    /**
     * viewholder初始化监听器
     */
    public interface OnViewHolderInitedListener {
        /**
         * 当viewholder初始化后
         * @param viewHolder viewHolder
         */
        void onViewHolderInited(ViewHolder viewHolder);
    }

    private final Context context;

    private OnViewHolderInitedListener onViewHolderInitedListener;
    private View contentView;

    public AbstractViewHolder(Context context) {
        this.context = CheckNull.ifNullThrowArgException(context, "context can't be null");
    }

    protected Context getContext() {
        return context;
    }

    @Override
    public final boolean isInit() {
        return contentView != null;
    }

    /**
     * 获取视图, 如果暂未初始化则初始化view, 已初始化则返回当前view
     * @param container view container
     * @return 视图
     */
    public final View obtainView(ViewGroup container) {
        return isInit()
                ? contentView()
                : initView(container);
    }

    /**
     * 设置viewholder初始化状态监听器
     * @param onViewHolderInitedListener 初始化状态监听器
     */
    public void setOnViewHolderInitedListener(OnViewHolderInitedListener onViewHolderInitedListener) {
        this.onViewHolderInitedListener = onViewHolderInitedListener;
        if (isInit()) {
            if (onViewHolderInitedListener != null) {
                onViewHolderInitedListener.onViewHolderInited(this);
            }
        }
    }

    @Override
    public final View initView(ViewGroup container) {

        if (contentView != null) {
            throw new IllegalStateException("viewholder already inited");
        }

        View contentView = initView(LayoutInflater.from(context), container);
        CheckNull.ifNullThrowArgException(contentView, "contentView can't be null");

        this.contentView = contentView;

        if (onViewHolderInitedListener != null) {
            onViewHolderInitedListener.onViewHolderInited(this);
        }

        return contentView;
    }

    /**
     * 初始化视图
     *
     * @param inflater  inflater
     * @param container container
     * @return 视图
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    /**
     * 校验是否已初始化
     */
    public final void requireInited() {
        if (!isInit()) {
            throw new IllegalStateException("please initView first");
        }
    }

    @Override
    public final View contentView() throws IllegalStateException {

        if (contentView == null) {
            throw new IllegalStateException("viewholder not initialized yet, please call initView() first");
        }

        return contentView;
    }
}
