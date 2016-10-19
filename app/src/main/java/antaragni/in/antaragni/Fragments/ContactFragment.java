package antaragni.in.antaragni.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cocosw.bottomsheet.BottomSheet;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import antaragni.in.antaragni.Adapters.ImageAdapter;
import antaragni.in.antaragni.DataHandler.DataManager;
import antaragni.in.antaragni.DataHandler.DatabaseAccess;
import antaragni.in.antaragni.DataHandler.ImageLoader;
import antaragni.in.antaragni.DataHandler.RetrofitAddOn;
import antaragni.in.antaragni.DataModels.Contact;
import antaragni.in.antaragni.OnFragmentInteractionListener;
import antaragni.in.antaragni.R;
import antaragni.in.antaragni.Utilities.EncodingUtil;
import antaragni.in.antaragni.serverFields.ContactSchema;
import antaragni.in.antaragni.serverFields.ImageModel;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by varun on 18/10/16.
 */

public class ContactFragment extends Fragment {
  private static final String KEY_TITLE = "title";
  private static String NAME = "name";
  private CompositeSubscription mSubscriptions;
  private DataManager mDataManager;
  public SimpleStringRecyclerViewAdapter recylclerAdapter;
  public ArrayList<ContactSchema> data;
  public RecyclerView mRecyclerView;


  public ContactFragment()
  {
    // Required empty public constructor
  }

  public static ContactFragment newInstance(String title, String cat) {
    ContactFragment f = new ContactFragment();
    Bundle args = new Bundle();
    args.putString(KEY_TITLE, title);
    args.putString(NAME,cat);
    f.setArguments(args);
    return (f);
  }
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mSubscriptions=new CompositeSubscription();
    RetrofitAddOn retrofitAddOn= RetrofitAddOn.getInstance(getActivity().getApplicationContext());
    mDataManager=new DataManager(getActivity().getApplicationContext());
    mDataManager.mService=retrofitAddOn.newUserService();
    loadData();
  }
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    // don't look at this layout it's just a listView to show how to handle the keyboard
    recylclerAdapter=new SimpleStringRecyclerViewAdapter(getActivity(), null);
    View rv= inflater.inflate(R.layout.fragment_current_line, container, false);
    WebView myWebView = (WebView) rv.findViewById(R.id.webviewcf);
    WebSettings webSettings = myWebView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    myWebView.loadUrl("https://www.antaragni.in/contact");

    mRecyclerView=(RecyclerView) rv.findViewById(R.id.current_line_recycler);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
    mRecyclerView.setAdapter(recylclerAdapter);
    return rv;
  }

  public void loadData() {
    mSubscriptions.add(mDataManager.getContacts()
        .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<ArrayList<ContactSchema>>() {
          @Override
          public void onCompleted() {
            Log.v("heloo","get is successssssss@@@@@@   past");
            recylclerAdapter.mValues=data;
            recylclerAdapter.notifyDataSetChanged();
          }

          @Override
          public void onError(Throwable e) {
            // cast to retrofit.HttpException to get the response code
            if (e instanceof HttpException) {
              HttpException response = (HttpException) e;
              int code = response.code();
            }
          }

          @Override
          public void onNext(ArrayList<ContactSchema> list) {
            data=list;
            Log.v("heloo","working is awesome here");

          }
        }));

  }
  public class SimpleStringRecyclerViewAdapter
      extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    public ImageLoader imageLoader;
    public ArrayList<ContactSchema> mValues;
    public String ENDPOINT="http://www.antaragni.in:7777/";
    public Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
      public String mBoundString;

      public final View mView;
      public final CircularImageView avatar;
      public final TextView mName;
      public final TextView mNumber;
      public final TextView mEmail;
      public final TextView mPost;

      public ViewHolder(View view) {
        super(view);
        mView = view;
        avatar = (CircularImageView) view.findViewById(R.id.avatar);
        mNumber = (TextView) view.findViewById(R.id.contact_num);
        mEmail = (TextView) view.findViewById(R.id.email);
        mName = (TextView) view.findViewById(R.id.name);
        mPost=(TextView) view.findViewById(R.id.position);
      }

      @Override
      public String toString() {
        return super.toString() + " '" + mName.getText();
      }
    }


    public SimpleStringRecyclerViewAdapter(Context context, ArrayList<ContactSchema> items) {
      context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
      mBackground = mTypedValue.resourceId;
      mValues = items;
      imageLoader = new ImageLoader(context);
      mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.contact_list_item, parent, false);
      view.setBackgroundResource(mBackground);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
      if(mValues!=null) {
        holder.mBoundString = mValues.get(position).text;
        String text = mValues.get(position).text;
        holder.mName.setText(text);
        holder.mNumber.setText(mValues.get(position).getnumber());
        holder.mEmail.setText(mValues.get(position).getEmail());
        holder.mPost.setText(mValues.get(position).getHeading());
        holder.mView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            new BottomSheet.Builder(getActivity()).title("Options").sheet(R.menu.contact_detail_menu).listener(new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                  case R.id.call:
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                      intent.setPackage("com.android.server.telecom");
                    } else {
                      intent.setPackage("com.android.phone");
                    }
                    intent.setData(Uri.parse("tel:" + mValues.get(position).getnumber()));
                    getActivity().startActivity(intent);
                    break;
                  case R.id.save:
                    Intent intent1 = new Intent(Intent.ACTION_INSERT);
                    intent1.setType(ContactsContract.Contacts.CONTENT_TYPE);
                    intent1.putExtra(ContactsContract.Intents.Insert.NAME, mValues.get(position).text);
                    intent1.putExtra(ContactsContract.Intents.Insert.PHONE, mValues.get(position).getnumber());
                    if (intent1.resolveActivity(getActivity().getPackageManager()) != null) {
                      startActivity(intent1);
                    }
                    break;
                }
              }
            }).show();
          }
        });
        imageLoader.DisplayImage(ENDPOINT + EncodingUtil.encodeURIComponent(mValues.get(position).images.filename), holder.avatar);
        holder.avatar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mValues.get(position).link));
            mContext.startActivity(intent);
          }
        });
      }

    }

    @Override
    public int getItemCount() {
      if(mValues!=null)
        return mValues.size();
      else
        return 0;
    }
  }
}

