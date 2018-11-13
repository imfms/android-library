package cn.f_ms.android_library.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.Serializable;

import cn.f_ms.android.ui.fragment.AbstractFragment;
import cn.f_ms.android_library.R;

public class DemoAbstractFragment extends AbstractFragment<DemoAbstractFragment.Arg, DemoAbstractFragment.State> {

    public static DemoAbstractFragment newFragment(Arg arg) {
        return newFragment(DemoAbstractFragment.class, arg);
    }

    public static class Arg implements Serializable {
        public String title;
        public String content;
    }
    static class State extends Arg {}

    private EditText etTitle;
    private EditText etContent;

    @Override
    protected Arg checkArgument(@Nullable Arg arg) throws Exception {
        super.checkArgument(arg);

        if (arg == null) {
            arg = new Arg();
        }

        return arg;
    }

    @Override
    protected View onCreateView(@Nullable Arg argument, @Nullable State savedInstanceState, @Nullable Bundle savedInstanceStateBundle, ViewGroup viewContainer) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.ui_fragment_demo_abstract_fragment, viewContainer, false);

        etTitle = rootView.findViewById(R.id.etTitle);
        etContent = rootView.findViewById(R.id.etContent);

        etTitle.setText(argument.title);
        etContent.setText(argument.content);

        if (savedInstanceState != null) {
            etTitle.setText(savedInstanceState.title);
            etContent.setText(savedInstanceState.content);
        }

        return rootView;
    }

    @Nullable
    @Override
    protected State onSaveInstanceState() {
        State state = new State();
        state.title = etTitle.getText().toString();
        state.content = etContent.getText().toString();
        return state;
    }
}
