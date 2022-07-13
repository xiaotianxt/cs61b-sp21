package gitlet;

import org.junit.Test;

import java.io.File;

public class StageTest {
    @Test
    public void testStageInit() {
        Stage stage = Utils.readObject(Repository.STAGED_FILE, Stage.class);
        File makefile = new File("Makefile");
        stage.add(makefile);
        stage.save();
    }
}
