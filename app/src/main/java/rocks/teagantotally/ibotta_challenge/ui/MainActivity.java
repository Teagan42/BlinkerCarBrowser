package rocks.teagantotally.ibotta_challenge.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.databinding.ActivityMainBinding;
import rocks.teagantotally.ibotta_challenge.events.NavigationEvent;
import rocks.teagantotally.ibotta_challenge.ui.fragments.RetailerListFragment;

public class MainActivity
          extends BaseActivity {

    private View.OnClickListener openClickListener =
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      new NavigationEvent(RetailerListFragment.ROUTE).post();
                  }
              };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding =
                  DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setOpenClickListener(openClickListener);
    }
}
