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
import rocks.teagantotally.blinkercarbrowser.common.UITestUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActivityContainerSearchTest {

    @Rule
    public ActivityTestRule<ActivityContainer> mActivityTestRule = new ActivityTestRule<>(ActivityContainer.class);

    @Test
    public void activityContainerSearchTest() throws
                                              InterruptedException {
        ViewInteraction appCompatImageButton = onView(
                  allOf(withContentDescription("Open Drawer"),
                        withParent(allOf(withId(R.id.action_bar),
                                         withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatCheckBox = onView(
                  allOf(withId(R.id.show_search_bar),
                        withText("Show Search Bar"),
                        isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction editText = onView(
                  withId(R.id.query_text));
        editText.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText = onView(
                  allOf(withClassName(is("android.support.v7.widget.AppCompatEditText")),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Audi"),
                                  closeSoftKeyboard());

        UITestUtil.waitForUiToSettle();

        ViewInteraction textView = onView(
                  allOf(withId(R.id.year),
                        withText("2013"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.vehicle_list),
                                            0),
                                  0),
                        isDisplayed()));
        textView.check(matches(withText("2013")));

        ViewInteraction textView2 = onView(
                  allOf(withId(R.id.make),
                        withText("Audi"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.vehicle_list),
                                            0),
                                  1),
                        isDisplayed()));
        textView2.check(matches(withText("Audi")));

        ViewInteraction textView3 = onView(
                  allOf(withId(R.id.model),
                        withText("A5"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.vehicle_list),
                                            0),
                                  2),
                        isDisplayed()));
        textView3.check(matches(withText("A5")));

        ViewInteraction textView4 = onView(
                  allOf(withId(R.id.mileage),
                        withText("17216"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.vehicle_list),
                                            0),
                                  3),
                        isDisplayed()));
        textView4.check(matches(withText("17216")));

        ViewInteraction textView5 = onView(
                  allOf(withId(R.id.year),
                        withText("2013"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.vehicle_list),
                                            1),
                                  0),
                        isDisplayed()));
        textView5.check(matches(withText("2013")));

        ViewInteraction textView6 = onView(
                  allOf(withId(R.id.make),
                        withText("Audi"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.vehicle_list),
                                            1),
                                  1),
                        isDisplayed()));
        textView6.check(matches(withText("Audi")));

        ViewInteraction textView7 = onView(
                  allOf(withId(R.id.model),
                        withText("A5"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.vehicle_list),
                                            1),
                                  2),
                        isDisplayed()));
        textView7.check(matches(withText("A5")));

        ViewInteraction textView8 = onView(
                  allOf(withId(R.id.mileage),
                        withText("17216"),
                        childAtPosition(
                                  childAtPosition(
                                            withId(R.id.vehicle_list),
                                            1),
                                  3),
                        isDisplayed()));
        textView8.check(matches(withText("17216")));

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
