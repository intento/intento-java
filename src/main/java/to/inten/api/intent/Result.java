package to.inten.api.intent;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Result {
    private static final int IO_ERROR_CODE = 599;
    private static Logger logger = LoggerFactory.getLogger(Result.class);
    private boolean success = false;
    private int statusCode;
    private JsonValue response;
    private String responseText;

    protected Result(String responseText, int statusCode) {
        this.success = (statusCode > 299 ? false : true);
        this.responseText = responseText;
        this.statusCode = statusCode;
        parserResponseText(responseText);
    }

    protected Result(boolean success, int statusCode, JsonValue response) {
        this.success = success;
        this.statusCode = statusCode;
        this.response = response;
    }

    protected static Result createExceptionResult(IntentReq req, Throwable throwable) {
        logger.error("Request failed, request params: {}", req.toStr(), throwable);
        return new Result(false, getIOErrorCode(throwable), null);
    }

    private static int getIOErrorCode(Throwable throwable) {
        return IO_ERROR_CODE;
    }

    protected void parserResponseText(String responseJson) {
        JsonValue jsonObject = Json.parse(responseJson);
        this.response = jsonObject;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getResponseText() {
        return this.responseText;
    }

    public JsonValue getResponse() {
        return this.response;
    }

    @Override
    public String toString() {
        return (this.response != null ? this.response : "").toString();
    }
}
