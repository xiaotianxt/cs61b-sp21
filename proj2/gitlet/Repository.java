package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import static gitlet.Utils.*;


/**
 * Represents a gitlet repository.
 * This is a singleton Class, it always returns the same repository instance when anyone needs.
 *
 *  @author xiaotianxt
 */
public class Repository implements Serializable {

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /** The singleton object */
    private static Repository repository;

    /**
     * Get Repository instance. If no repository exists
     */
    public static Repository getInstance() {
        if (repository != null) {
            return repository;
        }

        if (!GITLET_DIR.isDirectory() || !join(GITLET_DIR, "repo").exists()) {
            System.out.println("You are not in a gitlet repository.");
            System.exit(0);
        }

        repository = Repository.load();
        return repository;
    }

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

        // create repository.
        Repository repo = new Repository();
        repo.save();
    }

    /**
     * command: gitlet add [filename]
     */
    public static void add(String fileName) {
        loadRepo();

        fileName = relativize(fileName);
        File file = join(fileName);

        // file doesn't exist.
        if (!file.exists()) {
            System.out.println("File not found!");
            System.exit(0);
        }
        // file is directory.
        if (file.isDirectory()) {
            throw error("Should not do this");
        }

        // add file to stage
        if (repository.blobRepo.containsHash(fileName, hash(file))) {
            return;
        }
        Stage stage = repository.stage;
        stage.add(file);

        saveRepo();
    }

    /**
     * command: gitlet commit [message]
     */
    public static void commit(String message) {
        loadRepo();

        Commit commit = new Commit(message, new Date());
        Stage stage = repository.stage;
        commit.summary(stage);

        saveRepo();
    }

    public static void loadRepo() {
        if (Repository.getInstance() == null) {
            System.out.println("You should initialize the gitlet repository first.");
            System.exit(0);
        }
    }

    public static void saveRepo() {
        if (repository == null) {
            throw error("No update needed");
        }

        repository.save();
    }

    /**
     * Reference to some key objects.
     */
    public String head;
    public BlobRepo blobRepo;
    public Stage stage;

    public Repository() {
        // make basic files.
        Commit initCommit = new Commit("init commit", new Date(0));
        initCommit.save();
        stage = new Stage();
        blobRepo = new BlobRepo();
        head = initCommit.hash();
    }

    private void save() {
        writeObject(join(GITLET_DIR, "repo"), this);
    }

    private static Repository load() {
        return readObject(join(GITLET_DIR, "repo"), Repository.class);
    }
}
