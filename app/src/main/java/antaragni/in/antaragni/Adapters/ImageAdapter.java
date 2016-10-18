package antaragni.in.antaragni.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import antaragni.in.antaragni.Activities.MainActivity;
import antaragni.in.antaragni.DataHandler.ImageLoader;
import antaragni.in.antaragni.DataModels.Category;
import antaragni.in.antaragni.Utilities.EncodingUtil;
import antaragni.in.antaragni.serverFields.CurrentLine;
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
  public ArrayList<CurrentLine> list;
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
  public View getView(final int position, View convertView, ViewGroup parent) {
    View imageView=null;
      if(currentTab.equals("home")) {
        if (convertView == null) {
          imageView = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.linear_card, parent, false);
          ImageAdapter.ViewHolder vh = new ImageAdapter.ViewHolder(imageView);
          imageView.setTag(vh);
        } else {
          imageView = convertView;
        }
        ViewHolder vh=(ViewHolder)imageView.getTag();
        if (imageLinks!=null) {
          imageLoader.DisplayImage(ENDPOINT + imageLinks.get(position).url.get(0).image.filename, ((ImageView) vh.img));
          vh.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String type=imageLinks.get(position).text;
              if(imageLinks.get(position).text.equals("web")||imageLinks.get(position).text.equals("youtube")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageLinks.get(position).link));
                mContext.startActivity(intent);
              }
              else if(imageLinks.get(position).text.equals("maps")){
                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse(imageLinks.get(position).link);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mContext.startActivity(mapIntent);
              }
              else if (type.equals("pronites"))
                ((MainActivity)mContext).openFragment(R.id.nav_current_line);
              else if (type.equals("schedule"))
                ((MainActivity)mContext).openFragment(R.id.nav_schedule);
              else if (type.equals("lineup"))
                ((MainActivity)mContext).openFragment(R.id.nav_past_line);
              else if (type.equals("sponsors"))
                ((MainActivity)mContext).openFragment(R.id.nav_sponsors);
              else if (type.equals("about"))
                ((MainActivity)mContext).openFragment(R.id.nav_about);
              else if (type.equals("contact"))
                ((MainActivity)mContext).openFragment(R.id.nav_contact);
            }
          });
        }
      }else if(currentTab.equals("sponsors")){
        if(convertView==null){
          imageView=LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_card,parent,false);
          ImageAdapter.ViewHolder vh =new ImageAdapter.ViewHolder(imageView);
          imageView.setTag(vh);
        }else
          imageView=convertView;
        ViewHolder vh = (ViewHolder)(imageView.getTag());
        if(imageLinks!=null) {
          if(imageLinks.get(position).images!=null){
            String s = EncodingUtil.encodeURIComponent(imageLinks.get(position).images.filename);
            imageLoader.DisplayImage(ENDPOINT + s, ((ImageView) vh.img));
          }
        }
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
        if(dataList!=null) {
          vh.recycler.setAdapter(new CompAdapter(mContext, dataList.get(position).subevents));
          //Log.v("hello",""+dataList.get(position).Event);
          if(dataList.get(position).image!=null)
          imageLoader.DisplayImage(ENDPOINT + dataList.get(position).image.filename, (ImageView) (vh.img));
        }
      }else if (currentTab.equals("out")) {
        if (convertView == null) {
          imageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_activity_card, parent, false);
          ImageAdapter.ViewHolder vh = new ImageAdapter.ViewHolder(imageView);
          imageView.setTag(vh);
        } else
          imageView = convertView;
        ViewHolder vh = (ViewHolder) (imageView.getTag());
        if (list != null) {
          imageLoader.DisplayImage(ENDPOINT + (list.get(0).images.get(position).image.filename), ((ImageView) vh.img));
        }
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
          if(imageLinks.get(position).images!=null){
            String s = EncodingUtil.encodeURIComponent(imageLinks.get(position).images.filename);
            imageLoader.DisplayImage(ENDPOINT + s, ((ImageView) vh.img));
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

  public static ImageAdapter ImageAdapter2(ArrayList<CurrentLine> list, Context c, String tab) {
    ImageAdapter a= new ImageAdapter(null,null,c,tab);
    a.mContext = c;
    a.currentTab=tab;
    a.dataList=null;
    a.list=list;
    a.imageLoader = new ImageLoader(c.getApplicationContext());
    return a;
  }

  // Return the size of your dataset (invoked by the layout manager)
  @Override
  public int getCount() {
  if(list!=null){
    return list.get(0).images.size();
  }
  else if (dataList!=null)
    return dataList.size();
  else if (imageLinks!=null)
    return imageLinks.size();
  else return 10;
  }

}
