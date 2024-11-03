package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.pages.admin.CreateBuildTypePage;
import com.example.teamcity.ui.pages.admin.ProjectPage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseUiTest{
    private static final String REPO_URL = "https://github.com/Chesnova/teamcity-testing-framework";

    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {
        loginAs(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        CreateBuildTypePage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildType(testData.getBuildType().getName());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES)
                .read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType, "Build Type was not created");

        ProjectPage.open(createdProject.getId())
                .buildTypeName.shouldHave(Condition.exactText(testData.getBuildType().getName()));
    }

    @Test(description = "User should not be able to create build configuration without build type name", groups = {"Negative"})
    public void userFailsToCreateBuildTypeWithoutBuildTypeName() {
        loginAs(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        CreateBuildTypePage.open(testData.getProject().getId())
                .createForm(REPO_URL)
                .setupBuildType("");

        var errorNotification = CreateBuildTypePage.getErrorMessage();
        softy.assertTrue(errorNotification.isDisplayed(), "Expected an error notification for empty build type name");
        softy.assertEquals(errorNotification.getText(), "Build configuration name must not be empty", "Incorrect error message displayed");
    }
}
