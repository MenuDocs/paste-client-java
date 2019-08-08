package org.menudocs.paste;

import com.github.natanbc.reliqua.request.PendingRequest;

public interface PasteClient {
    PendingRequest<Paste> createPaste(String lang, String body, String expiration);
}
