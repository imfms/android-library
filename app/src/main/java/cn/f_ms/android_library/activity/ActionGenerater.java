package cn.f_ms.android_library.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import cn.f_ms.android.ui.LifecycleEventProvider;
import cn.f_ms.android_library.Action;
import cn.f_ms.android_library.NestFeatureDialogShowAction;
import cn.f_ms.library.logic.Callback;

/**
 * Desc: activity library demo generater
 *
 * @author f_ms
 * @time 18-4-21
 */
public class ActionGenerater extends NestFeatureDialogShowAction.AbstractActionGenerater {

    public ActionGenerater(cn.f_ms.android.ui.AbstractActivity activity) {
        super(activity);
    }

    @Override
    public Action[] generate() {
        return new Action[]{

                new Action(cn.f_ms.android.ui.AbstractActivity.class.getSimpleName()) {
                    @Override
                    public void run() {

                        final int startActivityRequestCode = 2;

                        final LifecycleEventProvider.OnActivityResultObserver[] onActivityResultObservers = new LifecycleEventProvider.OnActivityResultObserver[1];

                        final LifecycleEventProvider.OnActivityResultObserver onActivityResultObserver
                                = new LifecycleEventProvider.OnActivityResultObserver() {

                            @Override
                            public void onActivityResult(int requestCode, int resultCode, Intent data) {

                                getActivity().getActivityEventProvider().removeOnActivityResultObserver(onActivityResultObservers[0]);

                                if (startActivityRequestCode == requestCode
                                        && resultCode == AbstractActivity.Result.RESULT_CODE) {

                                    AbstractActivity.Result result = AbstractActivity.getResult(data);

                                    if (result == null
                                            || result.logs == null) {
                                        return;
                                    }

                                    String[] logs = result.logs;
                                    StringBuilder sb = new StringBuilder();
                                    for (String log : logs) {
                                        sb.append(log).append('\n');
                                    }

                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("result content")
                                            .setMessage(sb)
                                            .show();
                                }
                            }
                        };

                        onActivityResultObservers[0] = onActivityResultObserver;

                        getActivity().getActivityEventProvider().addOnActivityResultObserver(onActivityResultObserver);

                        showInputDialog("input argument", "please input argument for new activity", new Callback<String>() {
                            @Override
                            public void onCallback(String s) {
                                AbstractActivity.Arg arg = new AbstractActivity.Arg();
                                arg.arg = s;
                                getActivity().startActivityForResult(
                                        AbstractActivity.newIntent(getActivity(), arg), startActivityRequestCode
                                );
                            }
                        });
                    }
                }
        };
    }

    private void showInputDialog(String title, String hint, final Callback<String> callback) {
        final EditText editText = new EditText(getActivity());
        editText.setHint(hint);

        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setView(editText)
                .setNegativeButton("cancel", null)
                .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onCallback(
                                editText.getText().toString()
                        );
                    }
                })
                .show();
    }

}
