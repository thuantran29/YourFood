package com.trannguyentanthuan2903.yourfood.Utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trannguyentanthuan2903.yourfood.R;
import com.trannguyentanthuan2903.yourfood.models.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 9/27/2017.
 */

public class PhotoListAdapter extends ArrayAdapter<Photo>{

    private static final String TAG = "PhotoListAdapter";


    private LayoutInflater mInflater;
    private List<Photo> mPhotos = null;
    private int layoutResource;
    private Context mContext;
    private ArrayList<Photo> arrayList;

    public PhotoListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Photo> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mPhotos = objects;

        this.arrayList = new ArrayList<Photo>();
        this.arrayList.addAll(mPhotos);
    }


    private static class ViewHolder{
        TextView tags, caption;
        ImageView showImage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            holder.tags = (TextView) convertView.findViewById(R.id.tags);
            holder.caption = (TextView) convertView.findViewById(R.id.caption);
            holder.showImage = (ImageView) convertView.findViewById(R.id.show_image);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tags.setText(getItem(position).getTags());
        holder.caption.setText(getItem(position).getCaption());

        Picasso.with(mContext).load(getItem(position).getImage_path()).into(holder.showImage);

//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.displayImage(getItem(position).getImage_path(),holder.showImage);

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference.child(mContext.getString(R.string.dbname_user_account_settings))
//                .orderByChild(mContext.getString(R.string.field_user_id))
//                .equalTo(getItem(position).getUser_id());
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
//                    Log.d(TAG, "onDataChange: found user: " +
//                            singleSnapshot.getValue(UserAccountSettings.class).toString());
//
//                    ImageLoader imageLoader = ImageLoader.getInstance();
////                    UniversalImageLoader.setImage(singleSnapshot.getValue(UserAccountSettings.class).getProfile_photo(), holder.profileImage, null, "");
//                    imageLoader.displayImage(singleSnapshot.getValue(UserAccountSettings.class).getProfile_photo(),
//                            holder.showImage);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mPhotos.clear();
        if (charText.length() == 0) {// offline tìm ra dc, còn online thi dợi add lại
            mPhotos.addAll(arrayList);
        } else {
            for (Photo pt : arrayList) {
                if (pt.getTags().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mPhotos.add(pt);
                }
            }
        }
        notifyDataSetChanged();
    }

}
