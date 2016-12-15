package com.study.shenxing.caesar;

import android.os.Parcel;
import com.study.shenxing.caesar.test.ForTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author shenxing
 * @description AndroidInstrumentTest
 * @date 2016/11/3
 */
@RunWith(AndroidJUnit4.class)
public class ParcelObjectTest {

    private ForTest mTest;

    @Before
    public void createObject() {
        Log.i("sh", "createObject: ");
        mTest = new ForTest();
    }

    @Test
    public void forTest_ParcelableWriteRead() {
        Log.i("sh", "forTest_ParcelableWriteRead: ");
        // Write the data.
        Parcel parcel = Parcel.obtain();
        mTest.setForTest("hello world");
        mTest.setDescription("This is description");
        mTest.writeToParcel(parcel, mTest.describeContents());

        // After you're done with writing, you need to reset the parcel for reading.
        parcel.setDataPosition(0);

        // Read the data.
        ForTest createdFromParcel = ForTest.CREATOR.createFromParcel(parcel);
        String testStr = createdFromParcel.getForTest();
        String description = createdFromParcel.getDescription();

        // Verify that the received data is correct.
        assertThat(testStr, is("hello world"));
        assertThat(description, is("This is description"));
    }
}

