package cn.f_ms.android_library.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import cn.f_ms.android.ui.AbstractActivity;
import cn.f_ms.android.ui.AbstractArgumentActivity;
import cn.f_ms.android.ui.AbstractResultActivity;
import cn.f_ms.android.ui.AbstractStateActivity;
import cn.f_ms.android.ui.LifecycleEventProvider;
import cn.f_ms.android_library.Action;
import cn.f_ms.android_library.NestFeatureDialogShowAction;

/**
 * Desc: activity library demo generater
 *
 * @author f_ms
 * @time 18-4-21
 */
public class ActionGenerater extends NestFeatureDialogShowAction.AbstractActionGenerater {

    public ActionGenerater(AbstractActivity activity) {
        super(activity);
    }

    @Override
    public Action[] generate() {
        return new Action[]{

                new Action(AbstractActivity.class.getSimpleName()) {
                    @Override
                    public void run() {
                        getActivity().startActivity(
                                new Intent(getActivity(), cn.f_ms.android_library.activity.AbstractActivity.class)
                        );
                    }
                },
                new Action(AbstractArgumentActivity.class.getSimpleName()) {
                    @Override
                    public void run() {

                        final EditText editText = new EditText(getActivity());
                        editText.setHint("activity display contenet");

                        new AlertDialog.Builder(getActivity())
                                .setTitle(getName())
                                .setView(editText)
                                .setNegativeButton("cancel", null)
                                .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String displayContent = editText.getText().toString();
                                        callArgumentActivity(displayContent);
                                    }
                                })
                                .show();
                    }

                    private void callArgumentActivity(String displayContent) {
                        ArgumentActivity.Arg arg = new ArgumentActivity.Arg();
                        arg.displayContent = displayContent;
                        getActivity().startActivity(
                                ArgumentActivity.newIntent(getActivity(), arg)
                        );
                    }
                },

                new Action(AbstractResultActivity.class.getSimpleName()) {

                    static final int REQUEST_CODE = 1234;

                    @Override
                    public void run() {
                        getActivity().getActivityEventProvider().addOnActivityResultObserver(new LifecycleEventProvider.OnActivityResultObserver() {
                            @Override
                            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                                if (requestCode == REQUEST_CODE
                                        && ResultActivity.Result.RESULT_CODE == resultCode) {
                                    ResultActivity.Result result = ResultActivity.getResult(data);
                                    Toast.makeText(getActivity(), String.valueOf(result.getSelectPosition()), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        getActivity().startActivityForResult(
                                new Intent(getActivity(), ResultActivity.class), REQUEST_CODE
                        );
                    }
                },

                new Action(AbstractStateActivity.class.getSimpleName()) {
                    @Override
                    public void run() {
                        getActivity().startActivity(
                                new Intent(getActivity(), StateActivity.class)
                        );
                    }
                }
        };
    }
}
