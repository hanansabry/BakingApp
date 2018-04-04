package com.hanan.and.udacity.bakingapp;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hanan.and.udacity.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Nono on 4/3/2018.
 */

@RunWith(AndroidJUnit4.class)
public class StepDetailsActivityScreenTest {

    public static final String STEP_1 = "Recipe Introduction";
    public static final String STEP_2 = "Starting prep";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTest
            = new ActivityTestRule<>(MainActivity.class);

    //test the next button
    @Test
    public void clickNextButton_goToNextStep() throws InterruptedException {
        goToStepDetailsActivity(0);

        //verify that next button text is displayed
        onView(withId(R.id.next)).check(matches(withText(R.string.next)));

        //click on the next button
        onView(withId(R.id.next)).perform(click());

        //verify that the next button moves us to the next step
        //check the step_name
        onView(withId(R.id.step_name)).check(matches(withText(STEP_2)));

        //check the step_number
        onView(withId(R.id.steps_progress)).check(matches(withText("1 / 6")));
    }

    //test the previous button
    @Test
    public void clickPreviousButton_goToPreviousStep() throws InterruptedException {
        goToStepDetailsActivity(1);

        //verify that previous button text is displayed
        onView(withId(R.id.previous)).check(matches(withText(R.string.previous)));

        //click on the previous button
        onView(withId(R.id.previous)).perform(click());

        //verify that the previous button moves us to the previous step
        //check the step_name
        onView(withId(R.id.step_name)).check(matches(withText(STEP_1)));

        //check the step_number
        onView(withId(R.id.steps_progress)).check(matches(withText("0 / 6")));

    }

    public void goToStepDetailsActivity(int stepsPosition) throws InterruptedException {
        //click on recipe item from the recycler view
        Thread.sleep(2000);
        onView(withId(R.id.recipes_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //click on the first step to go to the step details activity
        onView(withId(R.id.steps_rv)).perform(ViewActions.scrollTo())
                .perform(RecyclerViewActions.actionOnItemAtPosition(stepsPosition, click()));

        //verify that the correct step is opened
        if (stepsPosition == 0) {
            onView(withId(R.id.step_name)).check(matches(withText(STEP_1)));
        } else if (stepsPosition == 1) {
            onView(withId(R.id.step_name)).check(matches(withText(STEP_2)));
        }
    }
}
