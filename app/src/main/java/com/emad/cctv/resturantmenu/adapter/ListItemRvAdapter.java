package com.emad.cctv.resturantmenu.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.emad.cctv.resturantmenu.R;
import com.emad.cctv.resturantmenu.model.DataItem;
import com.emad.cctv.resturantmenu.ui.ItemDetails;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;



public class ListItemRvAdapter extends RecyclerView.Adapter<ListItemRvAdapter.MyViewHolder>{

    public static final String ITEM_KEY ="item_key" ;
    private Context mContext;
    private List<DataItem> mDataItemList;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    public ListItemRvAdapter(Context context, List<DataItem> dataItemList) {
        mContext = context;
        mDataItemList = dataItemList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //here we are getting the default shared Preferences
        SharedPreferences settingPreferences= PreferenceManager.getDefaultSharedPreferences(mContext);
        // we need to know the sharedpref value to define which layout to view in each system
        boolean grid=settingPreferences
                .getBoolean(mContext.getString(R.string.grid_view_option_key),false);
        //here we are judging the grid value if ture then grid layout to be inflated in the Recycler view
        //else noremal list item to be inflated in the Recycler view
        int layout= grid? R.layout.list_item_grid :R.layout.list_item;

        View view= LayoutInflater.from(mContext).inflate(layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final DataItem item=mDataItemList.get(position);
        final String itemName= mDataItemList.get(position).getItemName();

        holder.itemNameText.setText(itemName);
        final String itemImage=mDataItemList.get(position).getPhoto();

        try {
            setImageViewDrawable(holder, itemImage);
        } catch (IOException e) {
            Log.e("Error",e.getMessage());
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mContext, ItemDetails.class);
                intent.putExtra(ITEM_KEY,item);
                mContext.startActivity(intent);
            }
        });
    }

    private void setImageViewDrawable(MyViewHolder holder, String itemImage) throws IOException {

        InputStream inputStream=mContext.getAssets().open(itemImage);
        Drawable drawable=Drawable.createFromStream(inputStream,null);
        holder.itemImageView.setImageDrawable(drawable);
        if (inputStream!=null) inputStream.close();
    }

    @Override
    public int getItemCount() {
        return mDataItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView itemNameText;
        ImageView itemImageView;
        View mView;

        MyViewHolder(View itemView) {
            super(itemView);
            itemNameText=(TextView) itemView.findViewById(R.id.item_name);
            itemImageView=(ImageView) itemView.findViewById(R.id.imageView);
            mView=itemView;
        }
    }


}
