package cz.martinbarton.weather.android.client.request;

import com.google.gson.JsonParseException;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import cz.martinbarton.weather.android.client.parser.WeatherParser;
import cz.martinbarton.weather.android.client.response.Response;
import cz.martinbarton.weather.android.utility.Logcat;


public class WeatherRequest extends Request {
    private static final String REQUEST_METHOD = "GET";
    private static final String REQUEST_PATH = "free/v2/weather.ashx";

    private String mLocation;
    private String mFormat;
    private String mForecast;


    public WeatherRequest(String location, String format, String forecast) {
        mLocation = location;
        mFormat = format;
        mForecast = forecast;
    }


    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }


    @Override
    public String getAddress() {
        StringBuilder builder = new StringBuilder();
        List<NameValuePair> params = new LinkedList<NameValuePair>();

        // params
        params.add(new BasicNameValuePair("q", mLocation));
        params.add(new BasicNameValuePair("format", mFormat));
        params.add(new BasicNameValuePair("num_of_days", mForecast));
        params.add(new BasicNameValuePair("includelocation", "yes"));
        params.add(new BasicNameValuePair("tp", "24"));
        params.add(new BasicNameValuePair("key", API_KEY));
        String paramsString = URLEncodedUtils.format(params, CHARSET);

        // url
        builder.append(API_ENDPOINT);
        builder.append(REQUEST_PATH);
        if (paramsString != null && !paramsString.equals("")) {
            builder.append("?");
            builder.append(paramsString);
        }

        Logcat.e("Final adress", builder.toString());
        return builder.toString();
    }


    @Override
    public Response parseResponse(InputStream stream) throws IOException, JsonParseException {
        return WeatherParser.parse(stream);
    }


    @Override
    public byte[] getContent() {
        StringBuilder builder = new StringBuilder();
        builder.append("content");

        try {
            return builder.toString().getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
