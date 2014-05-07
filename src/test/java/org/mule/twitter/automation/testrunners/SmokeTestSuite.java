/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.twitter.automation.testrunners;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.twitter.automation.SmokeTests;
import org.mule.twitter.automation.testcases.DestroyStatusTestCases;
import org.mule.twitter.automation.testcases.RetweetStatusTestCases;
import org.mule.twitter.automation.testcases.ShowUserTestCases;
import org.mule.twitter.automation.testcases.UpdateStatusTestCases;

@RunWith(Categories.class)
@IncludeCategory(SmokeTests.class)
@SuiteClasses({DestroyStatusTestCases.class,
        RetweetStatusTestCases.class,
        ShowUserTestCases.class,
        UpdateStatusTestCases.class})
public class SmokeTestSuite {
}
