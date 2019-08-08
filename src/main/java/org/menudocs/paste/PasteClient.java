package org.menudocs.paste;

import com.github.natanbc.reliqua.request.PendingRequest;

public interface PasteClient {
    PendingRequest<String> createPaste(String lang, String body);
    PendingRequest<String> createPaste(String lang, String body, String expiration);
}
