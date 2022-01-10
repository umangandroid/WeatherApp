package com.umang.weatherapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.umang.weatherapp.ui.MainActivity
import com.umang.weatherapp.utils.EspressoIdlingResource
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before

/**
 * Common Espresso methods to use in UI/Integration tests
 */
open class BaseTest {


    private fun launchActivity(): ActivityScenario<MainActivity>? {
        return ActivityScenario.launch(MainActivity::class.java)
    }

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        launchActivity()
    }

    @After
    fun unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)

    }


    /**
     * To Verify text in [RecyclerView] for particular item
     */
    fun verifyViewInList(listId: Int, itemPosition: Int, text: String?) {
        Espresso.onView(ViewMatchers.withId(listId)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                itemPosition,
                ViewActions.scrollTo()
            )
        )
            .check(
                ViewAssertions.matches(
                    ViewMatchers.hasDescendant(
                        ViewMatchers.withText(
                            text
                        )
                    )
                )
            )
    }

    /**
     * To check visibility of view in [RecyclerView] for particular item
     */
    fun verifyViewShowInList(listId: Int, itemPosition: Int, viewId: Int) {
        Espresso.onView(Matchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(listId)))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    itemPosition,
                    ViewActions.scrollTo()
                )
            )
            .check(
                ViewAssertions.matches(
                    ViewMatchers.hasDescendant(
                        Matchers.allOf(
                            ViewMatchers.withId(
                                viewId
                            ), ViewMatchers.isDisplayed()
                        )
                    )
                )
            )
    }

    /**
     * To click [RecyclerView] item
     */
    fun clickOnListItemView(position: Int, view: Int, listId: Int) {
        Espresso.onView(ViewMatchers.withId(listId))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    position,
                    RecyclerViewAction.clickChildViewWithId(view)
                )
            )
    }


}