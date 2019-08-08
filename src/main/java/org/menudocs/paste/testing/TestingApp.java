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

package org.menudocs.paste.testing;

import org.menudocs.paste.PasteClient;
import org.menudocs.paste.PasteClientBuilder;
import org.menudocs.paste.PasteHost;

public class TestingApp {
    public static void main(String[] args) {
        PasteClient client = new PasteClientBuilder()
                .setPasteHost(PasteHost.LOCAL)
                .setDefaultExpiry("10m")
                .build();

        String pasteID = client.createPaste("html", "<h1>testing</h1>").execute();

        System.out.println("Paste id: " + pasteID);

        client.getPaste(pasteID).async((paste) -> {
            System.out.println(paste.getPasteUrl());
            System.out.println(paste.getBody());
        });
    }
}
