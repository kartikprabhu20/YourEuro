package com.artexceptionals.youreuro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.artexceptionals.youreuro.R;
import com.artexceptionals.youreuro.model.Category;
import com.maltaisn.icondialog.IconHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilterAdapter extends ArrayAdapter<Category> {

    private final List<Category> categories = new ArrayList<>();
    private final List<Category> filterCategories = new ArrayList<>();
    private Context mContext;

    public CategoryFilterAdapter(Context context, List<Category> categories) {
        super(context, R.layout.category_checkbox);
        categories.remove(0);
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
        convertView = mInflater.inflate(R.layout.category_checkbox, parent, false);
        mViewHolder.categoryImage = (ImageView) convertView.findViewById(R.id.category_spinner_image);
        mViewHolder.categoryName = (TextView) convertView.findViewById(R.id.category_spinner_Name);
        mViewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.category_checkbox);
        mViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((CheckBox)view).isChecked()) {
                    filterCategories.add(categories.get(position));
                }else {
                    filterCategories.remove(categories.get(position));
                }
            }
        });
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

    private static class ViewHolder {
        ImageView categoryImage;
        TextView categoryName;
        CheckBox checkBox;
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

    public List<Category> getSelectedItems(){
        return filterCategories;
    }
}
