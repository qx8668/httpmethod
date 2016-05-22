package com.app.code.utils;

import java.util.Map;
/**
 * @author liuquanxing
 * email 330967811@qq.com
 */
public class N {

	public static String getAddParamToUrl(String url, Map<String, String> param) {
		if (param != null && param.size() > 0) {
			StringBuilder builder = new StringBuilder(url);
			builder.append('?');
			for (Map.Entry<String, String> entry : param.entrySet()) {
				builder.append(entry.getKey()).append('=').append(entry.getValue()).append('&');
			}
			builder.deleteCharAt(builder.length() - 1);
			return builder.toString();
		} else {
			return url;
		}
	}
}

