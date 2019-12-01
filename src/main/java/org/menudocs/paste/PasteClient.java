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

import com.github.natanbc.reliqua.request.PendingRequest;

public interface PasteClient {
    /**
     * Creates a paste
     *
     * @param lang
     *         The language to use, languages can be obtained from <a href="https://paste.menudocs.org/languages.json">https://paste.menudocs.org/languages.json</a>
     * @param body
     *         The text content of the paste
     *
     * @return The id of the created paste, the paste can be retrieved with {@link #getPaste(String) #getPaste(String)}
     */
    PendingRequest<String> createPaste(String lang, String body);

    /**
     * Creates a paste
     *
     * @param lang
     *         The language to use, languages can be obtained from <a href="https://paste.menudocs.org/languages.json">https://paste.menudocs.org/languages.json</a>
     * @param body
     *         The text content of the paste
     * @param expiration
     *         The expiration of the paste, useful if you don't want to use the default set expiration on the builder
     *
     * @return The id of the created paste, the paste can be retrieved with {@link #getPaste(String) #getPaste(String)}
     */
    PendingRequest<String> createPaste(String lang, String body, String expiration);

    /**
     * Retrieves a paste by it's id
     *
     * @param pasteId
     *         The id of the paste to look up the info for
     *
     * @return The paste
     */
    PendingRequest<Paste> getPaste(String pasteId);

    /**
     * Generates the url to a paste that you can put in your browser to view it
     *
     * @param pasteId
     *         The id of the paste to generate the url for
     *
     * @return The generated url for the paste
     */
    String getPasteUrl(String pasteId);
}
