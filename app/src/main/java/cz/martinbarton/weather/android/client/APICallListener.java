package cz.martinbarton.weather.android.client;


import cz.martinbarton.weather.android.client.response.Response;

public interface APICallListener
{
	public void onAPICallRespond(APICallTask task, ResponseStatus status, Response response);
	public void onAPICallFail(APICallTask task, ResponseStatus status, Exception exception);
}
