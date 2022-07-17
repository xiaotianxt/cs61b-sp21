package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

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
     * Get the head commit of current Repository.
     */
    public static Commit head() {
        return readObject(join(GITLET_DIR, getInstance().head), Commit.class) ;
    }

    public static BlobRepo blobRepo() {
        return getInstance().blobRepo;
    }

    public static Stage stage() {
        return getInstance().stage;
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
        if (repository.blobRepo.containsHash(fileName, hashFile(file))) {
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

        Commit commit = new Commit(message, new Date(), head());
        Stage stage = repository.stage;
        commit.summary(stage);
        commit.save();

        repository.head = commit.hash();
        saveRepo();
    }

    /**
     * command: gitlet log
     */
    public static void log() {
        Commit head = head();
        for (Commit item: head) {
            System.out.println(item);
        }
    }

    /**
     * 1. checkout -- [filename]
     * 2. checkout [commit id] -- [filename]
     * 3. checkout [branch name]
     */
    public static void checkout(String[] args) {
        System.out.println(args.length);
        String commitId, dash, filename, branchName;
        switch (args.length) {
            case 3: // checkout -- [filename]
                dash = args[1];
                if (!dash.equals("--")) {
                    System.out.println("Syntax error.");
                    return;
                }
                filename = relativize(args[2]);
                checkoutFile(filename);
                break;
            case 4:
                commitId = args[1];
                dash = args[2];
                filename = relativize(args[3]);
                checkoutFile(commitId, filename);
                break;
        }
    }

    /**
     * checkout -- [filename]
     */
    private static void checkoutFile(String filename) {
        checkoutFile(getInstance().head, filename);
    }

    /**
     * checkout [commit id] -- [filename]
     */
    private static void checkoutFile(String commitId, String filename) {
        Commit commit = retrieveCommit(commitId);
        if (commit == null) {
            System.out.println("Commit not found.");
            System.exit(0);
        }
//        commit.print();
        File blob = commit.getBlob(filename);
        if (blob == null) {
            System.out.println("File not found in this commit.");
            System.exit(0);
        }
        Utils.restrictedDelete(filename);
        Utils.copy(blob, new File(filename));
//        System.out.println("checking out " + filename + " from commit: " + commitId);
    }

    /**
     * Returns a commit that has 'commitId' from GITLET_DIR.
     * Returns `null' if the 'commitId' doesn't exist in GITLET_DIR.
     */
    private static Commit retrieveCommit(String commitId) {
        Queue<Commit> commits = new LinkedList<>();
        commits.add(head());
        while (!commits.isEmpty()) {
            Commit curr = commits.poll();
            if (curr.hash().equals(commitId)) {
                return curr;
            }
            Commit parent = curr.parent();
            Commit secondParent = curr.secondParent();
            if (parent != null) {
                commits.add(parent);
            }
            if (secondParent != null) {
                commits.add(secondParent);
            }
        }
        return null;
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

    private static Repository load() {
        return readObject(join(GITLET_DIR, "repo"), Repository.class);
    }

    /**
     * Reference to some key objects.
     */
    private String head;
    private BlobRepo blobRepo;
    private Stage stage;

    public Repository() {
        // make basic files.
        Commit initCommit = new Commit("initial commit", new Date(0));
        initCommit.save();
        stage = new Stage();
        blobRepo = new BlobRepo();
        head = initCommit.hash();
    }

    private void save() {
        writeObject(join(GITLET_DIR, "repo"), this);
    }
}
