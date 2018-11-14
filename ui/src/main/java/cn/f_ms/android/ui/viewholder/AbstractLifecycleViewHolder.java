package cn.f_ms.android.ui.viewholder;

/**
 * 把控自己生命周期的视图控制器基类
 *
 * @author f_ms
 * @date 18-11-14
 */
public abstract class AbstractLifecycleViewHolder extends AbstractViewHolder implements LifecycleViewHolder {

    private boolean isAttach = false;

    @Override
    public final boolean isAttach() {
        return isAttach;
    }

    @Override
    public final void onAttach() {
        if (isAttach) {
            return;
        }
        if (!isInit()) {
            throw new IllegalStateException("viewholder not initialized yet");
        }

        onAttached();
        isAttach = true;
    }

    @Override
    public final void onDetach() {
        if (!isInit()) {
            return;
        }
        if (!isAttach) {
            return;
        }

        onDetached();
        isAttach = false;
    }

    /**
     * @see #onAttach()
     */
    protected void onAttached() {}

    /**
     * @see #onDetach()
     */
    protected void onDetached() {};
}
