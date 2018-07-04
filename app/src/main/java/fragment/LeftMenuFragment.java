package fragment;

import android.view.View;
import com.example.administrator.zhbj.R;

/**
 * 侧边栏fragment
 * 
 * @author Kevin
 * @date 2015-10-18
 */
public class LeftMenuFragment extends BaseFragment {

	private int mCurrentPos;// 当前被选中的item的位置

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		return view;
	}

	@Override
	public void initData() {
	}







}
