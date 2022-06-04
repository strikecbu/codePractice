import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sound.sampled.SourceDataLine;

import static java.nio.file.Files.find;

public class CleanTool {

    public static void main(String[] args) throws IOException {
        List<String> folders = List.of("node_modules", "target");
        Path filePath = Paths.get("/Users/andy/Documents/lesson");
        try (Stream<Path> pathStream = find(filePath,
                Integer.MAX_VALUE,
                (path, fileAttr) -> Files.isDirectory(path) && folders.contains(path.toFile()
                        .getName()))) {
            pathStream
                    .forEach(path -> {
                        try (Stream<Path> walk = Files.walk(path)) {
                            ArrayList<File> files = walk.sorted(Comparator.reverseOrder())
                                    .map(Path::toFile)
                                    .collect(Collectors.toCollection(ArrayList::new));
                                    
                            System.out.println("Clean Folder: " + path.toFile().getAbsolutePath());
                            double totalSize = (long) files.size();
                            System.out.println("Total stream size: " + totalSize);

                            files.stream()
                                    // .peek(file -> {
                                    //     System.out.println(file.getAbsolutePath());
                                    // })
                                    .reduce(0.0, (acc, file) -> {
                                        var count = acc + 1;
                                        long percent = Math.round(count / totalSize * 100);
                                        if (percent % 10 == 0) {
                                            if(percent == 100) {
                                                System.out.printf("%s%%%n", percent);
                                            } else {
                                                System.out.printf("%s%%... ", percent);
                                            }
                                        }
                                        file.delete();
                                        return count;
                                    }, Double::sum);
                        } catch (IOException e) {
                            System.out.println(e);
                            throw new RuntimeException(e);
                        }
                    });
        }
    }
}
