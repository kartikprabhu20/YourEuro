package com.artexceptionals.youreuro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    @BindView(R.id.category_settings_listView)
    ListView categoryListView;

    CategorySettingsAdapter categoryAdapter = null;
    MoneyControlManager moneyControlManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbarCategory);
        setSupportActionBar(toolbar);

        categoryImageView.setVisibility(View.GONE);

        iconDialog = new IconDialog();

        moneyControlManager = MoneyControlManager.getInstance(YourEuroApp.getAppContext());

        categoryAdapter = new CategorySettingsAdapter(this, IconHelper.getInstance(this),moneyControlManager.getAllCategories());
        categoryListView.setAdapter(categoryAdapter);
        categoryAdapter.setCategoryListener(new CategorySettingsAdapter.CategoryListener() {
            @Override
            public void listen(int position) {
                Category category = categoryAdapter.getItem(position);
                categoryAdapter.remove(category);
                moneyControlManager.removeCategory(category);
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
    }
}
