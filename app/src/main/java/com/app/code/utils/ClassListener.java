package com.app.code.utils;

/**
 * @author liuquanxing
 * email 330967811@qq.com
 */
public interface ClassListener extends ExceptionListener {
	<T> void onResult(T t);
}
