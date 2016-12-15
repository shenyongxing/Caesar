package com.study.shenxing.caesar.mvp;

/**
 * @author shenxing
 * @description some contract between view and presenter
 * @date 2016/11/14
 */

public class TaskContract {

    interface View extends BaseView<Presenter> {

    }


    interface Presenter extends BasePresenter {

    }

}
