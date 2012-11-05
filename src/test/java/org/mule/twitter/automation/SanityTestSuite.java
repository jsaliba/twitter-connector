package org.mule.twitter.automation;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.twitter.automation.testcases.*;

@RunWith(Categories.class)
@IncludeCategory(SanityTests.class)
@SuiteClasses({ 
		CreatePlaceTestCases.class, DestroyStatusTestCases.class,
		GetAvailableTrendsTestCases.class, GetDailyTrendsTestCases.class,
		GetGeoDetailsTestCases.class, GetHomeTimelineTestCases.class,
		GetLocationTrendsTestCases.class, GetMentionsTestCases.class,
		GetPublicTimelineTestCases.class, GetRetweetedByIdsTestCases.class,
		GetRetweetedByMeTestCases.class, GetRetweetedByTestCases.class,
		GetRetweetedByUserByScreenNameTestCases.class,
		GetRetweetedByUserByUserIdTestCases.class,
		GetRetweetedToMeTestCases.class,
		GetRetweetedToUserByScreenNameTestCases.class,
		GetRetweetedToUserByUserIdTestCases.class,
		GetRetweetsOfMeTestCases.class, GetRetweetsTestCases.class,
		GetUserTimelineByScreenNameTestCases.class,
		GetUserTimelineByUserIdTestCases.class, GetUserTimelineTestCases.class,
		GetWeeklyTrendsTestCases.class, RetweetStatusTestCases.class,
		ReverseGeoCodeTestCases.class, SearchPlacesTestCases.class,
		SearchTestCases.class, SendDirectMessageByScreenNameTestCases.class,
		SendDirectMessageByUserIdTestCases.class, ShowStatusTestCases.class,
		ShowUserTestCases.class, UpdateStatusTestCases.class 
		})
public class SanityTestSuite {
}
