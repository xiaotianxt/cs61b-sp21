package gitlet;

import org.junit.Test;

import java.io.File;

public class FileTreeTest {
    @Test
    public void testFileTreeLoad() {
        BlobLinkedList tree = new BlobLinkedList(new File("hello"));
        System.out.println(tree);
    }
}
