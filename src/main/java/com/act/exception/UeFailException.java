package com.act.exception;

import com.act.beans.enums.ErrorCode;
import com.act.util.Response;



public class UeFailException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Response response;

    public UeFailException(ErrorCode error) {
		super(error.memo);
        this.response=Response.FAIL(error);
	}

    public UeFailException(Response response){
        this.response=response;
    }
    public UeFailException(String msg){
    	super(msg);
        this.response=Response.FAIL(msg);
    }

    public String toJson(){
        return response.toJson();
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
