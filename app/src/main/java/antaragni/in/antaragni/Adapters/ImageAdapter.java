package antaragni.in.antaragni.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import antaragni.in.antaragni.DataHandler.ImageLoader;
import antaragni.in.antaragni.DataModels.Category;
import antaragni.in.antaragni.serverFields.ImageModel;
import antaragni.in.antaragni.R;

/**
 * Created by varun on 3/10/16.
 */

public class ImageAdapter extends BaseAdapter {
  private Context mContext;
  public String currentTab;
  public Drawable mDrawable;
  public ArrayList<Category> dataList;
  public ArrayList<ImageModel> imageLinks;
  public ImageLoader imageLoader;
  public Object getItem(int position) {
    return null;
  }
  public String ENDPOINT="http://www.antaragni.in:7777/";

  public long getItemId(int position) {
    return 0;
  }
  public class ViewHoldertype{
    public ImageView img;
    public RecyclerView recycler;
    public ViewHoldertype(View v) {
      this.img= (ImageView) v.findViewById(R.id.image);
      this.recycler=(RecyclerView) v.findViewById(R.id.horizontal_scroll);
    }
  }
  public class ViewHolder{
    public View img;

    public ViewHolder(View v) {
      this.img= v.findViewById(R.id.image);

    }
  }
  // create a new ImageView for each item referenced by the Adapter
  public View getView(int position, View convertView, ViewGroup parent) {
    View imageView=null;
      if(currentTab.equals("home")) {
        if (convertView == null) {
          imageView = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.home_card, parent, false);
          ImageAdapter.ViewHolder vh = new ImageAdapter.ViewHolder(imageView);
          imageView.setTag(vh);
        } else {
          imageView = convertView;
        }
        ViewHolder vh=(ViewHolder)imageView.getTag();
        if (imageLinks!=null)
        imageLoader.DisplayImage(ENDPOINT+imageLinks.get(position).url.get(0).image.filename, ((ImageView)vh.img));
      }
      else if (currentTab.equals("competitions")){
        if(convertView==null) {
          imageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comp_horizontal_recycler, parent, false);
          ImageAdapter.ViewHoldertype vh = new ImageAdapter.ViewHoldertype(imageView);
          imageView.setTag(vh);
        }
        else
          imageView=convertView;
        ViewHoldertype vh=(ViewHoldertype)imageView.getTag();
        vh.recycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.mContext,
            LinearLayoutManager.HORIZONTAL, false);
        vh.recycler.setLayoutManager(layoutManager);
        if(dataList!=null)
          vh.recycler.setAdapter(new CompAdapter(mContext,dataList.get(position).subevents));
        vh.img.setImageDrawable(mDrawable);
      }
      else{
        if(convertView==null){
          imageView=LayoutInflater.from(parent.getContext()).inflate(R.layout.main_activity_card,parent,false);
          ImageAdapter.ViewHolder vh =new ImageAdapter.ViewHolder(imageView);
          imageView.setTag(vh);
        }else
          imageView=convertView;
        ViewHolder vh = (ViewHolder)(imageView.getTag());
        if(imageLinks!=null) {
          try {
            imageLoader.DisplayImage(ENDPOINT + URLEncoder.encode(imageLinks.get(position).images.filename, "%20"), ((ImageView) vh.img));
          }//Log.v("image loader",""+imageLinks.get(position).url.get(0).image.filename+" loading images.............");
          catch(UnsupportedEncodingException u){
            
          }
        }
      }
    return imageView;
  }


  public ImageAdapter(Drawable drawable, ArrayList<Category> list, Context c, String tab) {
    mDrawable=drawable;
    mContext = c;
    currentTab=tab;
    dataList=list;
    imageLoader = new ImageLoader(c.getApplicationContext());
  }

  public ImageAdapter(ArrayList<ImageModel> list, Context c, String tab) {
    mContext = c;
    currentTab=tab;
    dataList=null;
    imageLinks=list;
    imageLoader = new ImageLoader(c.getApplicationContext());
  }

  // Return the size of your dataset (invoked by the layout manager)
  @Override
  public int getCount() {
    if(dataList==null && imageLinks==null)
    return 10;
    else if (dataList!=null && imageLinks==null)
      return dataList.size();
    else
      return imageLinks.size();
  }
}
