package com.wanchang.employee.ui.report;

import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.ClientTag;
import com.wanchang.employee.ui.base.BaseActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.angmarch.views.NiceSpinner;

public class SalesmanReportActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.tv_start_time)
  TextView mStartTimeTv;
  @BindView(R.id.tv_end_time)
  TextView mEndTimeTv;

  @BindView(R.id.spinner_category)
  NiceSpinner mCategorySpinner;
  @BindView(R.id.spinner_tag)
  NiceSpinner mTagSpinner;

  @BindView(R.id.chart_price)
  LineChart mChart1;
  @BindView(R.id.chart_client_num)
  LineChart mChart2;

  private long time_start = getTimesmorning();
  private long time_end = getTimesnight();


  //获得当天0点时间
  public long getTimesmorning(){
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    return (cal.getTimeInMillis()/1000);
  }
  //获得当天24点时间
  public long getTimesnight(){
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    return (cal.getTimeInMillis()/1000);
  }


  private int salesman_id;

  private int client_category;
  private int client_tag;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_salesman_report;
  }

  @Override
  protected void initData() {
    salesman_id = getIntent().getIntExtra("salesman_id", -1);
  }



  @Override
  protected void initView() {
    mTitleTv.setText("报表");

    mStartTimeTv.setText(new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime()));
    mEndTimeTv.setText(new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime()));

    getClientCategory();
    getClientTag();

    mCategorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        client_category = i;
        getReport();
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

    mTagSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        client_tag = i;
        getReport();
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });
  }

  private void getClientCategory() {
    List<String> dataset = new LinkedList<>(Arrays.asList("药店类型", "商业", "连锁", "单店"));
    mCategorySpinner.attachDataSource(dataset);
  }

  List<ClientTag> clientTags;
  private void getClientTag() {
    OkGo.<String>get(MallAPI.CLIENT_CLIENT_TAG)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              clientTags = JSON.parseArray(jsonObj.getString("items"), ClientTag.class);
              List<String> dataset = new ArrayList<>();
              dataset.add("药店标签");
              for (ClientTag tag: clientTags) {
                dataset.add(tag.getTag());
              }
              mTagSpinner.attachDataSource(dataset);
            }
          }
        });
  }

  List<String> keysList;
  private void getReport() {
    OkGo.<String>get(MallAPI.CHART_SALESMAN_DETAIL)
        .tag(this)
        .params("time_start", time_start)
        .params("time_end", time_end)
        .params("salesman_id", salesman_id)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {

              JSONArray jsonArray = JSON.parseArray(response.body());

              ArrayList<Entry> yVals1 = new ArrayList<Entry>();
              ArrayList<Entry> yVals2 = new ArrayList<Entry>();
              keysList = new ArrayList<>();
              for (int i = 0; i < jsonArray.size(); i++) {
                String time = jsonArray.getJSONObject(i).getString("time");
                keysList.add(time);

                String price = jsonArray.getJSONObject(i).getString("price");
                float y1 = Float.parseFloat(price);
                yVals1.add(new Entry(i, y1));

                String client_num = jsonArray.getJSONObject(i).getString("client_num");
                float y2 = Float.parseFloat(client_num);
                yVals2.add(new Entry(i, y2));
              }


              //char 1
              LineDataSet d1 = new LineDataSet(yVals1, "销售金额");
              d1.setLineWidth(2.5f);
              d1.setCircleRadius(4.5f);
              d1.setColor(Color.parseColor("#EBAA2C"));
              d1.setCircleColor(Color.parseColor("#EBAA2C"));
              d1.setDrawFilled(true);
              d1.setFillColor(Color.parseColor("#EBAA2C"));
              d1.setFillAlpha(50);
              d1.setHighLightColor(Color.rgb(244, 117, 117));
              d1.setDrawValues(true);
              d1.setValueTextSize(12f);
              d1.setValueTextColor(Color.parseColor("#EBAA2C"));
              d1.setValueFormatter(new DefaultValueFormatter(2));
              XAxis xAxis1 = mChart1.getXAxis();
              xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
              xAxis1.setGranularity(1f);
              xAxis1.setValueFormatter(new MyCustomXAxisValueFormatter());

              ArrayList<ILineDataSet> sets1 = new ArrayList<ILineDataSet>();
              sets1.add(d1);

              LineData cd1 = new LineData(sets1);
              mChart1.getDescription().setEnabled(false);
              mChart1.getAxisRight().setEnabled(false);
              mChart1.getAxisLeft().setAxisMinValue(0);
              mChart1.getAxisLeft().setEnabled(false);
              mChart1.animateX(3000);
              mChart1.setData(cd1);
              mChart1.fitScreen();
              mChart1.setVisibleXRangeMaximum(6);


              //char 2
              LineDataSet d2 = new LineDataSet(yVals2, "客户数量");
              d2.setLineWidth(2.5f);
              d2.setCircleRadius(4.5f);
              d2.setColor(Color.parseColor("#A04BEF"));
              d2.setCircleColor(Color.parseColor("#A04BEF"));
              d2.setDrawFilled(true);
              d2.setFillColor(Color.parseColor("#A04BEF"));
              d2.setFillAlpha(50);
              d2.setHighLightColor(Color.rgb(244, 117, 117));
              d2.setDrawValues(true);
              d2.setValueTextSize(12f);
              d2.setValueTextColor(Color.parseColor("#A04BEF"));
              d2.setValueFormatter(new DefaultValueFormatter(0));
              XAxis xAxis2 = mChart2.getXAxis();
              xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
              xAxis2.setGranularity(1f);
              xAxis2.setValueFormatter(new MyCustomXAxisValueFormatter());

              ArrayList<ILineDataSet> sets2 = new ArrayList<ILineDataSet>();
              sets2.add(d2);

              LineData cd2 = new LineData(sets2);
              mChart2.getDescription().setEnabled(false);
              mChart2.getAxisRight().setEnabled(false);
              mChart2.getAxisLeft().setAxisMinValue(0);
              mChart2.getAxisLeft().setEnabled(false);
              mChart2.animateX(3000);
              mChart2.setData(cd2);
              mChart2.fitScreen();
              mChart2.setVisibleXRangeMaximum(6);
            }
          }

          @Override
          public void onStart(Request<String, ? extends Request> request) {
            super.onStart(request);
            if (client_category == 1) {
              request.params("client_category", 10);
            }
            if (client_category == 2) {
              request.params("client_category", 20);
            }
            if (client_category == 3) {
              request.params("client_category", 30);
            }

            if (client_tag != 0) {
              request.params("client_tag", clientTags.get(client_tag-1).getTag());
            }
          }
        });
  }

  private class MyCustomXAxisValueFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
      String xVal = keysList.get((int) (value % keysList.size()));
      return DateFormat.format("MM/dd", Long.parseLong(xVal) * 1000).toString();
    }
  }

  @OnClick(R.id.tv_start_time)
  public void startTime() {
    TimePickerView pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
      @Override
      public void onTimeSelect(Date date,View v) {//选中事件回调
        mStartTimeTv.setText(getTime(date));
        time_start = TimeUtils.string2Millis(getTime(date)+" 00:00:00", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"))/1000;
        getReport();
      }
    }).setType(new boolean[]{true, true, true, false, false, false}).build();
    pvTime.show();
  }

  @OnClick(R.id.tv_end_time)
  public void endTime() {
    TimePickerView pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
      @Override
      public void onTimeSelect(Date date,View v) {//选中事件回调
        mEndTimeTv.setText(getTime(date));
        time_end = TimeUtils.string2Millis(getTime(date)+" 23:59:59", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"))/1000;
        getReport();
      }
    }).setType(new boolean[]{true, true, true, false, false, false}).build();
    pvTime.show();
  }


  public String getTime(Date date) {//可根据需要自行截取数据显示
    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
    return format.format(date);
  }



  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
