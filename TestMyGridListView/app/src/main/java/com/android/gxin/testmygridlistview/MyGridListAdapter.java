package com.android.gxin.testmygridlistview;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * Created by gxin on 16-10-12.
 */
public abstract class MyGridListAdapter extends BaseAdapter {
    private int mLineCount = 1;
    private Activity mActivity;

    private OnItemClickListener mOnItemClickListener;

    /**
     * 填充方式
     */
    public enum FillMode {
        LeaveBlank,     //留出空白项
        Stretching      //拉伸其他项目填充（布局允许的话）
    }

    FillMode mFillMode = FillMode.LeaveBlank;

    /**
     *
     * @param activity
     * @param lineCount 每行个数
     * @param fillMode  空白项填充方式
     */
    public MyGridListAdapter(Activity activity, int lineCount, FillMode fillMode) {
        mActivity = activity;
        setLineCount(lineCount);
        mFillMode = fillMode;
    }

    public Activity getActivity() {
        return mActivity;
    }

    /**
     * 设置每行个数
     *
     * @param lineCount
     */
    public void setLineCount(int lineCount) {
        mLineCount = lineCount;
    }

    public int getLineCount() {
        return mLineCount;
    }

    /**
     * 使用这个获得Item总数
     *
     * @return
     */
    public abstract int getTCount();

    /**
     * 使用这个获得Item对象
     *
     * @return
     */
    public abstract Object getTItem(int position);

    /**
     * 使用这个获得ItemId
     *
     * @return
     */
    public abstract long getTItemId(int position);

    /**
     * 使用这个创建View
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public abstract View getTView(int position, View convertView, ViewGroup parent);

    /**
     * 不要重写
     *
     * @return
     */
    @Override
    public int getCount() {
        if (getTCount() == 0)
            return 0;
        return ((getTCount() - 1) / mLineCount) + 1;
    }

    /**
     * 不要重写
     *
     * @return
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * 不要重写
     *
     * @return
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 不要重写
     *
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mActivity, R.layout.adapter_my_grid, null);
//            convertView = new LinearLayout(mActivity);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.gravity = Gravity.LEFT;
//            convertView.setLayoutParams(layoutParams);
        }

        LinearLayout layout = (LinearLayout) convertView;
        layout.setOrientation(LinearLayout.HORIZONTAL);
//        layout.setWeightSum(getLineCount());

        int startPos = position * mLineCount;
        int endPos = position * mLineCount + (mLineCount - 1);

        int viewItemCount = layout.getChildCount();
        for (int index = startPos; index <= endPos; index++) {
            if (layout.getChildAt(index - startPos) == null) {
                LinearLayout childLayout = new LinearLayout(mActivity);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                p.gravity = Gravity.CENTER;
                layout.addView(childLayout, index - startPos, p);
            }
            if (index >= getTCount()) {
                if ((index - startPos) < viewItemCount) {
                    if (layout.getChildAt(index - startPos) != null) {
                        if (mFillMode == FillMode.LeaveBlank) {
                            layout.getChildAt(index - startPos).setVisibility(View.INVISIBLE);
                        } else {
                            layout.getChildAt(index - startPos).setVisibility(View.GONE);
                        }
                    }
                }
                continue;
            }
            LinearLayout childLayout = (LinearLayout) layout.getChildAt(index - startPos);
            View itemView = null;
            if ((index - startPos) < viewItemCount) {
                itemView = childLayout.getChildAt(0);
            }
            if (itemView != null) {
                childLayout.setVisibility(View.VISIBLE);
            }
            View newItem = getTView(index, itemView, childLayout);
            if (newItem == null) {
                if (mFillMode == FillMode.LeaveBlank) {
                    childLayout.setVisibility(View.INVISIBLE);
                } else {
                    childLayout.setVisibility(View.GONE);
                }
                childLayout.setOnClickListener(null);
                continue;
            } else {
                final int finalPos = index;
                childLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnItemClickListener != null && isTEnabled(finalPos)) {
                            mOnItemClickListener.onItemClick(finalPos, getTItemId(finalPos));
                        }
                    }
                });
            }
            childLayout.removeAllViews();
            childLayout.addView(newItem);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) newItem.getLayoutParams();
            layoutParams.weight = 1;
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            newItem.setLayoutParams(layoutParams);
        }

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public boolean isTEnabled(int position) {
        return true;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, long id);
    }
}
