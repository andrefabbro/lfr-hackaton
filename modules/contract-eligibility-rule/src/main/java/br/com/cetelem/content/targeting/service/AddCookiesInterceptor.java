package br.com.cetelem.content.targeting.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.Cookie;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author André Fabbro
 */
public class AddCookiesInterceptor implements Interceptor {

    protected List<Cookie> cookies = new ArrayList<Cookie>();

    public AddCookiesInterceptor(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    @Override
    public Response intercept(Chain chain)
        throws IOException {

        Request.Builder builder = chain.request().newBuilder();

        StringBuilder cookiesSb = new StringBuilder();

        IntStream.range(0, cookies.size()).mapToObj(
            i -> (i > 0 ? "; " : "") + cookies.get(i).getName() + "=" +
                cookies.get(i).getValue()).forEach(cookiesSb::append);

        builder.addHeader("Cookie", cookiesSb.toString());

        return chain.proceed(builder.build());
    }

}
