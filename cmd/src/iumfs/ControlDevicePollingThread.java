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

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.logging.Logger;

/** 
 * Worker Thread which opens iumfscntl device
 */
public abstract class ControlDevicePollingThread extends Thread {

    protected static final Logger logger = Logger.getLogger("iumfs");

    public ControlDevicePollingThread (String name){
        super(name);
    }

    @Override
    public void run() {
        ByteBuffer rbbuf = ByteBuffer.allocate(Request.DEVICE_BUFFER_SIZE);
        rbbuf.order(ByteOrder.nativeOrder());
        FileInputStream devis = null;
        FileOutputStream devos = null;
        int len;
        Request req = null;
        RandomAccessFile raf = null;

        try {
            raf = new RandomAccessFile("/dev/iumfscntl", "rw");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        FileChannel ch = raf.getChannel();
        logger.fine("Successfully open device.");

        logger.fine("Started");

        while (true) {
            try {
                /*
                 * Read request data from iumfs device
                 */
                rbbuf.clear();
                if ((len = ch.read(rbbuf)) < 0) {
                    logger.severe("read from device failed");
                    System.exit(1);
                }

                logger.finer("device returns " + len + " bytes ");

                /*
                 * Create request object
                 */
                req = RequestFactory.getInstance(rbbuf);
                setFile(req);
                
                if (req == null) {
                    logger.severe("Request object is null");
                    System.exit(1);
                }
                /*
                 * Execute request
                 */
                logger.fine("calling " + req.getClass().getSimpleName() + ".execute()");
                req.execute();
            } catch (NotADirectoryException ex){                
                logger.finer(ex.getMessage());                
                req.setResponseHeader(Request.ENOTDIR, 0);                
            } catch (FileNotFoundException ex) {
                logger.finer(ex.getMessage());
                req.setResponseHeader(Request.ENOENT, 0);
            } catch (FileExistException ex) {
                req.setResponseHeader(Request.EEXIST, 0);
            } catch (IOException ex) {
                logger.severe(ex.getMessage());
                req.setResponseHeader(Request.EIO, 0);
            } catch (NotSupportedException ex) {
                logger.finer("NotsupportedException happened");
                req.setResponseHeader(Request.ENOTSUP, 0);
            } catch (RuntimeException ex) {
                /*
                 * It is important to return an error to driver, even
                 * if exception happend.
                 * Convert RuntimeException to EIO error.
                 */
                logger.severe("RuntimeException happened");
                logger.severe(ex.getMessage());
                ex.printStackTrace();
                StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                StringBuilder stacktrace = new StringBuilder();
                for (int i = 0; i < elements.length; i++) {
                    stacktrace.append(elements[i]);
                }
                logger.severe(stacktrace.toString());
                req.setResponseHeader(Request.EIO, 0);
            }
            /*
             * Write response to driver.
             */
            try {
                ch.write(req.getResponseBuffer());
            } catch (IOException ex) {
                logger.severe(ex.getMessage());
                System.exit(1);
            }
            logger.finer("request for " + req.getClass().getSimpleName() + " finished.");
        }
    }

    protected abstract void setFile(Request req);
}
