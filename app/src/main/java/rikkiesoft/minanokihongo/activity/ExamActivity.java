package rikkiesoft.minanokihongo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import rikkiesoft.minanokihongo.R;
import rikkiesoft.minanokihongo.activity.base.MinanoBaseActivity;

/**
 * Created by tuyenpx on 22/06/2016.
 */
public class ExamActivity extends MinanoBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_activity_layout);
    }
}
