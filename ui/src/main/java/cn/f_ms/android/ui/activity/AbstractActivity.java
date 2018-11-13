package cn.f_ms.android.ui.activity;

/**
 * 基础activity
 *
 * @author f_ms
 * @date 18-8-31
 */
public class AbstractActivity<Arg, Result, State> extends AbstractResultActivity<Arg, Result, State> {

    /**
     * 获取当前activity实例, 主要服务于于内部类中获取activity实例的需求
     *
     * @return 当前activity实例
     */
    protected AbstractLifecycleActivity getActivity() {
        return this;
    }

}
