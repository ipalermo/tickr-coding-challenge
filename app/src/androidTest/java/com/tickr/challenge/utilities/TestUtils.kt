package com.tickr.challenge.utilities

import android.content.Intent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import com.tickr.challenge.data.ArticleFields
import com.tickr.challenge.data.GuardianArticle
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf

/**
 * [GuardianArticle] objects used for tests.
 */
val testArticles = arrayListOf(
    GuardianArticle("1", "url1", ArticleFields("headline1", "trailText1", "thumnail1")),
    GuardianArticle("2", "url2", ArticleFields("headline2", "trailText2", "thumnail2")),
    GuardianArticle("3", "url3", ArticleFields("headline3", "trailText3", "thumnail3"))
)
val testArticle = testArticles[0]

/**
 * Simplify testing Intents with Chooser
 *
 * @param matcher the actual intent before wrapped by Chooser Intent
 */
fun chooser(matcher: Matcher<Intent>): Matcher<Intent> = allOf(
    hasAction(Intent.ACTION_CHOOSER),
    hasExtra(`is`(Intent.EXTRA_INTENT), matcher)
)
