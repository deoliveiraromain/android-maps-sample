package i2.application.bisonfute.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * @author De Oliveira Romain
 */
public class ConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditions);
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
//                        Animator anim = AnimatorInflater.loadAnimator(ConditionsActivity.this, R.animator.animation);
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
        AnimUtils.finishActivityWithAnimation(ConditionsActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            AnimUtils.finishActivityWithAnimation(ConditionsActivity.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
