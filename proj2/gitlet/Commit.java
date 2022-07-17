package gitlet;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *
 *  @author xiaotianxt
 */
public class Commit implements Serializable, Iterable<Commit> {
    public static Commit load(String commitHash) {
        return readObject(join(Repository.GITLET_DIR, commitHash), Commit.class);
    }

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
        files = new HashMap<>();
        message = m;
        timestamp = t;
    }

    public Commit(String m, Date t, Commit parent) {
        files = new HashMap<>();
        message = m;
        timestamp = t;
        parentID = parent.hash();
    }

    public String hash() {
        return sha1(message, timestamp.toString());
    }

    /**
     * Return true if this commit contains a hash.
     */
    public boolean contains(File file) {
        return Utils.hashFile(file).equals(files.getOrDefault(file.getPath(), null));
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
        BlobRepo blobRepo = Repository.blobRepo();
        Map<String, String> blobs = stage.blobs();
        for (String filename : blobs.keySet()) {
            if (blobRepo.contains(filename)) {
                blobRepo.append(filename);
                continue;
            }
            blobRepo.add(filename);
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Commit> iterator() {
        return new Iterator<>() {
            private Commit sentinel = new Commit("sentinel", new Date(), Repository.head());
            @Override
            public boolean hasNext() {
                return sentinel.parentID != null;
            }

            @Override
            public Commit next() {
                sentinel = load(sentinel.parentID);
                return sentinel;
            }
        };
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);

        String date = dateFormat.format(timestamp);
        return "===\n" +
                "commit " + hash() + "\n" +
                "Date: " + date + "\n" +
                message + "\n";
    }
}
