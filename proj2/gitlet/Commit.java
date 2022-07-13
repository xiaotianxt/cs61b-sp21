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
     * The file mapping, it maps a file that locates inside the working directory
     * to the FileTree that describes the historical commits.
     */
    private Map<File, BlobLinkedList> files;

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

    public Commit(String m) {
        message = m;
    }

    @Override
    public String toString() {
        return message + timestamp.toString();
    }

    public void save() {
        String fileName = sha1(message, timestamp.toString());
        File commitFile = join(Repository.GITLET_DIR, fileName);
        writeObject(commitFile, this);
    }
}
