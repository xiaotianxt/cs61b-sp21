package gitlet;

import java.io.File;

import static gitlet.Utils.join;

/**
 * A linked list that offers the history of a given file.
 * All files in `FileNode' locate in `.gitlet/blobs'.
 */
public class BlobLinkedList {
    /**
     * This is the file in working directory.
     */
    private File file;
    /**
     * This is the latest commit of the file.
     */
    private BlobNode head;

    private static class BlobNode {
        String blobID;
        String hash;
        BlobNode prev;

        /**
         * Constructor of a FileNode
         *
         * @param blobID: The new file
         * @param head:
         */
        public BlobNode(String blobID, BlobNode head) {
            this.blobID = blobID;
            this.prev = head;
        }
    }

    public BlobLinkedList(File file) {
        this.file = file;
    }

    /**
     * Returns the head file.
     */
    public File head() {
        return join(Repository.GITLET_DIR, head.blobID);
    }

    /**
     * Add a new blob, append it to FileTree.
     */
    public void add(String blobID) {
        head = new BlobNode(blobID, head);
    }

    /**
     * Returns true if the file hashing is in this file tree.
     */
    public boolean contains(String hash) {
        BlobNode curr = head;
        while (curr != null) {
            if (curr.blobID.equals(hash)) {
                return true;
            }
            curr = curr.prev;
        }
        return false;
    }

    @Override
    public String toString() {
        BlobNode curr = head;
        StringBuilder sb = new StringBuilder();
        while (curr != null) {
            sb.append(curr.blobID);
            sb.append(", ");
            curr = curr.prev;
        }
        return sb.toString();
    }
}
