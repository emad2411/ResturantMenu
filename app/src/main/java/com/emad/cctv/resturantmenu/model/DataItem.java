package com.emad.cctv.resturantmenu.model;

import android.os.Parcel;
import android.os.Parcelable;



public class DataItem implements Parcelable {


    private String mItemId;
    private String mItemName;
    private String mDescription;
    private String mCategory;
    private int mSortPosition;
    private double mPrice;
    private String mPhoto;

    public DataItem() {
    }


    public DataItem(String itemId, String itemName, String category, String description, int sortPosition, double price, String photo) {
        this.mItemId = itemId;
        this.mItemName = itemName;
        this.mDescription = description;
        mCategory = category;
        this.mSortPosition = sortPosition;
        this.mPrice = price;
        this.mPhoto =photo;


    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        this.mPrice = price;
    }

    public String getItemId() {
        return mItemId;
    }

    public void setItemId(String itemId) {
        this.mItemId = itemId;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        this.mItemName = itemName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public int getSortPosition() {
        return mSortPosition;
    }

    public void setSortPosition(int sortPosition) {
        this.mSortPosition = sortPosition;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        this.mPhoto = photo;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mItemId);
        dest.writeString(this.mItemName);
        dest.writeString(this.mDescription);
        dest.writeString(this.mCategory);
        dest.writeInt(this.mSortPosition);
        dest.writeDouble(this.mPrice);
        dest.writeString(this.mPhoto);
    }

    protected DataItem(Parcel in) {
        this.mItemId = in.readString();
        this.mItemName = in.readString();
        this.mDescription = in.readString();
        this.mCategory = in.readString();
        this.mSortPosition = in.readInt();
        this.mPrice = in.readDouble();
        this.mPhoto = in.readString();
    }

    public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel source) {
            return new DataItem(source);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };
}
