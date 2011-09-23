/*
 * Copyright 2010 Kazuyoshi Aizawa
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.logging.Logger;

/**
 * FileOutputStream for IUMFS
 */
abstract public class IumfsFileOutputStraem extends FileOutputStream{
    private IumfsFile file;
    protected static final Logger logger = Logger.getLogger("iumfs");
    
    @Override
    abstract public void write(byte[] buf, int size, int offset);
    
    public IumfsFileOutputStraem(IumfsFile file) throws FileNotFoundException{
        super(file);
        this.file = file;
    }    
}