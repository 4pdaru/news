package four.pda;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import four.pda.ui.article.NewsActivity;
import four.pda.ui.article.NewsActivity_;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Klishin.Pavel on 08.02.2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class AllArticlesTest {

	private UiDevice device;

	@Rule
	public ActivityTestRule<NewsActivity> activityTestRule = new ActivityTestRule(NewsActivity_.class);

	@Before
	public void startMainActivityFromHomeScreen() throws RemoteException {
		// Initialize UiDevice instance
		device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
		device.wakeUp();
	}

	@Test
	public void allArticlesActivityTest() throws UiObjectNotFoundException, InterruptedException {
		//Открываем Navigaton Drawer методами UiAutomator:
		UiObject openDrawerButton = device.findObject(new UiSelector().className("android.widget.ImageButton").packageName("four.pda.debug").instance(0));
		openDrawerButton.click();
		device.waitForWindowUpdate("four.pda", 100);
		//Открываем категорию "Новости"
		onView(withId(R.id.all_category_view)).perform(click());
		//Открываем первую статью из списка
		UiObject openfirstButton = device.findObject(new UiSelector().className("android.widget.ImageView").packageName("four.pda.debug")
				.resourceId("four.pda.debug:id/image_view")
				.instance(0));
		openfirstButton.click();
		//Ждем пока окошко загрузиться
		device.waitForWindowUpdate("four.pda", 100);
		//Свайпаем туда-сюда
		onView(withId(R.id.drawer_layout)).perform(swipeUp()).perform(swipeUp()).perform(swipeUp()).perform(swipeUp());
		pressBack();
		onView(withId(R.id.drawer_layout)).perform(swipeUp()).perform(swipeUp()).perform(swipeUp()).perform(swipeUp());
		device.waitForIdle();
		//Нажимаем на Floating Button для возврата в начало списка
		onView(withId(R.id.up_button)).perform(click());
		onView(withId(R.id.drawer_layout)).perform(swipeDown()).perform(swipeDown());
		device.waitForWindowUpdate("four.pda", 100);
		device.waitForIdle(150);
		//Открываем вторую статью
		UiObject openSecondButton = device.findObject(new UiSelector().className("android.widget.ImageView").packageName("four.pda.debug")
				.resourceId("four.pda.debug:id/image_view")
				.instance(1));
		openSecondButton.click();
		//Ждем пока окошко загрузиться
		device.waitForWindowUpdate("four.pda", 100);
		//Свайпаем туда-сюда
		onView(withId(R.id.drawer_layout)).perform(swipeUp()).perform(swipeUp()).perform(swipeUp());
		//Возвращаемся назад
		pressBack();

	}

}
