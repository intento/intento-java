# Intento java

An adapter to query Intento Collective Intelligence API.

To get more information check out [the site](https://inten.to/).

[API User Manual](https://github.com/intento/intento-api)

In case you don't have a key to use Intento API, please feel free to mail hello@inten.to

## Installation

gradle:

```
dependencies {
    compile 'to.inten.api:java-sdk:1.0'
}
```

## Example usage

### Using `data` argument from a curl request directly
For a `curl` instruction from [the docs](https://github.com/intento/intento-api)

```bash
curl -XPOST -H 'apikey: YOUR_API_KEY' 'https://api.inten.to/ai/text/translate' -d '{
 "context": {
  "text": "A sample text",
  "to": "es"
 },
 "service": {
  "provider": "ai.text.translate.microsoft.translator_text_api.2-0"
 }
}'
```

try following:

```java

import to.inten.api.intent.IntentApi;
import to.inten.api.intent.IntentReq;
import to.inten.api.intent.Result;

IntentApi api = IntentApi.create("INTENTO API KEY");

IntentReq req = IntentReq.post("/ai/text/translate", "
{
        \"context\": {
            \"text\": \"A sample text\",
            \"to\": \"es\"
        },
        \"service\": {
            \"provider\": \"ai.text.translate.microsoft.translator_text_api.2-0\"
        }
}
"));
Result res = api.send(req);

```


## Details


### to.inten.api.intent.IntentApi

- IntentApi.create(String apiKey) => IntentApi –– init API with key
- IntentApi.create(String apiKey, String apiDomain) => IntentApi –– init API with key and custom domain
- IntentApi.send(IntentReq res) => Result –– send request to API

### to.inten.api.intent.IntentReq

- IntentReq.post(String path, JsonValue body) => IntentReq –– make POST request from com.eclipsesource.json.JsonValue body
- IntentReq.post(String path, String body) => IntentReq –– make POST request from String body
- IntentReq.get(String path) => IntentReq –– make GET request

### to.inten.api.intent.Result

- isSuccess() –– `false` if an error is received from the server, `true` if the request was processed successfully
- getStatusCode() –– HTTP status code of response
- getResponse() –– get response as com.eclipsesource.json.JsonValue object
- toString() –– get response as String
