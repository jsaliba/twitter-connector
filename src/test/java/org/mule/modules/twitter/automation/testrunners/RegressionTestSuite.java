/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.twitter.automation.testrunners;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.modules.twitter.automation.RegressionTests;
import org.mule.modules.twitter.automation.testcases.*;

@RunWith(Categories.class)
@IncludeCategory(RegressionTests.class)
@SuiteClasses({
        CreatePlaceTestCases.class, DestroyStatusTestCases.class,
        GetAvailableTrendsTestCases.class,
        GetGeoDetailsTestCases.class, GetHomeTimelineTestCases.class,
        GetLocationTrendsTestCases.class, GetMentionsTestCases.class,
        GetRetweetsOfMeTestCases.class, GetRetweetsTestCases.class,
        GetUserTimelineByScreenNameTestCases.class,
        GetUserTimelineByUserIdTestCases.class,
        RetweetStatusTestCases.class,
        ReverseGeoCodeTestCases.class, SearchPlacesTestCases.class,
        SearchTestCases.class, SendDirectMessageByScreenNameTestCases.class,
        SendDirectMessageByUserIdTestCases.class, ShowStatusTestCases.class,
        ShowUserTestCases.class, UpdateStatusTestCases.class
})
public class RegressionTestSuite {
}
