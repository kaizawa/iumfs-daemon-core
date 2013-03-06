/*
 * Copyright 2011 Kazuyoshi Aizawa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iumfs;

import java.io.IOException;

/**
 * <p>CREATE Request Class</p>
 */
public class CreateRequest extends Request {

    /**
     * <p>Excecute FileSystem.create</p>
     * <p>
     * </p>
     */
    @Override
    public void execute() throws IOException {
        IumfsFile file = getFile();
        if (file == null) {
            setResponseHeader(ENOENT, 0);
            return;
        }
        file.create();
        /*
         * レスポンスヘッダをセット
         */
        setResponseHeader(SUCCESS, 0);
    }
}
