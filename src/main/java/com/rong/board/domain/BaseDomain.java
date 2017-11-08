package com.rong.board.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Author:rong
 * Description:所有po类父类
 * Data: Create in 下午 4:34 17.11.8
 * Package: com.rong.board.domain
 */
public class BaseDomain  implements Serializable{
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
