package rocks.teagantotally.blinkercarbrowser.ui;


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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rocks.teagantotally.blinkercarbrowser.R;

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
public class ActivityContainerGroupByYearTest {

    @Rule
    public ActivityTestRule<ActivityContainer> mActivityTestRule = new ActivityTestRule<>(ActivityContainer.class);

    @Test
    public void activityContainerGroupByYearTest() {
        ViewInteraction appCompatImageButton = onView(
                  allOf(withContentDescription("Open Drawer"),
                        withParent(allOf(withId(R.id.action_bar),
                                         withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatCheckBox = onView(
                  allOf(withId(R.id.group_by_year),
                        withText("Group by Year"),
                        isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction textView = onView(
                  allOf(withText("1999"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.vehicle_list),
                                            0),
                                  0),
                        isDisplayed()));
        textView.check(matches(withText("1999")));

        ViewInteraction textView2 = onView(
                  allOf(withText("2013"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.vehicle_list),
                                            3),
                                  0),
                        isDisplayed()));
        textView2.check(matches(withText("2013")));

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
