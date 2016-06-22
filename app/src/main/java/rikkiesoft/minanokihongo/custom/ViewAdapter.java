package rikkiesoft.minanokihongo.custom;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import rikkiesoft.minanokihongo.R;
import rikkiesoft.minanokihongo.imageprocess.ImageUtils;

/**
 * Created by tuyenpx on 22/06/2016.
 */
public class ViewAdapter extends PagerAdapter {

    private ArrayList mListPicture;
    private LayoutInflater inflater;
    private WeakReference<Activity> mWeakReference;

    public ViewAdapter(Activity activity, ArrayList<String> mListPicture) {

        mWeakReference = new WeakReference<>(activity);
        inflater = LayoutInflater.from(mWeakReference.get());
        this.mListPicture = mListPicture;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.viewpager_item_learn_activity, container, false);
        ImageView imageview = (ImageView) view.findViewById(R.id.img_pic);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        final String manualView = (String) mListPicture.get(position);
        mWeakReference.get().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        imageview.setImageBitmap(ImageUtils.getsInstance().decodeSampledBitmapFromResource(manualView, displaymetrics.heightPixels, displaymetrics.widthPixels));


        ImageButton image_sound = (ImageButton) view.findViewById(R.id.btn_sound);
        if (image_sound != null)
            image_sound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        final TextView text = (TextView) view.findViewById(R.id.txt_text);

        ImageButton show_text = (ImageButton) view.findViewById(R.id.btn_show_text);
        if (show_text != null)
            show_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    text.setText(manualView);
                    text.setVisibility(View.VISIBLE);
                }
            });

        container.addView(view, 0);
        return view;
    }

    @Override
    public int getCount() {
        return mListPicture.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
