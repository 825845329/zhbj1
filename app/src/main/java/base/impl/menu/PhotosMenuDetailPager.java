package base.impl.menu;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhbj.R;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import base.BaseMenuDetailPager;
import domain.PhotosBean;
import global.GlobalConstants;
import utils.CacheUtils;

/**
 * 菜单详情页-组图
 * @author Kevin
 * @date 2015-10-18
 */
public class PhotosMenuDetailPager extends BaseMenuDetailPager implements View.OnClickListener{

	@ViewInject(R.id.lv_photo)
	private ListView lvPhoto;
	@ViewInject(R.id.gv_photo)
	private GridView gvPhoto;

	private ArrayList<PhotosBean.PhotoNews> mNewsList;

	private ImageButton btnPhoto;

	public PhotosMenuDetailPager(Activity activity, ImageButton btnPhoto) {
		super(activity);
		btnPhoto.setOnClickListener(this);// 组图切换按钮设置点击事件
		this.btnPhoto = btnPhoto;
	}

	@Override
	public View initView() {
//		TextView view = new TextView(mActivity);
//		view.setText("菜单详情页-组图");
//		view.setTextColor(Color.RED);
//		view.setTextSize(22);
//		view.setGravity(Gravity.CENTER);

		View view = View.inflate(mActivity, R.layout.pager_photos_menu_detail,
				null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		String cache = CacheUtils.getCache(GlobalConstants.PHOTOS_URL,
				mActivity);
		if (!TextUtils.isEmpty(cache)) {
			processData(cache);
		}

		getDataFromServer();

	}


	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpRequest.HttpMethod.GET, GlobalConstants.PHOTOS_URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						processData(result);

						CacheUtils.setCache(GlobalConstants.PHOTOS_URL, result,
								mActivity);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// 请求失败
						error.printStackTrace();
						Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
								.show();
					}
				});
	}


	protected void processData(String result) {
		Gson gson = new Gson();
		PhotosBean photosBean = gson.fromJson(result, PhotosBean.class);

		mNewsList = photosBean.data.news;

		lvPhoto.setAdapter(new PhotoAdapter());
		gvPhoto.setAdapter(new PhotoAdapter());// gridview的布局结构和listview完全一致,
		// 所以可以共用一个adapter
	}




	class PhotoAdapter extends BaseAdapter {

		private BitmapUtils mBitmapUtils;

		public PhotoAdapter() {
			mBitmapUtils = new BitmapUtils(mActivity);
			mBitmapUtils
					.configDefaultLoadingImage(R.drawable.pic_item_list_default);
		}

		@Override
		public int getCount() {
			return mNewsList.size();
		}

		@Override
		public PhotosBean.PhotoNews getItem(int position) {
			return mNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(mActivity,
						R.layout.list_item_photos, null);
				holder = new ViewHolder();
				holder.ivPic = (ImageView) convertView
						.findViewById(R.id.iv_pic);
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			PhotosBean.PhotoNews item = getItem(position);

			holder.tvTitle.setText(item.title);
			mBitmapUtils.display(holder.ivPic,GlobalConstants.SERVER_URL2 + item.listimage);

			return convertView;
		}

	}

	static class ViewHolder {
		public ImageView ivPic;
		public TextView tvTitle;
	}


	private boolean isListView = true;// 标记当前是否是listview展示

	@Override
	public void onClick(View v) {
		if (isListView) {
			// 切成gridview
			lvPhoto.setVisibility(View.GONE);
			gvPhoto.setVisibility(View.VISIBLE);
			btnPhoto.setImageResource(R.drawable.icon_pic_list_type);

			isListView = false;
		} else {
			// 切成listview
			lvPhoto.setVisibility(View.VISIBLE);
			gvPhoto.setVisibility(View.GONE);
			btnPhoto.setImageResource(R.drawable.icon_pic_grid_type);

			isListView = true;
		}
	}


}
