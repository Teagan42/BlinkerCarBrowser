package rocks.teagantotally.ibotta_challenge.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import rocks.teagantotally.ibotta_challenge.R;
import rocks.teagantotally.ibotta_challenge.routing.Router;
import rocks.teagantotally.ibotta_challenge.ui.fragments.RetailerListFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by tglenn on 8/31/17.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RetailerListTest {
    @Rule
    public ActivityTestRule<ActivityContainer> mActivityTestRule =
              new ActivityTestRule<ActivityContainer>(ActivityContainer.class,
                                                      true,
                                                      false) {
                  @Override
                  protected Intent getActivityIntent() {
                      Intent i = super.getActivityIntent();
                      i.putExtra(Router.FRAGMENT,
                                 RetailerListFragment.class);

                      return i;
                  }
              };

    @Before
    public void setUp() throws
                        InterruptedException {
        mActivityTestRule.launchActivity(null);
        waitForUiToSettle();
    }

    @Test
    public void retailerListFragmentTest() {
        ViewInteraction viewGroup = onView(
                  allOf(withId(R.id.retailer_list),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                  allOf(withId(R.id.name),
                        withText("H-E-B"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.retailer_list),
                                            0),
                                  1),
                        isDisplayed()));
        textView.check(matches(withText("H-E-B")));

        ViewInteraction textView2 = onView(
                  allOf(withText("View Locations"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.retailer_list),
                                            0),
                                  2),
                        isDisplayed()));
        textView2.check(matches(withText("View Locations")));

        ViewInteraction textView3 = onView(
                  allOf(withText("View Offers"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.retailer_list),
                                            0),
                                  3),
                        isDisplayed()));
        textView3.check(matches(withText("View Offers")));

    }

    private static Matcher<View> childAtPosition(
              final Matcher<View> parentMatcher,
              final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                       && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private static void waitForUiToSettle() throws
                                           InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();

                return null;
            }
        };

        latch.await(1,
                    TimeUnit.SECONDS);
    }
}
