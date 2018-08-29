package cn.f_ms.android_library.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

import cn.f_ms.android.ui.AbstractStateActivity;
import cn.f_ms.android_library.R;

class State implements Serializable {

    State(int itemCount) {
        this.itemCount = itemCount;
    }

    int itemCount;
}

/**
 * @author f_ms
 * @time 18-4-22
 */
public class StateActivity extends AbstractStateActivity<State> {

    private ViewGroup llItemContainer;

    @Override
    protected void onCreate(@Nullable State instanceState, Bundle savedInstanceState) {
        super.onCreate(instanceState, savedInstanceState);
        setContentView(R.layout.ui_activity_state);

        llItemContainer = findViewById(R.id.ll_item_container);

        findViewById(R.id.btn_add_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextToContainer(
                        String.valueOf(llItemContainer.getChildCount() + 1),
                        llItemContainer
                );
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(@Nullable State instanceState, Bundle savedInstanceState) {
        super.onRestoreInstanceState(instanceState, savedInstanceState);

        for (int i = 0; i < instanceState.itemCount; i++) {
            addTextToContainer(String.valueOf(i + 1), llItemContainer);
        }
    }

    @Nullable
    @Override
    protected State getInitInstanceState() {
        return new State(0);
    }

    @Nullable
    @Override
    protected State onSaveInstanceState() {
        return new State(llItemContainer.getChildCount());
    }

    private void addTextToContainer(String text, ViewGroup container) {
        TextView child = new TextView(getActivity());
        child.setText(text);
        container.addView(child, 0);
    }
}
