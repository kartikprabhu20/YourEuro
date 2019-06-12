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

public class CategorySettingsAdapter extends ArrayAdapter<Category> {

    private final List<Category> categories = new ArrayList<>();
    private final IconHelper iconHelper;
    private Context mContext;
    private CategoryListener categoryListener;

    public CategorySettingsAdapter(Context context, IconHelper iconHelper, List<Category> categories) {
        super(context, R.layout.category_delete);
        categories.remove(0);
        this.iconHelper = iconHelper;
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
            convertView = mInflater.inflate(R.layout.category_delete, parent, false);
            mViewHolder.categoryImage = (ImageView) convertView.findViewById(R.id.category_image_in_settings);
            mViewHolder.categoryName = (TextView) convertView.findViewById(R.id.category_name_in_settings);
            mViewHolder.categoryDelete = (ImageView) convertView.findViewById(R.id.category_delete_in_settings);
            mViewHolder.categoryDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    categoryListener.listen(position);
                }
            });

        if(categories.get(position).isDefault) {
            mViewHolder.categoryImage.setImageDrawable(mContext.getResources().getDrawable(mContext.getResources().getIdentifier(categories.get(position).getImageID(), "drawable", mContext.getPackageName())));
            mViewHolder.categoryDelete.setVisibility(View.GONE);
        }else{
            iconHelper.addLoadCallback(new IconHelper.LoadCallback() {
                @Override
                public void onDataLoaded() {
                    mViewHolder.categoryImage.setImageDrawable(iconHelper.getIcon(Integer.parseInt(categories.get(position).getImageID())).getDrawable(mContext));
                }
            });

            mViewHolder.categoryDelete.setVisibility(View.VISIBLE);

        }
        mViewHolder.categoryName.setText(categories.get(position).getCatagoryName());

        return convertView;
    }

    private static class ViewHolder {
        ImageView categoryImage;
        TextView categoryName;
        ImageView categoryDelete;
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

    @Override
    public void add(@androidx.annotation.Nullable @Nullable Category object) {
        categories.add(object);
        notifyDataSetChanged();
    }

    @Override
    public void remove(@androidx.annotation.Nullable @Nullable Category object) {
        categories.remove(object);
        notifyDataSetChanged();
    }

    public void setCategoryListener(CategoryListener categoryListener) {
        this.categoryListener = categoryListener;
    }

    public interface CategoryListener{
        void listen(int position);
    }
}
