package suryakancana.nongkinongki.utils;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;
import suryakancana.nongkinongki.data.remote.retrofit.RetrofitServiceFactory;

public class RetrofitErrorAdapter {
    private Throwable  error;
    private JsonObject errorResponse;
    private String     message;

    // constant variable. feel free to move these to S.java
    private final String messageKey          = "message";
    private final String ioErrorMessage      = "Input Output Error. Please Try Again";
    private final String unknownErrorMessage = "Unknown Error. Please Try Again";

    public void convert () {
        if (error instanceof HttpException) {
            HttpException lHttpException = (HttpException) error;
            Response      lResponse      = lHttpException.response();
            Converter<ResponseBody, JsonObject> lConverter = RetrofitServiceFactory.sRetrofit
                    .responseBodyConverter(JsonObject.class, new Annotation[0]);
            try {
                errorResponse = lConverter.convert(lResponse.errorBody());
                message = String.valueOf(errorResponse.get(messageKey) == null ? unknownErrorMessage : errorResponse.get(messageKey));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (error instanceof IOException) {
            message = ioErrorMessage;
        } else {
            message = unknownErrorMessage;
        }
    }

    public RetrofitErrorAdapter (Throwable error) {
        this.error = error;
        this.convert();
    }

    public Throwable getError ()
    {
        return error;
    }

    public JsonObject getErrorResponse ()
    {
        return errorResponse;
    }

    public String getMessage ()
    {
        return message;
    }
}