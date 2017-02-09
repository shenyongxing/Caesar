package com.study.shenxing.caesar;

import android.content.Context;

import com.study.shenxing.caesar.utils.DrawUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void hello() {
//        assertEquals(4, 3 + 2);
    }

    @Test
    public void checkDensity() {
        assertEquals(DrawUtils.dip2px(1), 1);
    }

    private static final String FAKE_STRING = "HELLO WORLD";

    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {
        // Given a mocked Context injected into the object under test...
        when(mMockContext.getString(R.string.app_name))
                .thenReturn(FAKE_STRING);

        // ...when the string is returned from the object under test...
        String result = mMockContext.getString(R.string.app_name);

        // ...then the result should be the expected one.
//        assertThat(result, is("Caesar"));
    }
}