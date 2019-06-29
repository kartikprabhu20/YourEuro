package com.artexceptionals.youreuro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class AppIntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(getSliderPage("New to YourEuro App","Follow the intro to know how to add transaction",
                R.drawable.home_screen_intro,ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))));
        addSlide(AppIntroFragment.newInstance(getSliderPage("", "",
                R.drawable.details_page_empty_intro, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))));
        addSlide(AppIntroFragment.newInstance(getSliderPage("", "",
                R.drawable.details_page_intro, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))));
        addSlide(AppIntroFragment.newInstance(getSliderPage("", "",
                R.drawable.details_page_recurring_intro, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))));
        addSlide(AppIntroFragment.newInstance(getSliderPage("", "",
                R.drawable.details_page_save_intro, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))));
        addSlide(AppIntroFragment.newInstance(getSliderPage("", "",
                R.drawable.homepage_transaction_intro, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))));

        showSkipButton(true);
        setZoomAnimation();
    }

    private SliderPage getSliderPage(String title, String description, int imageId, int color) {
        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle(title);
        sliderPage.setDescription(description);
        sliderPage.setImageDrawable(imageId);
        sliderPage.setBgColor(color);

        return sliderPage;
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
