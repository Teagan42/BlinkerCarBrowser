package rocks.teagantotally.ibotta_challenge.ui;

import android.os.Bundle;
import android.view.View;

import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.events.NavigationEvent;
import rocks.teagantotally.ibotta_challenge.ui.fragments.RetailerListFragment;

public class MainActivity
          extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NavigationEvent(RetailerListFragment.ROUTE).post();
            }
        });
    }
}
