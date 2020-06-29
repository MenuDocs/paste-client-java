/*
 *    Copyright 2019 Duncan Sterken
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.menudocs.paste;

import com.github.natanbc.reliqua.Reliqua;
import com.github.natanbc.reliqua.request.PendingRequest;
import com.github.natanbc.reliqua.util.StatusCodeValidator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PasteClientBuilder {

    private String defaultExpiry = "2d";
    private String userAgent = "paste-client-java";
    private PasteHost pasteHost = PasteHost.MENUDOCS;

    /**
     * Sets the default paste expiry, this is useful so that you don't need to specify the expiry every time
     *
     * @param defaultExpiry
     *         the default expiry time
     *         Examples: 30µs, 10s, 1h, 15d
     *
     * @return The current builder for chaining
     */
    public PasteClientBuilder setDefaultExpiry(String defaultExpiry) {
        this.defaultExpiry = defaultExpiry;
        return this;
    }

    /**
     * Sets the user agent
     *
     * @param userAgent
     *         the user agent to use when making request
     *
     * @return The current builder for chaining
     */
    public PasteClientBuilder setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    /**
     * Sets the host that we need to use for making the requests
     *
     * @param pasteHost
     *         the new paste host to use, default is {@link PasteHost#MENUDOCS MenuDocs}
     *
     * @return The current builder for chaining
     */
    public PasteClientBuilder setPasteHost(PasteHost pasteHost) {
        this.pasteHost = pasteHost;
        return this;
    }

    /**
     * Builds the client
     *
     * @return The paste client that you can use to create pastes
     */
    public PasteClient build() {
        return new PasteClientImpl(this.userAgent, this.defaultExpiry, this.pasteHost);
    }

    private static class PasteClientImpl extends Reliqua implements PasteClient {
        private final String userAgent;
        private final String defaultExpiry;
        private final String baseUrl;

        private PasteClientImpl(String userAgent, String defaultExpiry, PasteHost pasteHost) {
            super(new OkHttpClient.Builder().followRedirects(false).build(), null, true);
            this.userAgent = userAgent;
            this.defaultExpiry = defaultExpiry;
            this.baseUrl = pasteHost.getUrl();
        }

        @Override
        public PendingRequest<String> createPaste(String lang, String body) {
            return this.createPaste(lang, body, this.defaultExpiry);
        }

        @Override
        public PendingRequest<String> createPaste(String lang, String body, String expiration) {
            Map<String, String> postBody = new HashMap<>();
            postBody.put("lang", lang);
            postBody.put("text", body);
            postBody.put("expire", expiration);

            return this.createRequest(
                    this.defaultRequest()
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .post(RequestBody.create(null, createFormBody(postBody).getBytes()))
                            .url(this.baseUrl + "/paste/new")
            )
                    .setStatusCodeValidator(StatusCodeValidator.accept(303))
                    .build((r) -> {
                        String loc = r.header("location");
                        return loc.substring(loc.lastIndexOf('/') + 1);
                    }, null);
        }

        @Override
        public PendingRequest<Paste> getPaste(String pasteId) {
            return this.createRequest(this.defaultRequest().get().url(this.baseUrl + "/paste/" + pasteId + ".json"))
                    .setStatusCodeValidator(StatusCodeValidator.ACCEPT_200)
                    .build((r) -> {
                        JSONObject json = new JSONObject(new JSONTokener(r.body().byteStream()));
                        String id = json.getString("id");
                        JSONObject language = json.getJSONObject("language");

                        return new PasteImpl(
                                id,
                                json.getString("body"),
                                this.getPasteUrl(id),
                                new LanguageImpl(
                                        language.getString("name"),
                                        language.getString("id")
                                )
                        );
                    }, null);
        }

        @Override
        public String getPasteUrl(String pasteId) {
            return this.baseUrl + "/paste/" + pasteId;
        }

        private Request.Builder defaultRequest() {
            return new Request.Builder()
                    .header("User-Agent", this.userAgent);
        }

        private String createFormBody(Map<String, String> fields) {

            return fields.entrySet()
                    .stream()
                    .map(
                            (e) -> this.e(e.getKey()) + '=' + this.e(e.getValue())
                    )
                    .collect(Collectors.joining("&"));
        }

        private String e(String s) {
            try {
                return URLEncoder.encode(s, "UTF-8");
            } catch (UnsupportedEncodingException ignored) {
                return "null"; // should never happen
            }
        }
    }
}
