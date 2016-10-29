package com.study.shenxing.caesar.recycleview;

/**
 * @author shenxing
 * @description
 * @date 2016/10/29
 */

// 版本一  将几种view类型的数据都放在一个model里面，按需取用，不需要的view则无需理会
// 缺点：造成数据的冗余，如果集中viewtype的数据类型差异非常大，则维护非常困难。例如go省电的集中viewtype的数据差异就非常大。
public class DataModel {

    public static final int TYPE_ONE = 1;

    public static final int TYPE_TWO = 2;

    public static final int TYPE_THREE = 3;

    public int mType;

    public String mName;

    public String mAge;


}
