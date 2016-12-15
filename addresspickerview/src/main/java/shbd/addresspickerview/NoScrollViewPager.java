package shbd.addresspickerview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 项目名称：AddressSelect
 * 类描述：
 * 创建人：yh
 * 创建时间：2016/12/15 17:01
 * 修改人：yh
 * 修改时间：2016/12/15 17:01
 * 修改备注：
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
