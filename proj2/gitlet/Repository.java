package gitlet;

import java.io.File;
import java.util.Date;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /** The staged folder */
    public static final File STAGED_FILE = join(GITLET_DIR, "staged");

    /** The head commit reference to current commit */
    public static final File HEAD_FILE = join(GITLET_DIR, "head");

    /**
     * command: gitlet init
     */
    public static void init() {
        // if the .gitlet directory already exists
        if (GITLET_DIR.isDirectory()) {
            System.out.println("A Gitlet version-control system already exists in the current directory");
            System.exit(0);
        }

        // else if .gitlet is a file
        if (GITLET_DIR.isFile()) {
            System.out.println(GITLET_DIR.getName() + " is a file, cannot create repo.");
            System.exit(0);
        }

        // else create the gitlet repository.
        if (!GITLET_DIR.mkdir()) {
            throw error("Error when creating directory.");
        }

        // make basic files.
        Commit initCommit = new Commit("init commit", new Date(0));
        Stage stage = new Stage();
        stage.save();
        initCommit.save();
    }

    /**
     * command: gitlet add [filename]
     */
    public static void add(String fileName) {
        File file = join(CWD, fileName);
        // file doesn't exist.
        if (!file.exists()) {
            System.out.println("File not found!");
            System.exit(0);
        }
        // file is directory.
        if (file.isDirectory()) {
            throw error("Should not do this");
        }

        Stage stage = readObject(STAGED_FILE, Stage.class);
        stage.add(file);
        stage.save(); // remember to save file.
    }

    /**
     * Reference to the head commit.
     */
    private Commit head;
}
