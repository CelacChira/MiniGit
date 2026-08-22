import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MiniGit {
    private static final String REPO_DIR = ".minigit";
    private static final String COMMITS_DIR = REPO_DIR + "/commits";
    private static final String HEAD_FILE = REPO_DIR + "/HEAD";
    private static final String LOG_FILE = REPO_DIR + "/log.txt";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("        MINI GIT         ");
        
        while (true) {
            System.out.print("\nminigit> ");
            String command = scanner.nextLine().trim();
            
            if (command.isEmpty()) continue;
            
            String[] parts = command.split(" ", 2);
            String cmd = parts[0].toLowerCase();
            
            try {
                switch (cmd) {
                    case "init":
                        init();
                        break;
                    case "commit":
                        if (parts.length < 2) {
                            System.out.println(" Foloseste: commit <mesaj>");
                        } else {
                            commit(parts[1]);
                        }
                        break;
                    case "log":
                        showLog();
                        break;
                    case "status":
                        status();
                        break;
                    case "restore":
                        if (parts.length < 2) {
                            System.out.println(" Foloseste: restore <commit_id>");
                        } else {
                            restore(parts[1]);
                        }
                        break;
                    case "diff":
                        showDiff();
                        break;
                    case "list":
                        listCommits();
                        break;
                    case "help":
                        showHelp();
                        break;
                    case "exit":
                        System.out.println(" La revedere");
                        return;
                    default:
                        System.out.println(" Comanda necunoscuta. Tasteaza 'help' pentru ajutor.");
                }
            } catch (Exception e) {
                System.out.println(" Eroare: " + e.getMessage());
            }
        }
    }

    static void init() throws IOException {
        File repoDir = new File(REPO_DIR);
        if (repoDir.exists()) {
            System.out.println(" Repository deja initializat");
            return;
        }
        
        new File(COMMITS_DIR).mkdirs();
        new File(HEAD_FILE).createNewFile();
        new File(LOG_FILE).createNewFile();
        
        System.out.println(" Repository MiniGit initializat cu succes");
        System.out.println(" Creat: " + REPO_DIR + "/");
    }
  
    static void commit(String message) throws IOException {
        if (!new File(REPO_DIR).exists()) {
            System.out.println(" Repository neinitializat Ruleaza 'init' mai intai.");
            return;
        }
        
        String commitId = "commit_" + System.currentTimeMillis();
        File commitDir = new File(COMMITS_DIR + "/" + commitId);
        commitDir.mkdirs();
        
        File currentDir = new File(".");
        int fileCount = copyFiles(currentDir, commitDir, "");
        
        if (fileCount == 0) {
            System.out.println("  Niciun fisier de salvat");
            commitDir.delete();
            return;
        }
        
        Files.write(Paths.get(HEAD_FILE), commitId.getBytes());
        
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logEntry = commitId + " | " + timestamp + " | " + message + " | " + fileCount + " fisiere\n";
        Files.write(Paths.get(LOG_FILE), logEntry.getBytes(), StandardOpenOption.APPEND);
        
        System.out.println(" Commit creat: " + commitId);
        System.out.println(" Mesaj: " + message);
        System.out.println(" Fisiere salvate: " + fileCount);
    }
    
    static int copyFiles(File source, File target, String relativePath) throws IOException {
        int count = 0;
        
        for (File file : source.listFiles()) {
            if (file.getName().equals(".minigit") || file.getName().equals(".git")) {
                continue;
            }
            
            if (file.isDirectory()) {
                File newTarget = new File(target, relativePath + file.getName());
                newTarget.mkdirs();
                count += copyFiles(file, target, relativePath + file.getName() + "/");
            } else {
                File targetFile = new File(target, relativePath + file.getName());
                Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                count++;
            }
        }
        
        return count;
    }
  
    static void showLog() throws IOException {
        if (!new File(LOG_FILE).exists()) {
            System.out.println(" Niciun commit in istoric");
            return;
        }
        
        List<String> logs = Files.readAllLines(Paths.get(LOG_FILE));
        
        if (logs.isEmpty()) {
            System.out.println(" Istoric gol. Creeaza primul commit");
            return;
        }
        
        System.out.println("\n       ISTORIC COMMIT-URI     ");
        Collections.reverse(logs);
        
        for (String log : logs) {
            String[] parts = log.split(" \\| ");
            System.out.println("\n Commit: " + parts[0]);
            System.out.println("  Data: " + parts[1]);
            System.out.println("  Mesaj: " + parts[2]);
            System.out.println("  Fisiere: " + parts[3]);
        }
        System.out.println("\n   ");
    }
    
    static void listCommits() {
        File commitsDir = new File(COMMITS_DIR);
        if (!commitsDir.exists()) {
            System.out.println(" Repository neinitializat");
            return;
        }
        
        File[] commits = commitsDir.listFiles();
        if (commits == null || commits.length == 0) {
            System.out.println(" Niciun commit disponibil.");
            return;
        }
        
        System.out.println("\n Commit-uri disponibile:");
        Arrays.sort(commits, Comparator.comparing(File::getName).reversed());
        
        for (File commit : commits) {
            System.out.println("    " + commit.getName());
        }
    }
    
    static void restore(String commitId) throws IOException {
        File commitDir = new File(COMMITS_DIR + "/" + commitId);
        
        if (!commitDir.exists()) {
            System.out.println(" Commit-ul nu exista: " + commitId);
            System.out.println(" Foloseste 'list' pentru a vedea commit-urile disponibile.");
            return;
        }
        
        System.out.print("  Sigur vrei sa restaurezi? Modificarile curente vor fi suprascrise (da/nu): ");
        Scanner scanner = new Scanner(System.in);
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("da")) {
            System.out.println(" Restaurare anulata.");
            return;
        }
        
        int restored = restoreFiles(commitDir, new File("."), "");
        
        Files.write(Paths.get(HEAD_FILE), commitId.getBytes());
        
        System.out.println(" Restaurat la commit: " + commitId);
        System.out.println(" Fisiere restaurate: " + restored);
    }
    
    static int restoreFiles(File source, File target, String relativePath) throws IOException {
        int count = 0;
        
        for (File file : source.listFiles()) {
            if (file.isDirectory()) {
                File newTarget = new File(target, file.getName());
                newTarget.mkdirs();
                count += restoreFiles(file, target, relativePath + file.getName() + "/");
            } else {
                File targetFile = new File(target, relativePath + file.getName());
                Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                count++;
            }
        }
        
        return count;
    }
    
    static void status() throws IOException {
        if (!new File(REPO_DIR).exists()) {
            System.out.println(" Repository neinitializat!");
            return;
        }
        
        String currentCommit = "Niciun commit";
        if (new File(HEAD_FILE).exists()) {
            currentCommit = new String(Files.readAllBytes(Paths.get(HEAD_FILE)));
        }
        
        System.out.println("\n        STATUS REPOSITORY      ");
        System.out.println(" HEAD: " + currentCommit);
        
        File commitsDir = new File(COMMITS_DIR);
        int totalCommits = commitsDir.exists() ? commitsDir.listFiles().length : 0;
        System.out.println(" Total commit-uri: " + totalCommits);
        
        File currentDir = new File(".");
        int currentFiles = countFiles(currentDir);
        System.out.println("  Fisiere curente: " + currentFiles);
        System.out.println("     \n");
    }

    static int countFiles(File dir) {
        int count = 0;
        for (File file : dir.listFiles()) {
            if (file.getName().equals(".minigit") || file.getName().equals(".git")) {
                continue;
            }
            if (file.isDirectory()) {
                count += countFiles(file);
            } else {
                count++;
            }
        }
        return count;
    }

    static void showDiff() throws IOException {
        String currentCommit = new String(Files.readAllBytes(Paths.get(HEAD_FILE)));
        File commitDir = new File(COMMITS_DIR + "/" + currentCommit);
        
        if (!commitDir.exists()) {
            System.out.println(" Niciun commit de comparat!");
            return;
        }
        
        System.out.println("\n Comparatie cu ultimul commit:");
        System.out.println("                                 ");
        
        File[] currentFiles = new File(".").listFiles();
        for (File file : currentFiles) {
            if (file.isFile() && !file.getName().equals(".minigit")) {
                File oldFile = new File(commitDir, file.getName());
                if (!oldFile.exists()) {
                    System.out.println(" NOU: " + file.getName());
                } else {
                    long currentSize = file.length();
                    long oldSize = oldFile.length();
                    if (currentSize != oldSize) {
                        System.out.println(" MODIFICAT: " + file.getName() + 
                                         " (" + oldSize + "→" + currentSize + " bytes)");
                    }
                }
            }
        }
    }
    
    static void showHelp() {
        System.out.println("\n  COMENZI DISPONIBILE ");
        System.out.println("  init              - Initializeaza repository");
        System.out.println("  commit <mesaj>    - Creeaza un snapshot");
        System.out.println("  log               - Afisează istoric");
        System.out.println("  list              - Listeaza toate commit-urile");
        System.out.println("  restore <id>      - Restaureaza un commit");
        System.out.println("  status            - Afisează starea curenta");
        System.out.println("  diff              - Arata modificarile");
        System.out.println("  help              - Afisează acest mesaj");
        System.out.println("  exit              - Iesire");
        System.out.println(" \n");
    }
}