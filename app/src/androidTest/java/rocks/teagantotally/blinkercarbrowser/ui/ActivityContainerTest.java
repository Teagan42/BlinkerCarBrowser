package rocks.teagantotally.blinkercarbrowser.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.SwipeRefreshLayout;
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

import rocks.teagantotally.blinkercarbrowser.R;
import rocks.teagantotally.blinkercarbrowser.common.UITestUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActivityContainerTest {

    @Rule
    public ActivityTestRule<ActivityContainer> mActivityTestRule =
              new ActivityTestRule<>(ActivityContainer.class);

    @Before
    public void setUp() throws
                        InterruptedException {
        UITestUtil.waitForUiToSettle();
    }

    @Test
    public void activityContainerTest() {
        ViewInteraction textView = onView(
                  allOf(withText("Vehicles"),
                        childAtPosition(
                                  allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                  withId(R.id.action_bar_container),
                                                  0)),
                                  0),
                        isDisplayed()));
        textView.check(matches(withText("Vehicles")));

        ViewInteraction imageButton = onView(
                  allOf(withContentDescription("Open Drawer"),
                        childAtPosition(
                                  allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                  withId(R.id.action_bar_container),
                                                  0)),
                                  1),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction viewGroup = onView(
                  allOf(childAtPosition(
                            withId(R.id.vehicle_list),
                            1),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction appCompatImageButton = onView(
                  allOf(withContentDescription("Open Drawer"),
                        withParent(allOf(withId(R.id.action_bar),
                                         withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction checkBox = onView(
                  allOf(withId(R.id.search_year),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.drawer_layout),
                                            1),
                                  3),
                        isDisplayed()));
        checkBox.check(matches(isDisplayed()));

        ViewInteraction checkBox2 = onView(
                  allOf(withId(R.id.show_search_bar),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.drawer_layout),
                                            1),
                                  2),
                        isDisplayed()));
        checkBox2.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                  allOf(withId(R.id.year),
                        withText("2013"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.vehicle_list),
                                            1),
                                  0),
                        isDisplayed()));
        textView2.check(matches(withText("2013")));

        ViewInteraction checkBox3 = onView(
                  allOf(withId(R.id.search_model),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.drawer_layout),
                                            1),
                                  5),
                        isDisplayed()));
        checkBox3.check(matches(isDisplayed()));

        ViewInteraction checkBox4 = onView(
                  allOf(withId(R.id.group_by_year),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.drawer_layout),
                                            1),
                                  8),
                        isDisplayed()));
        checkBox4.check(matches(isDisplayed()));

        ViewInteraction checkBox5 = onView(
                  allOf(withId(R.id.group_by_make),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.drawer_layout),
                                            1),
                                  9),
                        isDisplayed()));
        checkBox5.check(matches(isDisplayed()));

        ViewInteraction checkBox6 = onView(
                  allOf(withId(R.id.reverse_groups),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.drawer_layout),
                                            1),
                                  10),
                        isDisplayed()));
        checkBox6.check(matches(isDisplayed()));

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
}
