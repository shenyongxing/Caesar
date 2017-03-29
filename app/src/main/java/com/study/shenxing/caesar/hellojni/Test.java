package com.study.shenxing.caesar.hellojni;


/**
 *
 * 出现了 java.lang.UnsatisfiedLinkError: no hellojni in java.library.path 错误
 *
 * 测试java.library.path的路径
 *
 */
public class Test {

		public static void main(String[] args) {
			String path = System.getProperty("java.library.path");
			System.out.println(path);
		}
}
