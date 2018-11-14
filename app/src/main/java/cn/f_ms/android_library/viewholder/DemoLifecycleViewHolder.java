package cn.f_ms.android_library.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.f_ms.android.ui.viewholder.AbstractLifecycleViewHolder;
import cn.f_ms.android_library.R;

public class DemoLifecycleViewHolder extends AbstractLifecycleViewHolder {

    private TextView tvAttach;
    private TextView tvInit;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.ui_viewholder_demo_lifecycle_viewholder, container, false);

        tvAttach = view.findViewById(R.id.tvAttach);
        tvInit = view.findViewById(R.id.tvInit);

        tvInit.setText("inited");

        return view;
    }

    @Override
    protected void onAttached() {
        super.onAttached();

        tvAttach.setText("attached");
    }

    @Override
    protected void onDetached() {
        super.onDetached();

        tvAttach.setText("detached");
    }
}
