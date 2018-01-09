package com.wanchang.employee.ui.salesman.me;

import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Client;
import com.wanchang.employee.ui.MainActivity;
import com.wanchang.employee.ui.base.BaseActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;

public class MyClientDetailActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.tv_category)
  TextView mCategoryTv;

  @BindView(R.id.tv_tel)
  TextView mTelTv;
  @BindView(R.id.tv_address)
  TextView mAddressTv;
  @BindView(R.id.tv_name)
  TextView mNameTv;
  @BindView(R.id.tv_mobile)
  TextView mMobileTv;

  @BindView(R.id.tv_credit_used)
  TextView mCreditUsedTv;
  @BindView(R.id.tv_credit_amount)
  TextView mCreditAmountTv;
  @BindView(R.id.tv_bill_repayment_date)
  TextView mBillRepaymentTv;
  @BindView(R.id.tv_credit_total)
  TextView mCreditTotalTv;


  @BindView(R.id.tv_count)
  TextView mCountTv;
  @BindView(R.id.tv_total)
  TextView mTotalTv;

  @BindView(R.id.chart_count)
  LineChart mChart1;
  @BindView(R.id.chart_total)
  LineChart mChart2;


  @BindView(R.id.tv_purchase_proxy_validity)
  TextView mValidity1Tv;
  @BindView(R.id.tv_receiving_proxy_validity)
  TextView mValidity2Tv;
  @BindView(R.id.tv_purchase_contract_validity)
  TextView mValidity3Tv;
  @BindView(R.id.tv_license_validity)
  TextView mValidity4Tv;
  @BindView(R.id.tv_business_license_validity)
  TextView mValidity5Tv;
  @BindView(R.id.tv_gps_validity)
  TextView mValidity6Tv;



  private String clientId;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_my_client_detail;
  }

  @Override
  protected void initData() {
    clientId = getIntent().getStringExtra("client_id");
  }

  @Override
  protected void initView() {

    loadData();

    getReport();
  }

  List<String> keysList;
  List<String> keysList2;
  private void getReport() {
    OkGo.<String>get(MallAPI.CHART_CLIENT_CHART)
        .tag(this)
        .params("client_id", clientId)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {

              JSONObject jsonObj = JSON.parseObject(response.body());
              JSONObject chartObj = jsonObj.getJSONObject("chart");
              mCountTv.setText(chartObj.getString("count")+"单");
              mTotalTv.setText(chartObj.getString("total")+"元");

              try {
                org.json.JSONObject jsonObjectItems = new org.json.JSONObject(jsonObj.getString("chart_count"));
                Iterator<String> keys = jsonObjectItems.keys();
                keysList = new ArrayList<String>();
                ArrayList<Entry> yVals1 = new ArrayList<Entry>();

                while (keys.hasNext()) {
                  String dynamicKey = keys.next();
                  keysList.add(dynamicKey);
                  Collections.sort(keysList);
                }
                for (int i = 0; i < keysList.size(); i++) {
                  String count = jsonObjectItems.getString(keysList.get(i));
                  float y1 = Float.parseFloat(count);
                  yVals1.add(new Entry(i, y1));

                }
                //char 1
                LineDataSet d1 = new LineDataSet(yVals1, "订单总数");
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
                d1.setValueFormatter(new DefaultValueFormatter(0));
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


                org.json.JSONObject jsonObjectItems2 = new org.json.JSONObject(jsonObj.getString("total_chart"));
                Iterator<String> keys2 = jsonObjectItems2.keys();
                keysList2 = new ArrayList<String>();
                ArrayList<Entry> yVals2 = new ArrayList<Entry>();

                while (keys2.hasNext()) {
                  String dynamicKey = keys2.next();
                  keysList2.add(dynamicKey);
                  Collections.sort(keysList2);
                }
                for (int i = 0; i < keysList2.size(); i++) {
                  String total = jsonObjectItems2.getString(keysList2.get(i));
                  float y2 = Float.parseFloat(total);
                  yVals2.add(new Entry(i, y2));

                }
                //char 2
                LineDataSet d2 = new LineDataSet(yVals2, "订单总额");
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
                d2.setValueFormatter(new DefaultValueFormatter(2));
                XAxis xAxis2 = mChart2.getXAxis();
                xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis2.setGranularity(1f);
                xAxis2.setValueFormatter(new MyCustomXAxisValueFormatter2());

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

              } catch (JSONException e) {
                e.printStackTrace();
              }
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

  private class MyCustomXAxisValueFormatter2 implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
      String xVal = keysList2.get((int) (value % keysList2.size()));
      return DateFormat.format("MM/dd", Long.parseLong(xVal) * 1000).toString();
    }
  }

  private void loadData() {
    OkGo.<String>get(MallAPI.CLIENT+"/"+clientId)
        .tag(this)
        .params("expand", "credit,user")
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              Client client = JSON.parseObject(response.body(), Client.class);

              mTitleTv.setText(client.getName());

              if (client.getCategory() == 10) {
                mCategoryTv.setText("商业");
              } else if (client.getCategory() == 20) {
                mCategoryTv.setText("连锁");
              } else {
                mCategoryTv.setText("单店");
              }

              mTelTv.setText(client.getTel());
              mAddressTv.setText(client.getProvince()+client.getCity()+client.getCounty()+client.getAddress());
              mNameTv.setText(client.getUser().getName());
              mMobileTv.setText(client.getBind_mobile());

              mCreditUsedTv.setText("待回款 "+client.getCredit().getCredit_used()+"元");
              mCreditAmountTv.setText("可用授信额度: "+client.getCredit().getCredit_remain()+"元");
              mBillRepaymentTv.setText("默认账期"+client.getCredit().getBill_periods()+"个月 还款日"+client.getCredit().getRepayment_date()+"号");
              if ("0".equals(client.getCredit().getTemporary_credit())) {
                mCreditTotalTv.setText("额度"+client.getCredit().getCredit_amount()+"元");
              } else {
                mCreditTotalTv.setText("临时额度："+client.getCredit().getTemporary_credit()+"元");
              }


              mValidity1Tv.setText(getTime(client.getPurchase_proxy_validity()));
              mValidity2Tv.setText(getTime(client.getReceiving_proxy_validity()));
              mValidity3Tv.setText(getTime(client.getPurchase_contract_validity()));
              mValidity4Tv.setText(getTime(client.getLicense_validity()));
              mValidity5Tv.setText(getTime(client.getBusiness_license_validity()));
              mValidity6Tv.setText(getTime(client.getGps_validity()));

            }
          }
        });
  }

  private String getTime(long time) {
    return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time*1000));
  }

  @OnClick(R.id.ll_client_product_price)
  public void onGoProductPrice() {
    startActivity(new Intent(mContext, ProductPriceListActivity.class)
    .putExtra("client_id", clientId));
  }

  @OnClick(R.id.ll_sign)
  public void onGoSign() {
    startActivity(new Intent(mContext, SignActivity.class));
  }

  @OnClick(R.id.ll_dai_order)
  public void onGoDai() {
    startActivity(new Intent(mContext, MainActivity.class).putExtra("go_index", true));
  }

  @OnClick(R.id.tv_credit_apply)
  public void onGoCreditApply() {
    startActivity(new Intent(mContext, ClientCreditApplyActivity.class).putExtra("client_id", clientId));
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }


}
