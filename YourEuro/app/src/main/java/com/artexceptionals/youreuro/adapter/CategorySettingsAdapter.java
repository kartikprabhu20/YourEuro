package com.artexceptionals.youreuro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.artexceptionals.youreuro.R;
import com.artexceptionals.youreuro.helpers.CurrencyInputFilter;
import com.artexceptionals.youreuro.model.Category;
import com.maltaisn.icondialog.IconHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategorySettingsAdapter  extends RecyclerView.Adapter<CategorySettingsAdapter.CategoryViewHolder> {

    private final List<Category> categories = new ArrayList<>();
    private final IconHelper iconHelper;
    private final boolean isAddCategory;
    private Context mContext;
    private CategoryListener categoryListener;

    public CategorySettingsAdapter(Context context, IconHelper iconHelper, List<Category> categories, boolean isAddCategory) {
//        super(context, R.layout.category_delete);
        categories.remove(0);
        this.iconHelper = iconHelper;
        this.categories.addAll(categories);
        this.mContext = context;
        this.isAddCategory = isAddCategory;
    }


    public List<Category> getCategories() {
        return categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_delete,viewGroup,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position) {

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = mInflater.inflate(R.layout.category_delete, parent, false);
//        categoryViewHolder.categoryImage = (ImageView) convertView.findViewById(R.id.category_image_in_settings);
//        categoryViewHolder.categoryName = (TextView) convertView.findViewById(R.id.category_name_in_settings);
//        categoryViewHolder.categoryDelete = (ImageView) convertView.findViewById(R.id.category_delete_in_settings);
//        categoryViewHolder.categoryThreshold = convertView.findViewById(R.id.category_threshold_et);

        categoryViewHolder.categoryThreshold.setFilters(new InputFilter[]{new CurrencyInputFilter(8,2)});

        if (isAddCategory) {
            categoryViewHolder.categoryThreshold.setVisibility(View.GONE);
            categoryViewHolder.categoryDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    categoryListener.listen(position);
                }
            });
        }else {
            categoryViewHolder.categoryThreshold.setText(String.format ("%.2f", categories.get(position).getThreshold()));
            categoryViewHolder.categoryDelete.setVisibility(View.GONE);

            categoryViewHolder.categoryThreshold.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String value = categoryViewHolder.categoryThreshold.getText().toString();
                    categories.get(position).setThreshold(Float.parseFloat(value.isEmpty()? "0": value));
                }
            });
        }

        if(categories.get(position).isDefault) {
            categoryViewHolder.categoryImage.setImageDrawable(mContext.getResources().getDrawable(mContext.getResources().getIdentifier(categories.get(position).getImageID(), "drawable", mContext.getPackageName())));
            categoryViewHolder.categoryDelete.setVisibility(View.GONE);
        }else{
            iconHelper.addLoadCallback(new IconHelper.LoadCallback() {
                @Override
                public void onDataLoaded() {
                    categoryViewHolder.categoryImage.setImageDrawable(iconHelper.getIcon(Integer.parseInt(categories.get(position).getImageID())).getDrawable(mContext));
                }
            });

            categoryViewHolder.categoryDelete.setVisibility(isAddCategory ? View.VISIBLE : View.GONE);

        }
        categoryViewHolder.categoryName.setText(categories.get(position).getCatagoryName());
    }

    @Override
    public int getItemCount() {
        if (categories != null) {
            return categories.size();
        }
        return 0;
    }


    public class CategoryViewHolder extends  RecyclerView.ViewHolder{
        @BindView(R.id.category_image_in_settings)
        ImageView categoryImage;

        @BindView(R.id.category_name_in_settings)
        TextView categoryName;

        @BindView(R.id.category_delete_in_settings)
        ImageView categoryDelete;

        @BindView(R.id.category_threshold_et)
        EditText categoryThreshold;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Nullable
    public Category getItem(int position) {
        return categories.get(position);
    }

    public int getPosition(@Nullable Category item) {
        return categories.indexOf(item);
    }

    public void add(@androidx.annotation.Nullable @Nullable Category object) {
        categories.add(object);
        notifyDataSetChanged();
    }

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
