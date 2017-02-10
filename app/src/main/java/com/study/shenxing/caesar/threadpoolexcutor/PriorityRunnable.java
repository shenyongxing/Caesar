package com.study.shenxing.caesar.threadpoolexcutor;

/**
 * @author shenxing
 * @description
 * @date 10/02/2017
 */

public abstract class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {

    private int mPriority;

    public PriorityRunnable(int priority) {
        if (priority < 0) {
            throw new IllegalArgumentException("Priority must be greater than 0");
        }
        mPriority = priority;
    }


    @Override
    public void run() {
        doSth();
    }

    public abstract void doSth();

    public int getPriority() {
        return mPriority;
    }

    @Override
    public int compareTo(PriorityRunnable another) {
        int my = this.getPriority();
        int other = another.getPriority();
        return other - my;
    }
}
