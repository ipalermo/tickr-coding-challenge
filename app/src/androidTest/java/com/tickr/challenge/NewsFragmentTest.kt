package com.tickr.challenge

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.tickr.challenge.utilities.chooser
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testClickOnItemNavigatesTo() {
        val recyclerView = onView(
            withId(R.id.news_list),
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(2, click()))

        pressBack()

        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        pressBack()
    }

    @Test
    fun testListAndItemsAreDisplayed() {
        val recyclerView = onView(
            withId(R.id.news_list),
        )

        recyclerView.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(R.id.item_photo), withContentDescription("Picture of News"),
                withParent(
                    withParent(
                        IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java)
                    )
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))
    }

    @Test
    fun testViewIntent() {
        val recyclerView = onView(
            withId(R.id.news_list),
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(2, click()))

        Intents.init()
        onView(ViewMatchers.withText("")).perform(click())
        Intents.intended(
            chooser(
                CoreMatchers.allOf(
                    IntentMatchers.hasAction(Intent.ACTION_VIEW),
                    IntentMatchers.hasType("text/plain"),
                )
            )
        )
        Intents.release()

        // go back
        InstrumentationRegistry.getInstrumentation()
            .uiAutomation
            .performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                    && view == parent.getChildAt(position)
            }
        }
    }
}
