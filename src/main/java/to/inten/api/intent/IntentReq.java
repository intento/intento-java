package to.inten.api.intent;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntentReq {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private String path;
    private JsonValue req;
    private boolean post;

    private IntentReq(String path, JsonValue req, boolean post) {

        this.path = path;
        this.req = req;
        this.post = post;

    }

    public static IntentReq post(String path, JsonValue req) {

        return new IntentReq(path, req, true);

    }

    public static IntentReq post(String path, String req) {

        return new IntentReq(path, Json.parse(req), true);

    }

    public static IntentReq get(String path) {

        return new IntentReq(path, null, false);

    }

    public String toStr() {

        return this.req.toString();

    }

    public boolean getPost() {

        return this.post;

    }

    public String getPath() {

        return this.path;

    }
}
