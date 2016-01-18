package cz.martinbarton.weather.android.entity;

public class ErrorEntity
{
	private String mType;
	private String mMessage;


	public ErrorEntity()
	{
		mType = null;
		mMessage = null;
	}


	public ErrorEntity(String type, String message)
	{
		mType = type;
		mMessage = message;
	}


	public String getType()
	{
		return mType;
	}


	public String getMessage()
	{
		return mMessage;
	}


	public void setType(String type)
	{
		this.mType = type;
	}


	public void setMessage(String message)
	{
		this.mMessage = message;
	}


}
