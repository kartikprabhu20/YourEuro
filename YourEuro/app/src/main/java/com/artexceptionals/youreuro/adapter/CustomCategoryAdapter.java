package com.artexceptionals.youreuro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.artexceptionals.youreuro.R;
import com.artexceptionals.youreuro.model.Category;
import com.maltaisn.icondialog.IconHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomCategoryAdapter extends ArrayAdapter<Category> {

    private final List<Category> categories = new ArrayList<>();
    private Context mContext;

    public CustomCategoryAdapter(Context context, List<Category> categories) {
        super(context, R.layout.custom_spinner_row);
        this.categories.addAll(categories);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder mViewHolder = new ViewHolder();

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.custom_spinner_row, parent, false);
        mViewHolder.categoryImage = (ImageView) convertView.findViewById(R.id.category_spinner_image);
        mViewHolder.categoryName = (TextView) convertView.findViewById(R.id.category_spinner_Name);
        convertView.setTag(mViewHolder);

        IconHelper iconHelper = IconHelper.getInstance(mContext);
        if(categories.get(position).isDefault) {
            mViewHolder.categoryImage.setImageDrawable(mContext.getResources().getDrawable(mContext.getResources().getIdentifier(categories.get(position).getImageID(), "drawable", mContext.getPackageName())));
        }else{
            iconHelper.addLoadCallback(new IconHelper.LoadCallback() {
                @Override
                public void onDataLoaded() {
                    mViewHolder.categoryImage.setImageDrawable(iconHelper.getIcon(Integer.parseInt(categories.get(position).getImageID())).getDrawable(mContext));
                }
            });
        }
        mViewHolder.categoryName.setText(categories.get(position).getCatagoryName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private static class ViewHolder {
        ImageView categoryImage;
        TextView categoryName;
    }

    @Nullable
    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    @Override
    public int getPosition(@Nullable Category item) {
        return categories.indexOf(item);
    }
}
