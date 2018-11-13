package cn.f_ms.android_library;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cn.f_ms.android.ui.activity.AbstractActivity;
import cn.f_ms.android_library.activity.ActionGenerater;
import cn.f_ms.android_library.fragment.DemoAbstractFragmentActivity;
import cn.f_ms.library.collection.util.ElementFilter;

public class MainActivity extends AbstractActivity {

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = new ListView(this);
        setContentView(listView);

        final Action[] actions = getInitActions();

        String[] actionNames = ElementFilter.convert(actions, String.class, new ElementFilter.Converter<Action, String>() {
            @Override
            public String convert(Action action) {
                return action.getName();
            }
        });

        initListAdapter(listView, actionNames);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actions[position].run();
            }
        });
    }

    private Action[] getInitActions() {
        return new Action[]{
                new NestFeatureDialogShowAction(getActivity(),"activity", new ActionGenerater(this)),
                new NestFeatureDialogShowAction(getActivity(), "fragment", new NestFeatureDialogShowAction.AbstractActionGenerater(this) {
                    @Override
                    public Action[] generate() {
                        return new Action[]{
                                new Action("DemoAbstractFragment") {
                                    @Override
                                    public void run() {
                                        getActivity().startActivity(
                                                DemoAbstractFragmentActivity.newIntent(getActivity(), DemoAbstractFragmentActivity.class, null)
                                        );
                                    }
                                }
                        };
                    }
                })
        };
    }

    private void initListAdapter(ListView listView, String[] actionNames) {
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        mAdapter.addAll(actionNames);

        listView.setAdapter(mAdapter);
    }
}
