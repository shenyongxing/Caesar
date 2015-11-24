package com.study.shenxing.caesar.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.study.shenxing.caesar.R;

/**
 * Fragment学习
 * 对于api的minimum如果是11或者更高,那么直接使用adnroid.app.Fragment
 * 对于低于api 11需要用suppoert包
 * FragmentManager的获取需要用getSupportFragmentManager() ;
 * 同时包含Fragment的Activity需要使用FragmentActivity.否则获取不到FragmentManager对象.
 */
public class FragmentDemoActivity extends AppCompatActivity implements HeadLinesFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_demo_layout);

        if (savedInstanceState != null) {
            return ;
        }

        if (findViewById(R.id.main) == null) {
            // 采用layout-large布局
            // 对于双面板的布局, fragment通过xml方式包含了,故不需要其他操作
        } else {
            // 采用layout布局,即单面板
            // 动态添加HeadlinesFragment
            HeadLinesFragment headLinesFragment = HeadLinesFragment.newInstance() ;
            FragmentManager fragmentManager = getFragmentManager() ;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.main, headLinesFragment) ;
            // 不要忘记提交,否则不会成功添加fragment
            transaction.commit();
        }

    }

    /**
     * 对于双面板的布局,点击事件直接更新界面
     * 对于单面板的布局,用新的fragment替换掉当前的fragment
     * @param id
     */
    @Override
    public void onFragmentInteraction(int id) {
        Toast.makeText(this, "you click" + id , Toast.LENGTH_SHORT).show();
        com.study.shenxing.caesar.fragment.ArticleFragment articleFragment = (com.study.shenxing.caesar.fragment.ArticleFragment) getFragmentManager().findFragmentById(R.id.article);
        if (articleFragment != null) {
            // 当前activity的布局中包含有articleFragment
            articleFragment.updateView(id);
        } else {
            // 当前是单面板布局, 不存在artcileFragment
            com.study.shenxing.caesar.fragment.ArticleFragment newInstance = com.study.shenxing.caesar.fragment.ArticleFragment.newInstance() ;
            Bundle args = new Bundle() ;
            args.putInt(ArticleFragment.POSITION, id) ;
            newInstance.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager() ;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main, newInstance);
            transaction.addToBackStack(null) ;
            transaction.commit() ;
        }
    }

    /**
     * 当采用单面板时,跳转到了第二个Fragment时再返回,由于都是包含在一个Activity中,故此时会退出应用程序
     * 所以,此处重写onBackPressed方法,先跳转回第一个Fragment.
     * 对于双面板的返回按正常逻辑不变.
     */
    @Override
    public void onBackPressed() {
        // 由于在跳转到了articleFragment界面时在返回会退出程序, 所以重写onBackPressed
        // 在详情界面时返回时先退回到HeadLines界面.
        // 但是android官网上提供的demos却可以不用重写onBackPressed方法就可以达到这种效果

        if (findViewById(R.id.main) != null) {
            FragmentManager fragmentManager = getFragmentManager() ;
            Fragment fragment = fragmentManager.findFragmentById(R.id.main);
            if (fragment instanceof HeadLinesFragment) {
                super.onBackPressed();
            } else {
                // 动态添加HeadlinesFragment
                HeadLinesFragment headLinesFragment = HeadLinesFragment.newInstance() ;
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main, headLinesFragment) ;
                // 不要忘记提交,否则不会成功添加fragment
                transaction.commit();
            }
        } else {
            super.onBackPressed();
        }
    }
}
