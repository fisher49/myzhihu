package org.qxh.myapp.myzhihu;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.qxh.myapp.myzhihu.model.usecase.ThemesUsecase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testThemesUsecase() throws Exception {
        ThemesUsecase usecase = new ThemesUsecase(getContext());
        usecase.getLocalThemes();
    }
}