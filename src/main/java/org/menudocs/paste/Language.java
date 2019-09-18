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

/**
 * Represents a language object, a full list of languages can be found at <a href="https://paste.menudocs.org/languages.json">https://paste.menudocs.org/languages.json</a>
 *
 */
public interface Language {

    /**
     * Returns the id of the language, eg "text"
     *
     * @return The id of the language
     */
    String getId();

    /**
     * Returns the name of the language, eg "Plain Text"
     *
     * @return The name of the language
     */
    String getName();
}
