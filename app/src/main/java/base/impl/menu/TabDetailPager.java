package base.impl.menu;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.zhbj.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import base.BaseMenuDetailPager;
import domain.NewsMenu;
import global.GlobalConstants;
import utils.CacheUtils;

/**
 * 页签页面对象
 * 
 * @author Kevin
 * @date 2015-10-20
 */
public class TabDetailPager extends BaseMenuDetailPager {

	private NewsMenu.NewsTabData mTabData;// 单个页签的网络数据
	private TextView view;

	public TabDetailPager(Activity activity, NewsMenu.NewsTabData newsTabData) {
		super(activity);
		mTabData = newsTabData;
	}

	@Override
	public View initView() {
		view = new TextView(mActivity);

		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);
		return view;
	}

	@Override
	public void initData() {
		view.setText(mTabData.title);
	}

}
