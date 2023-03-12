package org.example.util;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

public class FolderListenerUtilities {

    public static String newFileCreated(Path path, AtomicBoolean running) { //static method that returns absolute string path to newly created object
        try { //check if the given path is folder !!!should never fail, but this is an extra safety measure!!!
            boolean isFolder = (boolean) Files.getAttribute(path, "basic:isDirectory", NOFOLLOW_LINKS);
            if (!isFolder) {
                throw new IllegalArgumentException("not a folder");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileSystem fileSystem = path.getFileSystem(); //generating a new fileSystem

        try (WatchService watchService = fileSystem.newWatchService()) {
            path.register(watchService, ENTRY_CREATE); //watch service that watches for file creation //https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/WatchService.html
            WatchKey key;
            while (true && running.get()) {//loop
                key = watchService.poll();//poll does not block the tread
                WatchEvent.Kind<?> kind = null;
                if(key != null) { //if key exists
                    for (WatchEvent<?> wEvent : key.pollEvents()) {
                        kind = wEvent.kind();
                        if (kind == ENTRY_CREATE) { // if creation happened
                            Path retPath = ((WatchEvent<Path>) wEvent).context();
                            return path.toString().concat("\\").concat(retPath.getFileName().toString()); // return the new file adress
                        }
                    }
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}
