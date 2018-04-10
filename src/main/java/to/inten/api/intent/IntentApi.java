package to.inten.api.intent;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;


public class IntentApi {

    private String API_DOMAIN = "https://api.inten.to";

    private static final int DEFAULT_TIMEOUT = 60000;
    private static Logger logger = LoggerFactory.getLogger(IntentApi.class);
    private static Charset UTF_8 = Charset.forName("UTF-8");
    private final HttpParams httpParams = new BasicHttpParams();
    private String apiKey;
    private DefaultHttpClient client;


    private IntentApi(String apiKey) {
        this.apiKey = apiKey;

        HttpConnectionParams.setConnectionTimeout(this.httpParams, this.DEFAULT_TIMEOUT);
        this.client = new DefaultHttpClient(this.httpParams);

    }

    private IntentApi(String apiKey, String apiDomain) {
        this.apiKey = apiKey;
        this.API_DOMAIN = apiDomain;

        HttpConnectionParams.setConnectionTimeout(this.httpParams, this.DEFAULT_TIMEOUT);
        this.client = new DefaultHttpClient(this.httpParams);

    }

    public static IntentApi create(String apiKey) {
        return new IntentApi(apiKey);
    }
    public static IntentApi create(String apiKey, String apiDomain) {
        return new IntentApi(apiKey, apiDomain);
    }

    public Result send(IntentReq intentReq) {
        try {
            HttpResponse resp = requestSend(getSendAPIURI(intentReq), intentReq);
            String body = IOUtils.toString(resp.getEntity().getContent(), "utf-8");
            int code = resp.getStatusLine().getStatusCode();
            if (code > 299) {
                logger.error("Request failed, request params: {}", intentReq.toStr());
            }
            return new Result(body, code);
        } catch (IOException ioe) {
            return Result.createExceptionResult(intentReq, ioe);
        }
    }

    protected String getSendAPIURI(IntentReq intentReq) {
        return String.format("%s%s", this.API_DOMAIN, intentReq.getPath());
    }

    private HttpResponse requestSend(String uri, IntentReq intentReq) throws IOException {
        if (intentReq.getPost()) {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeader("apikey", apiKey);
            httpPost.setEntity(new StringEntity(intentReq.toStr(), UTF_8));
            return this.client.execute(httpPost);
        } else {
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("apikey", apiKey);
            return this.client.execute(httpGet);
        }
    }

}
