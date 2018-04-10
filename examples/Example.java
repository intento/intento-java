import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.eclipsesource.json.Json;
import org.slf4j.LoggerFactory;

import to.inten.api.intent.IntentApi;
import to.inten.api.intent.IntentReq;
import to.inten.api.intent.Result;
import to.inten.api.Intento;

class Example {
    public static void main(String[] args) {

        // disable debug logging
        Logger logger = (Logger) LoggerFactory.getLogger("org.apache.http");
        logger.setLevel(Level.INFO);
        logger.setAdditive(false);
        //

        Intento intento = Intento.createIntentApi("INTENTO API KEY");
        IntentApi api = IntentApi.create(intento);

        IntentReq req;
        Result res;

        System.out.println("JsonValue, post /ai/text/translate");
        System.out.println("---");
        req = IntentReq.post("/ai/text/translate", Json.parse("{\"context\":{\"text\":[\"A sample text\",\"Hello world\"," +
                "\"Hallo Welt\"],\"to\":\"es\"},\"service\":{\"provider\":\"ai.text.translate.google.translate_api.2-0\"}}"));
        res = api.send(req);
        System.out.println(String.format("%s | %s | %s", res.getStatusCode(), res.isSuccess(), res));
        System.out.println("---\n---\n---");

        System.out.println("JsonValue, post /ai/text/translate, invalid");
        System.out.println("---");
        req = IntentReq.post("/ai/text/translate", Json.parse("{\"contextttttt\":{\"text\":[\"A sample text\",\"Hello world\"," +
                "\"Hallo Welt\"],\"to\":\"es\"},\"service\":{\"provider\":\"ai.text.translate.google.translate_api.2-0\"}}"));
        res = api.send(req);
        System.out.println(String.format("%s | %s | %s", res.getStatusCode(), res.isSuccess(), res));
        System.out.println("---\n---\n---");

        System.out.println("String, post /ai/text/translate");
        System.out.println("---");
        req = IntentReq.post("/ai/text/translate", "{\"context\":{\"text\":[\"A sample text\",\"Hello world\"," +
                "\"Hallo Welt\"],\"to\":\"es\"},\"service\":{\"provider\":\"ai.text.translate.google.translate_api.2-0\"}}");
        res = api.send(req);
        System.out.println(String.format("%s | %s | %s", res.getStatusCode(), res.isSuccess(), res));
        System.out.println("---\n---\n---");

        System.out.println("JsonValue, get /ai/text/translate");
        System.out.println("---");
        req = IntentReq.get("/ai/text/translate");
        res = api.send(req);
        System.out.println(String.format("%s | %s | %s", res.getStatusCode(), res.isSuccess(), res));
        System.out.println("---\n---\n---");


    }
}