package org.hoey.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Stream;

/**
 * 流库测试
 *
 * @author Joy
 * @date 2020-08-27
 */
public class StreamTest {

    private static final Logger log = LoggerFactory.getLogger(StreamTest.class);

    private static final String PATH = "src/main/resources/alice.txt";
    private static final String ROOT_PATH = "src";

    public static void main(String[] args) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(PATH)), StandardCharsets.UTF_8);
        log.info("read all bytes:{}", content);
        log.info("============================================");
        try (Stream<String> lines = Files.lines(Paths.get(PATH), StandardCharsets.UTF_8)) {
            lines.peek(log::info).map(String::toUpperCase).forEach(log::info);
        }
        log.info("============================================");
        List<String> strings = Files.readAllLines(Paths.get(PATH), StandardCharsets.UTF_8);
        strings.forEach(log::info);
        log.info("============================================");
        Path dir = Paths.get(ROOT_PATH);
        try (Stream<Path> walk = Files.walk(dir, FileVisitOption.FOLLOW_LINKS)) {
            walk.forEach(path -> log.info(path.toUri().getPath()));
        }
        log.info("============================================");
        try (Stream<Path> finds = Files.find(dir, Integer.MAX_VALUE,
            (path, basicFileAttributes) -> path.toUri().getPath().endsWith(".java") && basicFileAttributes
                .isRegularFile(), FileVisitOption.FOLLOW_LINKS)) {
            finds.forEach(path -> log.info(path.toUri().getPath()));
        }
        log.info("============================================");
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(dir, "**.java")) {
            paths.forEach(path -> log.info(path.toUri().getPath()));
        }
        log.info("============================================");
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });


    }

}
