package com.rong.board.exceptions;

/**
 * Author:rong
 * Description:
 * Data: Create in 上午 11:31 17.11.9
 * Package: com.rong.board.exceptions
 */
public class UserExistException extends Exception {
	public UserExistException(String errorMsg){
		super(errorMsg);
	}
}
