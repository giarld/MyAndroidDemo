package com.android.gxin.testmygridlistview;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gxin on 17-1-11.
 */

public class TAdapter extends MyGridListAdapter {
    private List<String> mList;

    public TAdapter(Activity activity, List<String> list) {
        super(activity, 4, FillMode.LeaveBlank);
        mList = list;
    }

    @Override
    public int getTCount() {
        if(mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public Object getTItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getTItemId(int position) {
        return 0;
    }

    @Override
    public View getTView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = View.inflate(getActivity(), R.layout.adapter_test, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.tv_adapter);
        textView.setText((String) getTItem(position));

        return convertView;
    }
}
