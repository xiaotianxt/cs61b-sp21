package gitlet;

import org.junit.Test;

import java.io.File;

public class BlobLinkedListTest {
    @Test
    public void testBlobLoad() {
        BlobLinkedList tree = new BlobLinkedList(new File("hello"));
        System.out.println(tree);
    }
}
