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

import java.io.*;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Date;

abstract public class IumfsFile extends java.io.File {
    final public static int VREG = 1; // Normal File
    final public static int VDIR = 2; // Directory
    protected long length = 0;
    private long atime; // Last access time (msec)
    private long ctime; // Modify time(csec)
    private long mtime; // Modify time(msec)
    protected boolean directory = false;
    
    
    public IumfsFile(String path_name){
        super(path_name);
        init();
    }

    private void init() {
        Date now = new Date();
        setAtime(now.getTime());
        setCtime(now.getTime());
        setMtime(now.getTime());
    }

    public void setLength(long length){
        this.length = length;
    }
    
    @Override
    public long length() {
        return length;
    }

    /**
     * Read requested data from requested offset/size and copy to specified buffer.
     * 
     * @param  buffer where read data to be copied
     * @param  size of data
     * @param  offset of date to be read
     * @return read byte
     * @throws ngException 
     */
    abstract public long read(ByteBuffer buf, long size, long offset)
            throws FileNotFoundException, IOException, NotSupportedException;   
    
    /**
     * Write data to requested offset/size.
     * 
     * @param  buf
     * @param  size to be written
     * @param  offset to be written
     * @return 0
     * @throws ngException 
     */
    abstract public long write(byte[] buf, long size, long offset)
            throws FileNotFoundException, IOException, NotSupportedException;       
    
    /**
     * 
     * @return 
     */
    @Override
    abstract public boolean delete();
    
    /**
     * Create new directory
     * 
     * @return
     * @throws FileExistException 
     */
    @Override
    abstract public boolean mkdir()
            throws FileExistException;
    
    @Override
    abstract public File[] listFiles();

    /**
     * @return the atime
     */
    public long getAtime() {
        return atime;
    }

    /**
     * @param atime the atime to set
     */
    public void setAtime(long atime) {
        this.atime = atime;
    }

    /**
     * @return the ctime
     */
    public long getCtime() {
        return ctime;
    }

    /**
     * @param ctime the ctime to set
     */
    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    /**
     * @return the mtime
     */
    public long getMtime() {
        return mtime;
    }

    /**
     * @param mtime the mtime to set
     */
    public void setMtime(long mtime) {
        this.mtime = mtime;
    }
    
    /**
     * Get filetype like VDIR, VREG
     * @return filetype
     */
    abstract public long getFileType();
    /**
     * Get prmission of this file
     * @return permission
     */
    abstract public long getPermission();
    /**
     * Check if this file represents directory.
     * @return 
     */
    @Override
    abstract public boolean isDirectory();

    abstract public void create() throws IOException;
    
    @Override
    public boolean isFile() {
        return ! isDirectory();
    }    
    
    @Override
    public boolean canExecute() {throw new NotSupportedException();}
    
    @Override
    public boolean canRead(){throw new NotSupportedException();}
 
    @Override
    public boolean canWrite() {throw new NotSupportedException();}
    
    @Override
    public boolean createNewFile() {throw new NotSupportedException();}
    
    public static IumfsFile	createTempFile(String prefix, String suffix) {
        throw new NotSupportedException();
    }
    public static IumfsFile	createTempFile(String prefix, String suffix, IumfsFile directory) {
        throw new NotSupportedException();
    }
    @Override
    public void	deleteOnExit() {throw new NotSupportedException();}
    @Override
    public boolean exists(){ throw new NotSupportedException();}
    @Override
    public long getFreeSpace(){ throw new NotSupportedException();}
    @Override
    public long	getTotalSpace() {throw new NotSupportedException();}
    @Override
    public long	getUsableSpace() {throw new NotSupportedException();}
    @Override
    public boolean isAbsolute() {throw new NotSupportedException();}
    @Override
    public boolean isHidden() {throw new NotSupportedException();}
    @Override
    public long lastModified() {throw new NotSupportedException();}
    @Override
    public String[] list() {throw new NotSupportedException();}
    @Override
    public String[] list(FilenameFilter filter) {throw new NotSupportedException();}
    @Override
    public File[] listFiles(FileFilter filter) {throw new NotSupportedException();}
    @Override
    public File[] listFiles(FilenameFilter filter) {throw new NotSupportedException();}
    public static IumfsFile[] listRoots() {throw new NotSupportedException();}
    @Override
    public boolean mkdirs() {throw new NotSupportedException();}
    public boolean renameTo(IumfsFile dest) {throw new NotSupportedException();}
    @Override
    public boolean setExecutable(boolean executable) {throw new NotSupportedException();}
    @Override
    public boolean setExecutable(boolean executable, boolean ownerOnly) {throw new NotSupportedException();}
    @Override
    public boolean setLastModified(long time) {throw new NotSupportedException();}
    @Override
    public boolean setReadable(boolean readable) {throw new NotSupportedException();}
    @Override
    public boolean setReadable(boolean readable, boolean ownerOnly) {throw new NotSupportedException();}
    @Override
    public boolean setReadOnly() {throw new NotSupportedException();}
    @Override
    public boolean setWritable(boolean writable) {throw new NotSupportedException();}
    @Override
    public boolean setWritable(boolean writable, boolean ownerOnly) {throw new NotSupportedException();}
    @Override
    public URI toURI() {throw new NotSupportedException();}
}
