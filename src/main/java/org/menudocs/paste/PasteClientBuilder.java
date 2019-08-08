package org.menudocs.paste;

import com.github.natanbc.reliqua.Reliqua;
import com.github.natanbc.reliqua.request.PendingRequest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class PasteClientBuilder {

    private String defaultExpiry = "2d";
    private String userAgent = "paste-client-java";
    private PasteHost pasteHost = PasteHost.MENUDOCS;

    public PasteClientBuilder setDefaultExpiry(String defaultExpiry) {
        this.defaultExpiry = defaultExpiry;
        return this;
    }

    public PasteClientBuilder setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public PasteClientBuilder setPasteHost(PasteHost pasteHost) {
        this.pasteHost = pasteHost;
        return this;
    }

    public PasteClient build() {
        return new PasteClientImpl(this.userAgent, this.defaultExpiry, this.pasteHost);
    }

    private static class PasteClientImpl extends Reliqua implements PasteClient {
        private final String userAgent;
        private final String defaultExpiry;
        private final String baseUrl;

        private PasteClientImpl(String userAgent, String defaultExpiry, PasteHost pasteHost) {
            super(new OkHttpClient.Builder().followRedirects(false).build());
            this.userAgent = userAgent;
            this.defaultExpiry = defaultExpiry;
            this.baseUrl = pasteHost.getUrl();
        }

        @Override
        public PendingRequest<String> createPaste(String lang, String body) {
            return createPaste(lang, body, this.defaultExpiry);
        }

        @Override
        public PendingRequest<String> createPaste(String lang, String body, String expiration) {
            Map<String, String> postBody = new HashMap<>();
            postBody.put("lang", lang);
            postBody.put("text", body);
            postBody.put("expire", expiration);

            return createRequest(
                    defaultRequest()
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .post(RequestBody.create(createFormBody(postBody).getBytes()))
                            .url(this.baseUrl + "/paste/new")
            ).build((r) -> {
                String loc = r.header("location");
                return loc.substring(loc.lastIndexOf('/') + 1);
            }, null);
        }

        @Override
        public PendingRequest<Paste> getPaste(String pasteId) {
            return createRequest(defaultRequest().get().url(this.baseUrl + "/paste/" + pasteId + ".json"))
                    .build((r) -> {
                        JSONObject json = new JSONObject(new JSONTokener(r.body().byteStream()));
                        String id = json.getString("id");
                        JSONObject language = json.getJSONObject("language");

                        return new PasteImpl(
                                id,
                                json.getString("body"),
                                this.baseUrl + "/paste/" + id,
                                new LanguageImpl(
                                        language.getString("name"),
                                        language.getString("id")
                                )
                        );
                    }, null);
        }

        private Request.Builder defaultRequest() {
            return new Request.Builder()
                    .header("User-Agent", this.userAgent);
        }

        private String createFormBody(Map<String, String> fields) {
            StringBuilder builder = new StringBuilder();

            for (final Map.Entry<String, String> entry : fields.entrySet()) {
                builder.append('&')
                        .append(URLEncoder.encode(entry.getKey(), Charset.defaultCharset()))
                        .append('=')
                        .append(URLEncoder.encode(entry.getValue(), Charset.defaultCharset()));
            }

            return builder.toString().substring(1);
        }
    }
}
