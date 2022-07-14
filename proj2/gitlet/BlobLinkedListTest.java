package gitlet;

import org.junit.Test;

import java.io.File;

public class BlobLinkedListTest {
    @Test
    public void testBlobLoad() {
        BlobLinkedList tree = new BlobLinkedList("hello");
        System.out.println(tree);
    }
}
