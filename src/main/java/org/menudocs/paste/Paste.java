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

public interface Paste {
    /**
     * Returns the id of the paste
     *
     * @return The id of the paste
     */
    String getId();

    /**
     * Returns the body of the paste
     *
     * @return The body of the paste
     */
    String getBody();

    /**
     * Returns the url of the paste, this url can be used to view the paste in a browser
     *
     * @return The url of the paste
     */
    String getPasteUrl();

    /**
     * Returns the language info of the paste
     *
     * @return The language info of the paste
     */
    Language getLanguage();
}
