package com.artexceptionals.youreuro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.model.SliderPage;

public class AppIntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Simple to add","The plus button on the home screen"
                , R.drawable.add_transaction));
        addSlide(AppIntroFragment.newInstance("One-click save","With personal notes and recurring option"
                , R.drawable.save_transaction));
        addSlide(AppIntroFragment.newInstance("Filter your transaction","Direct text search or customise search"
                , R.drawable.view_transactions));
        addSlide(AppIntroFragment.newInstance("Statistical analysis","Various charts to visualise"
                , R.drawable.visualise_transaction));
        addSlide(AppIntroFragment.newInstance("Summarise the transaction","Share the pdf across other apps"
                , R.drawable.share_transactions));
        addSlide(AppIntroFragment.newInstance("Secure the app","Enable PIN security and have a recover option"
                , R.drawable.secure_transaction));

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
