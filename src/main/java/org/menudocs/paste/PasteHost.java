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

import java.net.URI;

public class PasteHost {
    /**
     * The default MenuDocs paste
     */
    public static final PasteHost MENUDOCS = new PasteHost("https://paste.menudocs.org");
    /**
     * The paste service when running locally for testing
     */
    public static final PasteHost LOCAL = new PasteHost("http://localhost:7000");

    private final String url;
    private PasteHost(String url) {
        this.url = url;
    }

    /**
     * Allows you to use this lib with your own GhostBin paste
     *
     * @param uri the base url of your server, eg https://paste.menudocs.org
     */
    public PasteHost(URI uri) {
        String port = "";

        if (uri.getPort() > -1) {
            port = ":" + uri.getPort();
        }

        this.url = String.format("%s://%s%s%s", uri.getScheme(), uri.getHost(), port, uri.getRawPath());
    }

    public String getUrl() {
        return url;
    }
}
