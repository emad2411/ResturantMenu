package com.emad.cctv.resturantmenu.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emad.cctv.resturantmenu.R;
import com.emad.cctv.resturantmenu.adapter.ListItemRvAdapter;
import com.emad.cctv.resturantmenu.model.DataItem;

import java.io.IOException;
import java.io.InputStream;

public class ItemDetails extends AppCompatActivity {

    TextView itemNameTxt,itemCatTxt,itemDescTxt,itemPriceTxt;
    ImageView itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        itemNameTxt=(TextView)findViewById(R.id.item_name_txt);
        itemCatTxt=(TextView)findViewById(R.id.item_cat_text);
        itemDescTxt=(TextView)findViewById(R.id.item_desc_txt);
        itemPriceTxt=(TextView)findViewById(R.id.item_price_text);
        itemImage=(ImageView) findViewById(R.id.item_image);


        DataItem item=getIntent().getParcelableExtra(ListItemRvAdapter.ITEM_KEY);

        if (item != null) {
            String itemName=item.getItemName();
            itemNameTxt.setText(itemName);

            String itemPrice=String.valueOf(item.getPrice());
            itemPriceTxt.setText(itemPrice+" $");

            String itemDesc=item.getDescription();
            itemDescTxt.setText(itemDesc);

            String itemCat=item.getCategory();
            itemCatTxt.setText(itemCat);


            String ImageName=item.getPhoto();

            try {
                setImage(ImageName);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private void setImage(String imageName) throws IOException {
        InputStream inputStream=this.getAssets().open(imageName);
        Drawable drawable=Drawable.createFromStream(inputStream,null);
        itemImage.setImageDrawable(drawable);
        if (inputStream!=null) inputStream.close();
    }
}
