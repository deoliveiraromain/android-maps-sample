package i2.application.bisonfute.demo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * @author De Oliveira Romain
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        final Button btnExample = (Button) findViewById(R.id.buttonSettings);
//
//        // CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_settings);
//        //collapsingToolbarLayout.setTitle(getResources().getString(R.string.title_activity_settings));
//        btnExample.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //ViewCompat.animate(v).alpha(0.2f).xBy(-100).yBy(100);
//                        Animator anim = AnimatorInflater.loadAnimator(SettingsActivity.this, R.animator.animation);
//                        anim.setTarget(btnExample);
//                        anim.setDuration(1000);
//                        anim.setStartDelay(10);
//                        anim.start();
//                    }
//                }
//        );

    }

    @Override
    public void onBackPressed() {
        AnimUtils.finishActivityWithAnimation(SettingsActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            AnimUtils.finishActivityWithAnimation(SettingsActivity.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
