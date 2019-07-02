package com.artexceptionals.youreuro;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.paolorotolo.appintro.AppIntroViewPager;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.ISlideSelectionListener;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

public class AppIntroFragment  extends Fragment implements ISlideSelectionListener,
        ISlideBackgroundColorHolder {

    ImageView introImage;
    TextView introTitle,introDescription;
    LinearLayout bufferView;
    protected AppIntroViewPager pager;

    private String title, description;
    private int imageID, bufferHeight;

    public static AppIntroFragment newInstance(String title, String description, @DrawableRes int imageDrawable) {
        AppIntroFragment appIntroFragment = new AppIntroFragment();
        appIntroFragment.setTitle(title);
        appIntroFragment.setDescription(description);
        appIntroFragment.setImage(imageDrawable);
        return appIntroFragment;
    }

    public static Fragment newInstance(Fragment newFragment, int height) {
        AppIntroFragment appIntroFragment = newInstance(((AppIntroFragment) newFragment).title, ((AppIntroFragment) newFragment).description, ((AppIntroFragment) newFragment).imageID);
        appIntroFragment.setBufferHeight(height);
        return appIntroFragment;
    }

    private void setBufferHeight(int bufferHeight) {
        this.bufferHeight = bufferHeight;
    }

    private void setImage(int imageDrawable) {
        this.imageID = imageDrawable;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public void setBuffer(int bufferHeight) {
        bufferView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,bufferHeight));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        ButterKnife.bind(getActivity());
        return inflater.inflate(R.layout.fragment_appintro, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        introTitle = view.findViewById(R.id.intro_title);
        introDescription = view.findViewById(R.id.intro_description);
        introImage = view.findViewById(R.id.intro_image);
        bufferView =view.findViewById(R.id.pager_placeholder);
        pager = getActivity().findViewById(R.id.view_pager);

        introTitle.setText(title);
        introDescription.setText(description);
        Picasso.with(getActivity()).load(imageID).into(introImage);
    }

    @Override
    public int getDefaultBackgroundColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {

    }

    @Override
    public void onSlideSelected() {

    }

    @Override
    public void onSlideDeselected() {

    }
}
