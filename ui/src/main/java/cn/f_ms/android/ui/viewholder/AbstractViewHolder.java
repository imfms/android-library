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

    private final Context context;
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

    @Override
    public final View initView(ViewGroup container) {

        if (contentView != null) {
            throw new IllegalStateException("viewholder already inited");
        }

        View contentView = initView(LayoutInflater.from(context), container);
        CheckNull.ifNullThrowArgException(contentView, "contentView can't be null");

        this.contentView = contentView;

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

    @Override
    public final View contentView() throws IllegalStateException {

        if (contentView == null) {
            throw new IllegalStateException("viewholder not initialized yet, please call initView() first");
        }

        return contentView;
    }
}
