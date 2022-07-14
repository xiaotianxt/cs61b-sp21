package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *
 *  @author xiaotianxt
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    private String message;

    /**
     * The timestamp of this Commit.
     */
    private Date timestamp;

    /**
     * The author info
     */
    private String author;

    /**
     * The file mapping, it maps a file path that locates inside the working directory
     * to the GITLET_DIR that has the historical commits. (k, v) === (f.getPath(), hash(f))
     */
    private Map<String, String> files;

    /**
     * Reference to parent commit.
     */
    private String parentID;

    /**
     * Used only for merge.
     */
    private String secondParentID;

    public Commit(String m, Date t) {
        message = m;
        timestamp = t;
    }

    @Override
    public String toString() {
        return message + timestamp.toString();
    }

    public String hash() {
        return sha1(message, timestamp.toString());
    }

    /**
     * Save commit as file.
     */
    public void save() {
        String fileName = hash();
        File commitFile = join(Repository.GITLET_DIR, fileName);
        writeObject(commitFile, this);
    }

    /**
     * Giving a stage object, it summarizes all files.
     */
    public void summary(Stage stage) {
        BlobRepo blobRepo = BlobRepo.load();
        Map<String, String> blobs = stage.blobs();
        for (String filename : blobs.keySet()) {
            if (blobRepo.contains(filename)) {
                continue;
            }
            blobRepo.add(filename);
        }
    }
}
