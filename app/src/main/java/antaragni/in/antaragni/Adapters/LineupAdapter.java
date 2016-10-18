package antaragni.in.antaragni.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import antaragni.in.antaragni.DataHandler.ImageLoader;
import antaragni.in.antaragni.DataModels.subEvent;
import antaragni.in.antaragni.R;
import antaragni.in.antaragni.serverFields.CurrentLine;

/**
 * Created by varun on 18/10/16.
 */

public class LineupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private Context mContext;
  public ArrayList<CurrentLine> dataList;
  public String ENDPOINT="http://www.antaragni.in:7777/";
  private ImageLoader imageLoader;
  public static class ViewHolderRest extends RecyclerView.ViewHolder {
    public ImageView image;
    public ViewHolderRest(View v) {
      super(v);
      image=(ImageView) v.findViewById(R.id.image);
    }
  }
  public static class ViewHolderTop extends RecyclerView.ViewHolder {
    public LinearLayout mLinearLayout;
    public TextView textTop;
    public ViewHolderTop(View v) {
      super(v);
      mLinearLayout=(LinearLayout) v.findViewById(R.id.top_text_line_up);
      textTop=(TextView)v.findViewById(R.id.topText);
    }
  }
  public LineupAdapter(Context context, ArrayList<CurrentLine> list) {
    mContext= context;
    dataList=list;
    imageLoader=new ImageLoader(context);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
    RecyclerView.ViewHolder vh;

    switch (viewType) {
      case 1:
        View v = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.current_line_text, parent, false);
        vh = new ViewHolderTop(v);
        break;
      default:
        View v2 = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.linear_card, parent, false);
        vh = new ViewHolderRest(v2);
    }
    return vh;
  }


  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if(dataList!=null) {
       if(dataList.get(0)!=null)
         if(position==0)
           ((ViewHolderTop)holder).textTop.setText(dataList.get(0).getMaintext());
          else {
           imageLoader.DisplayImage(ENDPOINT + dataList.get(0).images.get(position - 1).image.filename, ((ViewHolderRest) holder).image);
           ImageView img=((ViewHolderRest)holder).image;
           img.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

             }
           });
         }
    }
  }

  @Override
  public int getItemViewType(int position) {
    if(position==0)
      return 1;
    else
      return 2;
  }

  @Override
  public int getItemCount() {
    if(dataList!=null)
      return dataList.get(0).images.size()+1;
    else
      return 10;
  }
}

