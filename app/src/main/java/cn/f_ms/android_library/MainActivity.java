package cn.f_ms.android_library;

import android.os.Bundle;
import android.support.v4.util.ArraySet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import cn.f_ms.android_library.activity.AbstractActivity;
import cn.f_ms.android_library.activity.ActionGenerater;
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

        ArraySet<String> strings = new ArraySet<>();
        for (int i = 0; i < 10; i++) {
            strings.add(String.valueOf(i));
        }

        Toast.makeText(getActivity(), strings.toString(), Toast.LENGTH_LONG).show();
    }

    private Action[] getInitActions() {
        return new Action[]{
                new NestFeatureDialogShowAction(getActivity(),"activity", new ActionGenerater(getActivity())),
        };
    }

    private void initListAdapter(ListView listView, String[] actionNames) {
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        mAdapter.addAll(actionNames);

        listView.setAdapter(mAdapter);
    }
}
