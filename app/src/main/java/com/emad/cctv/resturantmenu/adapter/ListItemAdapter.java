package com.emad.cctv.resturantmenu.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.emad.cctv.resturantmenu.R;
import com.emad.cctv.resturantmenu.model.DataItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;



public class ListItemAdapter extends BaseAdapter {
    Context mContext;
    private List<DataItem> mItem;

    public ListItemAdapter(Context context, List<DataItem> item) {
        mContext = context;
        mItem = item;
    }

    @Override
    public int getCount() {
        return mItem.size();
    }

    @Override
    public Object getItem(int position) {
        return mItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder holder;

        if (convertView == null) {
            holder=new MyViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
            holder.itemName=(TextView) convertView.findViewById(R.id.item_name);
            holder.itemImage=(ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }else{
            holder= (MyViewHolder) convertView.getTag();
        }

        holder.itemName.setText(mItem.get(position).getItemName());
       // in order to load the image dynamically  we need to copy all images in assets folder
        // to creat an assets folder go to project directory (on the up left corner of android studio)
        //app >> src>> main>> create directory name is assets>> then copy all images to assets folder

        //we create a method to
        try {
            setImage(position, holder);
        } catch (IOException e) {
            Log.e("Error",e.getMessage());
        }

        return convertView;
    }

    private void setImage(int position, MyViewHolder holder) throws IOException {
        //get the image name
        String imageName=mItem.get(position).getPhoto();
        //set an Input Stream to convert the message to bytes
        InputStream inputStream=mContext.getAssets().open(imageName);
        //create an image Drawable from the Input Stream
        Drawable drawable=Drawable.createFromStream(inputStream,null);
        //set the image to the drawable
        holder.itemImage.setImageDrawable(drawable);
        //finally we close the inputStream
        if(inputStream!=null){
            inputStream.close();
        }

    }

    private static class MyViewHolder{
        TextView itemName;
        ImageView itemImage;
    }
}
