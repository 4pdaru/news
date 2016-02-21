package four.pda.Autotests;

/**
 * Created by Klishin.Pavel on 01.02.2016.
 */

import android.app.Activity;
import android.app.Application;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.R;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.espresso.ViewAssertion;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.support.v7.app.ActionBarDrawerToggle;
import android.test.ActivityInstrumentationTestCase2;
import android.support.test.espresso.ViewFinder;
import android.support.test.espresso.*;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.ViewAction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import four.pda.App;
import four.pda.analytics.Analytics;
import four.pda.ui.AboutActivity;
import four.pda.ui.DrawerFragment;
import four.pda.ui.article.list.ListActivity;
import four.pda.ui.article.list.ListActivity_;
import four.pda.ui.article.list.ListFragment;
import four.pda.ui.article.list.ListFragment_;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.support.test.espresso.action.ScrollToAction;
import android.support.test.espresso.action.Swipe;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BaseFragmentTest {

	private UiDevice mDevice;
	private static final String TAG = "four.pda";

	@Rule
	public ActivityTestRule<ListActivity> mActivityRule = new ActivityTestRule(ListActivity_.class);


	@Before
	public void startMainActivityFromHomeScreen() throws RemoteException {
		// Initialize UiDevice instance
		mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
		mDevice.wakeUp();

	}

	@Test
	public void mainDrawerActivityTest () throws UiObjectNotFoundException, InterruptedException {

		//Открываем Navigaton Drawer методами UiAutomator:
		UiObject openDrawerButton = mDevice.findObject(new UiSelector().className("android.widget.ImageButton").packageName("four.pda.debug").instance(0));
		openDrawerButton.click();
		//Убеждаемся, что все пункты в наличии через Espresso:
		onView(withId(four.pda.R.id.all_category_view)).check(matches(isDisplayed())).check(matches(isClickable()));
		onView(withId(four.pda.R.id.news_category_view)).check(matches(isDisplayed())).check(matches(isClickable()));
		onView(withId(four.pda.R.id.articles_category_view)).check(matches(isDisplayed())).check(matches(isClickable()));
		onView(withId(four.pda.R.id.reviews_category_view)).check(matches(isDisplayed())).check(matches(isClickable()));
		onView(withId(four.pda.R.id.software_category_view)).check(matches(isDisplayed())).check(matches(isClickable()));
		onView(withId(four.pda.R.id.games_category_view)).check(matches(isDisplayed())).check(matches(isClickable()));
		onView(withId(four.pda.R.id.about_view)).check(matches(isDisplayed())).check(matches(isClickable()));
		Log.v(TAG, "Installed, all is OK");
	}
}
