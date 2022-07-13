package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static gitlet.Utils.*;

/**
 * A stage that contains all files added to the stage.
 */
public class Stage implements Serializable {
    /**
     * A map from the file path to the file in commit folder.
     */
    private Map<String, String> blobs;

    public Stage() {
        blobs = new HashMap<>();
    }

    public static Stage load() {
        return readObject(Repository.STAGED_FILE, Stage.class);
    }

    /**
     * Returns the blob file if add successfully
     */
    public void add(File file) {
        file = relativize(file);
        String blobID = blobs.getOrDefault(file.getPath(), null);
        if (blobID != null) {
            // stage contains blob
            if (blobID.equals(hash(file))) {
                // same with stage, ignore
                System.out.println("Ignore");
                System.exit(0);
            } else {
                // not same, delete previous and add this.
                File oldStagedBlob = join(Repository.GITLET_DIR, blobID);
                if (!oldStagedBlob.delete()) {
                    throw error("Cannot remove previously staged file!");
                }
            }
        }
        File blob = createBlobReference(file);
        saveBlob(file, blob);
        blobs.put(file.getPath(), blob.getName());
    }

    public void clear() {
        blobs.clear();
    }

    public void save() {
        writeObject(Repository.STAGED_FILE, this);
    }
}
