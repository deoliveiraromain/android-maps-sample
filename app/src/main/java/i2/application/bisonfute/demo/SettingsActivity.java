package i2.application.bisonfute.demo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

        final Button btnExample = (Button) findViewById(R.id.buttonSettings);
        btnExample.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ViewCompat.animate(v).alpha(0.2f).xBy(-100).yBy(100);
                        Animator anim = AnimatorInflater.loadAnimator(SettingsActivity.this, R.animator.animation);
                        anim.setTarget(btnExample);
                        anim.setDuration(1000);
                        anim.setStartDelay(10);
                        anim.start();
                    }
                }
        );

    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }else{
            ActivityCompat.finishAfterTransition(SettingsActivity.this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }else{
                ActivityCompat.finishAfterTransition(SettingsActivity.this);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
