package cn.f_ms.android_library;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import cn.f_ms.android.ui.activity.AbstractActivity;
import cn.f_ms.library.check.CheckNull;
import cn.f_ms.library.collection.util.ElementFilter;

public class NestFeatureDialogShowAction extends Action {

    private final Activity activity;
    private final AbstractActionGenerater mActionGenerater;

    public NestFeatureDialogShowAction(Activity activity, String name, AbstractActionGenerater actionGenerater) {
        super(name);
        this.activity = CheckNull.ifNullThrowArgException(activity, "activity can't be null");
        mActionGenerater = CheckNull.ifNullThrowArgException(actionGenerater, "actionGenerater can't be null");
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public final void run() {
        showSubFeatureActionsUi(getName(), mActionGenerater.generate());
    }

    private void showSubFeatureActionsUi(String title, final Action[] actions) {

        String[] actionNames = ElementFilter.convert(actions, String.class, new ElementFilter.Converter<Action, String>() {
            @Override
            public String convert(Action action) {
                return action.getName();
            }
        });

        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setItems(actionNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actions[which].run();
                    }
                })
                .show();
    }

    public static abstract class AbstractActionGenerater {

        private AbstractActivity mActivity;

        public AbstractActionGenerater(AbstractActivity activity) {
            mActivity = CheckNull.ifNullThrowArgException(activity, "activity can't be null");
        }

        public AbstractActivity getActivity() {
            return mActivity;
        }

        /**
         * 生成行为
         * @return 行为集
         */
        public abstract Action[] generate();
    }
}