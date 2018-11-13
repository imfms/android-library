package cn.f_ms.android_library.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.f_ms.android.ui.activity.AbstractActivity;
import cn.f_ms.android_library.R;
import cn.f_ms.android_library.util.DialogUtil;
import cn.f_ms.library.logic.Callback;

public class DemoAbstractFragmentActivity extends AbstractActivity<Void, Void, Void> {

    @Override
    protected void onCreate(@Nullable Void argument, @Nullable Void savedInstanceState, @Nullable Bundle savedInstanceStateBundle) {
        super.onCreate(argument, savedInstanceState, savedInstanceStateBundle);

        setContentView(R.layout.ui_activity_demo_abstract_fragment);

        DialogUtil.showInputDialog(this, "input title argument", "title", new Callback<String>() {
            @Override
            public void onCallback(String s) {
                DemoAbstractFragment.Arg arg = new DemoAbstractFragment.Arg();
                arg.title = s;

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.llFragmentContainer, DemoAbstractFragment.newFragment(arg))
                        .commit();
            }
        });
    }
}
