package com.wanchang.employee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wanchang.employee.R;
import com.wanchang.employee.data.entity.Client;
import me.yokeyword.indexablerv.IndexableAdapter;


/**
 * Created by stormlei on 2017/7/13.
 */

public class ClientListAdapter extends IndexableAdapter<Client> {

  private LayoutInflater mInflater;

  public ClientListAdapter(Context context) {
    mInflater = LayoutInflater.from(context);
  }

  @Override
  public ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
    View view = mInflater.inflate(R.layout.item_index_client, parent, false);
    return new IndexVH(view);
  }

  @Override
  public ViewHolder onCreateContentViewHolder(ViewGroup parent) {
    View view = mInflater.inflate(R.layout.item_client, parent, false);
    return new ContentVH(view);
  }

  @Override
  public void onBindTitleViewHolder(ViewHolder holder, String indexTitle) {
    IndexVH vh = (IndexVH) holder;
    vh.tv.setText(indexTitle);
  }

  @Override
  public void onBindContentViewHolder(ViewHolder holder, Client entity) {
    ContentVH vh = (ContentVH) holder;
    vh.tv.setText(entity.getName());
  }

  private class IndexVH extends ViewHolder {
    TextView tv;

    public IndexVH(View itemView) {
      super(itemView);
      tv = (TextView) itemView.findViewById(R.id.tv_index);
    }
  }

  private class ContentVH extends ViewHolder {
    TextView tv;

    public ContentVH(View itemView) {
      super(itemView);
      tv = (TextView) itemView.findViewById(R.id.tv_name);
    }
  }
}
