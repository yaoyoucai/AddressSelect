package shbd.addresspickerview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/**
 * 项目名称：AddressSelect
 * 类描述：
 * 创建人：yh
 * 创建时间：2016/12/15 14:46
 * 修改人：yh
 * 修改时间：2016/12/15 14:46
 * 修改备注：
 */
public class AddressPickAdapter extends PagerAdapter {
    private Context mContext;

    private List<List<String>> mDatas;

    private OnAddressPickItemClickListener mListener;

    public AddressPickAdapter(Context context, List<List<String>> datas, OnAddressPickItemClickListener listener) {
        this.mContext = context;
        this.mDatas = datas;
        this.mListener = listener;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment1, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        final int currentPosition = position;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onItemClick(currentPosition, mDatas.get(currentPosition).get(position));
                }
            }
        });
        listView.setAdapter(new DataAdapter(mContext, mDatas.get(position)));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface OnAddressPickItemClickListener {
        void onItemClick(int position, String data);
    }

}
