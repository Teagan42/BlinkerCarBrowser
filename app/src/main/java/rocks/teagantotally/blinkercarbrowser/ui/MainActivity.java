package rocks.teagantotally.blinkercarbrowser.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import rocks.teagantotally.blinkercarbrowser.R;
import rocks.teagantotally.blinkercarbrowser.databinding.ActivityMainBinding;
import rocks.teagantotally.blinkercarbrowser.events.NavigationEvent;
import rocks.teagantotally.blinkercarbrowser.ui.fragments.VehicleListFragment;

public class MainActivity
          extends BaseActivity {

    private View.OnClickListener openClickListener =
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      new NavigationEvent(VehicleListFragment.ROUTE).post();
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
