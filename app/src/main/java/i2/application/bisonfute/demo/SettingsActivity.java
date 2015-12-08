package i2.application.bisonfute.demo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnExample = (Button) findViewById(R.id.buttonSettings);
        btnExample.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ViewCompat.animate(v).alpha(0.2f).xBy(-100).yBy(100);
                        Animator anim = AnimatorInflater.loadAnimator(SettingsActivity.this, R.animator.animation);
                        anim.setTarget(v);
                        anim.setDuration(1000);
                        anim.setStartDelay(10);
                    }
                }
        );

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.animator.left_in, R.animator.right_out);
    }

}
