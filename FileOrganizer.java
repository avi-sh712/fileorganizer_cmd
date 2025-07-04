import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileOrganizer {

    private static final Map<String, String> extensionMap = new HashMap<>();

    static {
        extensionMap.put("jpg", "Images");
        extensionMap.put("jpeg", "Images");
        extensionMap.put("png", "Images");
        extensionMap.put("gif", "Images");
        extensionMap.put("mp4", "Videos");
        extensionMap.put("mkv", "Videos");
        extensionMap.put("mov", "Videos");
        extensionMap.put("pdf", "Documents");
        extensionMap.put("docx", "Documents");
        extensionMap.put("txt", "Documents");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the full directory path to organize: ");
        String pathInput = scanner.nextLine();

        File folderToOrganize = new File(pathInput);
        if (!folderToOrganize.exists() || !folderToOrganize.isDirectory()) {
            System.out.println("The path you entered is not a valid directory.");
            return;
        }

        File[] allFiles = folderToOrganize.listFiles();
        if (allFiles == null || allFiles.length == 0) {
            System.out.println("The folder is empty. Nothing to organize.");
            return;
        }
        for (File file : allFiles) {
            if (file.isFile()) {
                String extension = getFileExtension(file.getName());
                String category = extensionMap.getOrDefault(extension, "Others");
                moveFileToFolder(file, category);
            }
        }

        System.out.println("All files have been neatly organized!");
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return ""; // No extension found
        }
        return fileName.substring(dotIndex + 1).toLowerCase();
    }

    private static void moveFileToFolder(File file, String category) {
        Path parentFolder = file.getParentFile().toPath();
        Path destinationFolder = parentFolder.resolve(category);

        try {
            if (!Files.exists(destinationFolder)) {
                Files.createDirectory(destinationFolder);
            }

            Path newLocation = destinationFolder.resolve(file.getName());
            Files.move(file.toPath(), newLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            System.out.println("Couldn’t move file: " + file.getName() + ". Reason: " + e.getMessage());
        }
    }
}
