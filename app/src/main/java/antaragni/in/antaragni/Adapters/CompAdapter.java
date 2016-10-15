package antaragni.in.antaragni.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import antaragni.in.antaragni.DataModels.subEvent;
import antaragni.in.antaragni.R;

/**
 * Created by varun on 15/10/16.
 */

public class CompAdapter extends RecyclerView.Adapter<CompAdapter.ViewHolder>{
  private String[] mDataset;
  private Context mContext;
  private ArrayList<subEvent> dataList;
  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextView;
    public LinearLayout mLinearLayout;
    public ViewHolder(View v) {
      super(v);
      mLinearLayout=(LinearLayout) v.findViewById(R.id.inside_horizontal_text);
      mTextView = (TextView) v.findViewById(R.id.name);
    }
  }
  public CompAdapter(Context context, ArrayList<subEvent> subEvents) {
    mContext= context;
    dataList=subEvents;
  }

  @Override
  public CompAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
    // create a new view
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.comp_horizontal_inner_box, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }


  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.mTextView.setText(dataList.get(position).diplay_name);
    if(position%2==0)
      holder.mLinearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.orange_light));
  }

  @Override
  public int getItemCount() {
    return 10;
  }
}

