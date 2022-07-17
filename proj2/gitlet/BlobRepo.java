package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import static gitlet.Utils.*;

/**
 *
 */
public class BlobRepo implements Serializable {
    private Map<String, BlobLinkedList> blobRepo;

    public BlobRepo() {
        this.blobRepo = new HashMap<>();
    }

    public void add(String filename) {
        if (contains(filename)) {
            throw error("Should not remember a file that has already added");
        }

        blobRepo.put(filename, new BlobLinkedList(filename));
    }

    public void append(String filename) {
        BlobLinkedList list = blobRepo.get(filename);
        list.add(Utils.hashFile(filename));
    }

    public boolean contains(File file) {
        String filename = file.getPath();
        return contains(filename);
    }

    public boolean contains(String filename) {
        return blobRepo.containsKey(filename);
    }

    public boolean containsHash(String filename, String blobID) {
        if (!blobRepo.containsKey(filename)) {
            return false;
        }
        BlobLinkedList blobList = blobRepo.get(filename);
        return blobList.contains(blobID);
    }
}
