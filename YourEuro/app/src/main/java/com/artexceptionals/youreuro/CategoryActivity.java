package com.artexceptionals.youreuro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.artexceptionals.youreuro.adapter.CategorySettingsAdapter;
import com.artexceptionals.youreuro.model.Category;
import com.maltaisn.icondialog.Icon;
import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity implements IconDialog.Callback{

    public static final String ADD_CATEGORY = "add_category";
    public static final String TYPE = "category_activity_type";
    public static final String ADD_THRESHOLD = "add_threshold";
    IconDialog iconDialog;
    Icon[] selectedIcons = new Icon[]{};

    @BindView(R.id.selectedIcon)
    ImageView categoryImageView;

    @BindView(R.id.new_catergory_name)
    EditText newCategoryEditText;

    @BindView(R.id.select_icon_button)
    Button selectIconButton;

    @BindView(R.id.save_category)
    Button saveCategoryButton;

//    @BindView(R.id.category_settings_listView)
//    ListView categoryListView;

    @BindView(R.id.category_settings_listView)
    RecyclerView mCategoryRecyclerView;

    @BindView(R.id.new_category_cardview)
    CardView newCategoryCardView;

    CategorySettingsAdapter categoryAdapter = null;
    MoneyControlManager moneyControlManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbarCategory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryImageView.setVisibility(View.GONE);
        saveCategoryButton.setEnabled(false);

        iconDialog = new IconDialog();

        moneyControlManager = MoneyControlManager.getInstance(YourEuroApp.getAppContext());

        boolean isAddCategory = getIntent().getStringExtra(TYPE).equalsIgnoreCase(ADD_CATEGORY);

        if (!isAddCategory)
            newCategoryCardView.setVisibility(View.GONE);

        categoryAdapter = new CategorySettingsAdapter(this, IconHelper.getInstance(this),moneyControlManager.getAllCategories(),isAddCategory);
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCategoryRecyclerView.setAdapter(categoryAdapter);

//        categoryListView.setAdapter(categoryAdapter);
        categoryAdapter.setCategoryListener(new CategorySettingsAdapter.CategoryListener() {
            @Override
            public void listen(int position) {
                Category category = categoryAdapter.getItem(position);
                categoryAdapter.remove(category);
                moneyControlManager.removeCategory(category);
            }
        });

        newCategoryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!newCategoryEditText.getText().toString().isEmpty() && categoryImageView.getVisibility() == View.VISIBLE){
                    saveCategoryButton.setEnabled(true);
                }else {
                    saveCategoryButton.setEnabled(false);
                }
            }
        });
        saveCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Category category = new Category(newCategoryEditText.getText().toString(),
                        String.valueOf(selectedIcons[0].getId()), false);
                moneyControlManager.addCategory(category);
                selectIconButton.setText(R.string.selectIcon);
                categoryImageView.setVisibility(View.GONE);
                categoryAdapter.add(category);
                newCategoryEditText.getText().clear();
            }
        });

        selectIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchIconDialog();
            }
        });
    }

    private void launchIconDialog() {
        iconDialog.setSelectedIcons(selectedIcons);
        iconDialog.show(getSupportFragmentManager(), "icon_dialog");
    }

    @Override
    public void onIconDialogIconsSelected(Icon[] icons) {
        selectedIcons = icons;
        categoryImageView.setImageDrawable(selectedIcons[0].getDrawable(this));
        categoryImageView.setVisibility(View.VISIBLE);
        selectIconButton.setText(R.string.edit_icon);

        if(!newCategoryEditText.getText().toString().isEmpty() && categoryImageView.getVisibility() == View.VISIBLE){
            saveCategoryButton.setEnabled(true);
        }else {
            saveCategoryButton.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        moneyControlManager.updateAllCategories(categoryAdapter.getCategories());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
