package rikkiesoft.minanokihongo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import rikkiesoft.minanokihongo.R;
import rikkiesoft.minanokihongo.activity.base.MinanoBaseActivity;

public class MainActivity extends MinanoBaseActivity implements View.OnClickListener {

    private Button mPractice;
    private Button mExam;
    private Button mLearning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPractice = (Button) findViewById(R.id.btn_practice);
        if (mPractice != null)
            mPractice.setOnClickListener(this);

        mExam = (Button) findViewById(R.id.btn_exam);
        if (mExam != null)
            mExam.setOnClickListener(this);
        mLearning = (Button) findViewById(R.id.btn_learn);
        if (mLearning != null)
            mLearning.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_learn:
                startActivity(new Intent(getBaseContext(), LeaningActivity.class));
                break;
            case R.id.btn_exam:
                startActivity(new Intent(getBaseContext(), ExamActivity.class));
                break;
            case R.id.btn_practice:
                startActivity(new Intent(getBaseContext(), PracticeActivity.class));
                break;
            default:
                break;
        }
    }
}
