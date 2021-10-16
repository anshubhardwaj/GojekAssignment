package com.example.gojekassignment

import android.R
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.rule.ActivityTestRule
import com.example.gojekassignment.LatestNews.RepositoryListActivity
import org.hamcrest.Description
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.regex.Matcher
import java.util.regex.Pattern.matches


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class RepositoryUnitTest {
    @Rule
    var activityRule: ActivityTestRule<RepositoryListActivity> = ActivityTestRule<Any?>(
        RepositoryListActivity::class.java,
        true,
        true
    )

    @Test
    fun testSampleRecyclerVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.getActivity().getWindow().getDecorView())
                )
            )
            .check(matches(isDisplayed()))
    }

    @Test
    fun testCaseForRecyclerClick() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.getActivity().getWindow().getDecorView())
                )
            )
            .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()))
    }

    @Test
    fun testCaseForRecyclerScroll() {

        // Get total item of RecyclerView
        val recyclerView: RecyclerView =
            activityRule.getActivity().findViewById(R.id.recyclerView)
        val itemCount = recyclerView.adapter!!.itemCount

        // Scroll to end of page with position
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.getActivity().getWindow().getDecorView())
                )
            )
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
    }

    @Test
    fun testCaseForRecyclerItemView() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.getActivity().getWindow().getDecorView())
                )
            )
            .check(
                matches(
                    withViewAtPosition(
                        1, Matchers.allOf(
                            ViewMatchers.withId(R.id.cv_container), isDisplayed()
                        )
                    )
                )
            )
    }

    fun withViewAtPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description?) {
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}