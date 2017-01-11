package com.android.gxin.testmygridlistview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ListView mListView;
    private TAdapter mAdapter;
    private List<String> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListView();
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.listview);
        mDataList = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            mDataList.add(String.format("项目:%d", (i + 1)));
        }
        mAdapter = new TAdapter(this, mDataList);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new MyGridListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long id) {
                if(position >= mDataList.size())
                    return;
                Toast.makeText(MainActivity.this, mDataList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
