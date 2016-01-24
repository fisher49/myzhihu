package org.qxh.myapp.myzhihu.presenter;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by QXH on 2016/1/22.
 */
public class StartPresenterTest{


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testDownloadRemoteImage() throws Exception {
        StartPresenter startPresenter = new StartPresenter(null);
        startPresenter.downloadRemoteImage();
    }
}