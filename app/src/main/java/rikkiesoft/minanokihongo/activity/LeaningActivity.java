package rikkiesoft.minanokihongo.activity;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import rikkiesoft.minanokihongo.R;
import rikkiesoft.minanokihongo.activity.base.MinanoBaseActivity;
import rikkiesoft.minanokihongo.custom.ViewAdapter;

/**
 * Created by tuyenpx on 22/06/2016.
 */
public class LeaningActivity extends MinanoBaseActivity implements View.OnClickListener {

    private ImageButton mMenuHome;
    private ImageButton mManuShare;
    private ImageButton mManuInfo;
    private ArrayList mListPic = new ArrayList();
    public static final String Path_Dir_katana = Environment.getExternalStorageDirectory() + "/learning/katana";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_activity_layout);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mMenuHome = (ImageButton) findViewById(R.id.btn_back);
        if (mMenuHome != null)
            mMenuHome.setOnClickListener(this);
        mManuShare = (ImageButton) findViewById(R.id.btn_share);
        if (mManuShare != null)
            mManuShare.setOnClickListener(this);
        mManuInfo = (ImageButton) findViewById(R.id.btn_settings);
        if (mManuInfo != null)
            mManuInfo.setOnClickListener(this);
        new loadImage().execute();
    }


    class loadImage extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            copyAssets();
            loadPicture();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setupViewPager();
        }
    }


    private void copyAssets() {
        File filexxx = new File(Path_Dir_katana);
        if (!filexxx.exists()) {
            boolean n = filexxx.mkdirs();
            if (n) {
                AssetManager assetManager = getAssets();
                String[] files = null;
                try {
                    files = assetManager.list("katakana");
                } catch (IOException e) {
                    Log.e("tag", "Failed to get asset file list.", e);
                }
                Log.e("tuyenpx", "files = " + files.length);
                if (files != null) for (String filename : files) {
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = assetManager.open("katakana/" + filename);
                        File outFile = new File(Path_Dir_katana, filename);
                        out = new FileOutputStream(outFile);
                        copyFile(in, out);
                    } catch (IOException e) {
                        Log.e("tag", "Failed to copy asset file: " + filename, e);
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                            }
                        }
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }


    private void loadPicture() {
        if (mListPic.size() > 0) {
            mListPic.clear();
        }
        File filexxx = new File(Path_Dir_katana);
        File[] s = filexxx.listFiles();
        if (s != null && s.length > 0)
            for (File string : s) {
                mListPic.add(string.getPath());
            }
    }

    private ViewPager mViewPager;

    private void setupViewPager() {

        if (mViewPager == null) {
            mViewPager = (ViewPager) findViewById(R.id.viewpager);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
        mViewPager.setAdapter(new ViewAdapter(this, mListPic));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_share:
                break;
            case R.id.btn_settings:
                break;
            default:
                break;
        }

    }
}
