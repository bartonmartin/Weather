package cz.martinbarton.weather.android.client.response;


import cz.martinbarton.weather.android.entity.ErrorEntity;


public class Response
{
	protected boolean mError = false;
	protected String mErrorType = null;
	protected String mErrorMessage = null;
	protected ErrorEntity mErrorEntity = null;


	public ErrorEntity getErrorEntity()
	{
		return mErrorEntity;
	}


	public void setErrorEntity(ErrorEntity errorEntity)
	{
		mErrorEntity = errorEntity;
	}


	public boolean isError()
	{
		return mError;
	}


	public void setError(boolean error)
	{
		mError = error;
	}


	public String getErrorType()
	{
		return mErrorType;
	}


	public void setErrorType(String errorType)
	{
		mErrorType = errorType;
	}


	public String getErrorMessage()
	{
		return mErrorMessage;
	}


	public void setErrorMessage(String errorMessage)
	{
		mErrorMessage = errorMessage;
	}
}
