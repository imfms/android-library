package cn.f_ms.android_library.viewholder;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.f_ms.android.ui.activity.AbstractActivity;

public class DemoViewHolderActivity extends AbstractActivity<Void, Void, Void> {

    private DemoLifecycleViewHolder contentViewHolder;

    @Override
    protected void onCreate(@Nullable Void argument, @Nullable Void savedInstanceState, @Nullable Bundle savedInstanceStateBundle) {
        super.onCreate(argument, savedInstanceState, savedInstanceStateBundle);

        contentViewHolder = new DemoLifecycleViewHolder();

        setContentView(contentViewHolder.initView(this, null));
    }

    @Override
    protected void onResume() {
        super.onResume();
        contentViewHolder.onAttach();
    }

    @Override
    protected void onPause() {
        super.onPause();
        contentViewHolder.onDetach();
    }
}
