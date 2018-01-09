package com.wanchang.employee.ui.me;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.mylhyl.circledialog.CircleDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Order;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.util.GlideApp;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderCardFragment extends BaseFragment {


  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv_order)
  RecyclerView mOrderRv;

  public BaseQuickAdapter<Order, BaseViewHolder> mOrderAdapter;


  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;

  private int status;


  public OrderCardFragment() {
    // Required empty public constructor
  }

  public static OrderCardFragment newInstance(int status) {
    Bundle args = new Bundle();
    args.putInt("status", status);
    OrderCardFragment fragment = new OrderCardFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_order_card;
  }

  @Override
  protected void initData() {
    status = getArguments().getInt("status");

    mOrderRv.setLayoutManager(new LinearLayoutManager(mContext));
    mOrderRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).size(16).build());
    mOrderRv.setAdapter(mOrderAdapter = new BaseQuickAdapter<Order, BaseViewHolder>(R.layout.item_order_list) {
      @Override
      protected void convert(BaseViewHolder helper, final Order item) {
        if (item.getStatus() == 10) {
          helper.setText(R.id.tv_order_status, "待支付");
        } else if (item.getStatus() == 11) {
          helper.setText(R.id.tv_order_status, "待审核");
        } else if (item.getStatus() == 20) {
          helper.setText(R.id.tv_order_status, "待发货");
        } else if (item.getStatus() == 40) {
          helper.setText(R.id.tv_order_status, "待收货");
        } else if (item.getStatus() == 100) {
          helper.setText(R.id.tv_order_status, "已完成");
        } else if (item.getStatus() == -1) {
          helper.setText(R.id.tv_order_status, "审核拒绝");
        } else if (item.getStatus() == 0) {
          helper.setText(R.id.tv_order_status, "已取消");
        }

        helper.setText(R.id.tv_order_id, "订单号: "+item.getId());
        helper.setText(R.id.tv_order_time, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(item.getCreated_at()*1000)));

        RecyclerView mProductRv = helper.getView(R.id.rv_product);
        final BaseQuickAdapter<Order.OrderProductListBean, BaseViewHolder> mProductAdapter;
        TextView mProductCountTv = helper.getView(R.id.tv_product_count);
        List<Order.OrderProductListBean> dataList = item.getProduct();
        mProductCountTv.setText("共"+dataList.size()+"件");

        mProductCountTv.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {
            startActivity(new Intent(mContext, OrderProductListActivity.class)
                .putExtra("order_id", item.getId())
            );
          }
        });

        mProductRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mProductRv.setAdapter(mProductAdapter = new BaseQuickAdapter<Order.OrderProductListBean, BaseViewHolder>(R.layout.item_confirm_order, dataList) {

          @Override
          protected void convert(BaseViewHolder helper, Order.OrderProductListBean item) {
            ImageView productIv = helper.getView(R.id.iv_pic);
            GlideApp.with(mContext).load(MallAPI.IMG_SERVER+item.getPic()).placeholder(R.drawable.ic_default_image).into(productIv);
          }
        });

        if (item.getDai() == 1) {
          helper.setVisible(R.id.tv_dai, true);
          helper.setText(R.id.tv_dai, "代下单("+item.getOrder_user_name()+")");
        } else {
          helper.setVisible(R.id.tv_dai, false);
        }
        helper.setText(R.id.tv_total, "共3种商品 合计：￥"+item.getTotal());


        if (item.getStatus() == 10) {
          helper.setGone(R.id.tv_cancel_order, true);
          helper.setGone(R.id.tv_pay, true);
          helper.setGone(R.id.tv_shouhuo, false);
        } else if(item.getStatus() == 40) {
          helper.setGone(R.id.tv_cancel_order, false);
          helper.setGone(R.id.tv_pay, false);
          helper.setGone(R.id.tv_shouhuo, true);
        } else {
          helper.setGone(R.id.tv_cancel_order, false);
          helper.setGone(R.id.tv_pay, false);
          helper.setGone(R.id.tv_shouhuo, false);
        }


        helper.addOnClickListener(R.id.tv_cancel_order);
        helper.addOnClickListener(R.id.tv_pay);
        helper.addOnClickListener(R.id.tv_shouhuo);

        mProductAdapter.setOnItemClickListener(new OnItemClickListener() {
          @Override
          public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Order.OrderProductListBean orderProduct = mProductAdapter.getItem(position);
            startActivity(new Intent(mContext, OrderDetailActivity.class).putExtra("order_id", orderProduct.getOrder_id()));
          }
        });
      }
    });

    mOrderAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
      @Override
      public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Order order = mOrderAdapter.getItem(position);
        switch (view.getId()) {
          case R.id.tv_cancel_order:
            //ToastUtils.showShort("cancel");
            onCancelOrder(order);
            break;
          case R.id.tv_pay:
            //ToastUtils.showShort("pay");
            onOrderPay(order);
            break;
          case R.id.tv_shouhuo:
            //ToastUtils.showShort("pay");
            onShouHuo(order);
            break;
        }
      }
    });

    mOrderAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Order order = mOrderAdapter.getItem(position);
        startActivity(new Intent(mContext, OrderDetailActivity.class).putExtra("order_id", order.getId()));
      }
    });
  }

  public void onCancelOrder(final Order order) {
    new CircleDialog.Builder(mContext)
        .setTitle("提示")
        .setText("您确定要取消吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            OkGo.<String>post(MallAPI.ORDER_CANCEL)
                .tag(this)
                .params("order_id", order.getId())
                .execute(new StringDialogCallback(mContext) {

                  @Override
                  public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    if (response.code() == 200) {
                      loadData();
                    }
                  }
                });
          }
        }).show();
  }

  public void onOrderPay(final Order order) {
    new CircleDialog.Builder(mContext)
        .setTitle("提示")
        .setText("您确定要支付吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            OkGo.<String>post(MallAPI.ORDER_PAYED)
                .tag(this)
                .params("order_id", order.getId())
                .params("pay_method", order.getPay_method())
                .execute(new StringDialogCallback(mContext) {

                  @Override
                  public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    if (response.code() == 200) {
                      loadData();
                    }
                  }
                });
          }
        }).show();
  }

  public void onShouHuo(final Order order) {
    new CircleDialog.Builder(mContext)
        .setTitle("提示")
        .setText("您确定要收货吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            OkGo.<String>post(MallAPI.ORDER_COMPLETE)
                .tag(this)
                .params("order_id", order.getId())
                .execute(new StringDialogCallback(mContext) {

                  @Override
                  public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    if (response.code() == 200) {
                      loadData();
                    }
                  }
                });
          }
        }).show();
  }


  @Override
  protected void initView() {
    loadData();

    refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
      @Override
      public void onLoadmore(RefreshLayout refreshlayout) {
        loadState = Constants.STATE_MORE;
        currentPage++;
        loadData();
      }

      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        loadState = Constants.STATE_REFRESH;
        currentPage = 1;
        loadData();
      }
    });

  }


  private void loadData() {
    OkGo.<String>get(MallAPI.ORDER)
        .tag(this)
        .params("expand", "product")
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<Order> orderList = JSON.parseArray(jsonObj.getString("items"), Order.class);
              if (loadState == Constants.STATE_NORMAL || loadState == Constants.STATE_REFRESH) {
                mOrderAdapter.setNewData(orderList);
                refreshLayout.finishRefresh();
              } else if (loadState == Constants.STATE_MORE) {
                LogUtils.e("----"+orderList.size());
                if (orderList.size() == 0) {
                  refreshLayout.finishLoadmore();
                  refreshLayout.setLoadmoreFinished(true);
                } else {
                  mOrderAdapter.addData(orderList);
                  refreshLayout.finishLoadmore();
                }
              }
            }
          }

          @Override
          public void onStart(Request<String, ? extends Request> request) {
            super.onStart(request);
            if (status != -100) {
              request.params("status", status);
            }
          }
        });
  }

}
