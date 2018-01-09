package com.wanchang.employee.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.util.ACache;
import java.util.ArrayList;
import java.util.List;
import me.relex.circleindicator.CircleIndicator;

public class GuideActivity extends AppCompatActivity {

  private static final String TAG = GuideActivity.class.getSimpleName();

  private static int[] mImageIds = new int[]{R.drawable.guide_1, R.drawable.guide_2,
      R.drawable.guide_3, R.drawable.guide_4,R.drawable.guide_5,R.drawable.guide_6};
  private List<ImageView> mImageList;
  private Button mBtnStart;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    setContentView(R.layout.activity_guide);

    initView();
  }

  private void initView() {
    ViewPager mVpGuide = (ViewPager) findViewById(R.id.vp_guide);
    CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
    TextView mTvSkip = (TextView) findViewById(R.id.tv_skip);
    mBtnStart = (Button) findViewById(R.id.btn_start);

    mImageList = new ArrayList<>();
    for (int i = 0; i < mImageIds.length; i++) {
      ImageView image = new ImageView(this);
      image.setBackgroundResource(mImageIds[i]);
      mImageList.add(image);
    }

    mVpGuide.setAdapter(new GuideAdapter());
    indicator.setViewPager(mVpGuide);
    mVpGuide.addOnPageChangeListener(new GuidePageChangeListener());

    mBtnStart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ACache.get(GuideActivity.this).put(Constants.KEY_GUIDE_SHOWED, true);
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        finish();
      }
    });

    mTvSkip.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ACache.get(GuideActivity.this).put(Constants.KEY_GUIDE_SHOWED, true);
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        finish();
      }
    });
  }


  private class GuideAdapter extends PagerAdapter {

    @Override
    public int getCount() {
      return mImageList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
      return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      container.addView(mImageList.get(position));
      return mImageList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView(mImageList.get(position));
    }
  }

  private class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    // 某个页面被选中时回调此方法
    @Override
    public void onPageSelected(int position) {
      // 如果是最后一个页面，按钮可见，否则不可见
      if (position == mImageList.size() - 1) {
        mBtnStart.setVisibility(View.VISIBLE);
      } else {
        mBtnStart.setVisibility(View.INVISIBLE);
      }
    }
  }
}
