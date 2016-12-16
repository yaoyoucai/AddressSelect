package shbd.addresspickerview;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：AddressSelect
 * 类描述：
 * 创建人：yh
 * 创建时间：2016/12/15 10:27
 * 修改人：yh
 * 修改时间：2016/12/15 10:27
 * 修改备注：
 */
public class AddressPickView extends PopupWindow implements PopupWindow.OnDismissListener, View.OnClickListener, TabLayout.OnTabSelectedListener, AddressPickAdapter.OnAddressPickItemClickListener {
    private TextView mTvCancel;
    private TextView mTvConfirm;
    private NoScrollViewPager mVpContent;
    private TabLayout mTbSelector;
    private View mView;
    private Activity mActivity;

    //控件高度
    private int height;

    private List<List<String>> mdatas;

    List<String> mProvinceData = new ArrayList<>();
    List<String> mCityData = new ArrayList<>();
    List<String> mDistrictData = new ArrayList<>();

    public AddressPickView(Activity activity) {
        this.mActivity = activity;
        if (height == 0) {
            height = getScreenH(mActivity) / 2;
        }
        initPicker();
        initData();
        initView();
    }


    private void initPicker() {
        LayoutInflater inflater = LayoutInflater.from(mActivity.getApplicationContext());
        mView = inflater.inflate(R.layout.address_picker_view, null);
        this.setContentView(mView);

        //宽度和高度必需要设，否则无法显示
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(height);

        this.setAnimationStyle(R.style.AddressPickViewAnimStyle);
        //点击外部消失   不会消失的原因http://www.cnblogs.com/popfisher/p/5608717.html
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
    }

    private void initData() {
        createLocalDataBase();
        mdatas = new ArrayList<>();
        mdatas.add(mProvinceData);
        mdatas.add(mCityData);
        mdatas.add(mDistrictData);

    }
/*

    */

    /**
     * 创建本地数据库
     */

    private void createLocalDataBase() {
        DBManager dbHelper = new DBManager(mActivity);
        dbHelper.openDatabase();
        dbHelper.closeDatabase();
        mProvinceData = queryDataFromLocal("select distinct(shortname) from city where level=1", "shortname");
       /* try {
            dbHelper.createDataBase();
            mDataBase = dbHelper.getWritableDatabase();
            mProvinceData =
            //查询出所有省 select distinct(shortname) from city where level=1
            //查询出对应市 select * from city where pid in(select distinct(id) from city where shortname='湖南' and level=1);
            //查询出对应区  select distinct(name) from city where pid= (select distinct(id) from city where shortname ='北京' and level=2)
            //查询出对应邮编 select  distinct(zip_code) from city where name ='赫山区' and level=3;
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private List<String> queryDataFromLocal(String statement, String column) {
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
        List<String> datas = new ArrayList<>();
        Cursor cursor = database.rawQuery(statement, null);
        while (cursor.moveToNext()) {
            datas.add(cursor.getString(cursor.getColumnIndex(column)));
        }
        database.close();
        return datas;
    }

    private void initView() {
        mTvCancel = (TextView) mView.findViewById(R.id.tv_cancel);
        mTvConfirm = (TextView) mView.findViewById(R.id.tv_confirm);
        mVpContent = (NoScrollViewPager) mView.findViewById(R.id.vp_content);
        mTbSelector = (TabLayout) mView.findViewById(R.id.tb_selector);

        mTvCancel.setOnClickListener(this);
        mTvConfirm.setOnClickListener(this);
        mTbSelector.setOnTabSelectedListener(this);
        mVpContent.setAdapter(new AddressPickAdapter(mActivity.getApplicationContext(), mdatas, this));
        mTbSelector.addTab(mTbSelector.newTab().setText("请选择"));
    }


    public void show(View view) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.7f;
        mActivity.getWindow().setAttributes(lp);
        showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        setOnDismissListener(this);
    }


    public int getScreenH(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    @Override
    public void onDismiss() {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 1f;
        mActivity.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tv_cancel) {
            dismiss();
        } else if (viewId == R.id.tv_confirm) {
            dismiss();
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                mVpContent.setCurrentItem(0, false);
                break;
            case 1:
                mVpContent.setCurrentItem(1, false);
                break;
            case 2:
                mVpContent.setCurrentItem(2, false);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onItemClick(int position, String data) {
        mTbSelector.removeAllTabs();
        switch (position) {
            case 0:
                firsrt = data;
                mTbSelector.addTab(mTbSelector.newTab().setText(firsrt));
                mTbSelector.addTab(mTbSelector.newTab().setText("请选择"));
                mTbSelector.getTabAt(1).select();
                break;
            case 1:
                second = data;
                mTbSelector.addTab(mTbSelector.newTab().setText(firsrt));
                mTbSelector.addTab(mTbSelector.newTab().setText(second));
                mTbSelector.addTab(mTbSelector.newTab().setText("请选择"));
                mTbSelector.getTabAt(2).select();
                break;
            case 2:
                third = data;
                mTbSelector.addTab(mTbSelector.newTab().setText(firsrt));
                mTbSelector.addTab(mTbSelector.newTab().setText(second));
                mTbSelector.addTab(mTbSelector.newTab().setText(third));
                mTbSelector.getTabAt(2).select();
                break;
        }
//        Toast.makeText(mActivity, "currentPosition:" + position + ",data:" + data, Toast.LENGTH_SHORT).show();
    }

    private String firsrt;
    private String second;
    private String third;
}
