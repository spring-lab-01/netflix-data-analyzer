package com.hk.prj.netflix_data_analyzer;

import com.hk.prj.netflix_data_analyzer.model.Device;
import com.hk.prj.netflix_data_analyzer.model.IPAddressStreaming;
import com.hk.prj.netflix_data_analyzer.model.Report;
import com.hk.prj.netflix_data_analyzer.model.ViewedContent;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class FileUploadService {

    private Report report;

    @Value("${upload.path}")
    private String uploadPath;

    private Path pathToZipFile;

    @PostConstruct
    public void settingPathToZipFile() {
        try(Stream<Path> files = Files.list(Path.of(uploadPath))) {
            files.findFirst().ifPresent(path -> pathToZipFile = path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void upload(MultipartFile file) {
        pathToZipFile = Path.of(uploadPath + File.separator + System.currentTimeMillis()+"_"+ file.getOriginalFilename());
        saveFile(file, pathToZipFile);
    }

    List<Device> getDevices() {
        try (FileSystem fs = FileSystems.newFileSystem(pathToZipFile)) {
            List<String> lines = Files.readAllLines(fs.getPath(Constants.DEVICES_FILE_PATH));
            return IntStream.range(1, lines.size()).mapToObj(index -> processDeviceLine(lines.get(index)))
                    .distinct()
                    .sorted(Comparator.comparing(Device::getProfile).thenComparing(Device::getDeviceType).thenComparing(Device::getLastUsedTime))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Device processDeviceLine(String line) {
        String[] record = line.split(",");
        return new Device("", record[2].trim(), record[1].trim(), record[4].trim());
    }

    private IPAddressStreaming processIpAddressStreamingLine(String line) {
        String[] record = line.split(",");
        return new IPAddressStreaming(record[3], record[1], record[4], record[5], record[6], record[7]);
    }

    Map<String, List<IPAddressStreaming>> getIpAddressStreaming() {
        try (FileSystem fs = FileSystems.newFileSystem(pathToZipFile)) {
            List<String> lines = Files.readAllLines(fs.getPath(Constants.IP_ADDRESS_STREAMING_PATH));
            Map<String, List<IPAddressStreaming>> dataMap = IntStream.range(1, lines.size())
                    .mapToObj(index -> processIpAddressStreamingLine(lines.get(index)))
                    .collect(Collectors.groupingBy(IPAddressStreaming::getDevice));
            dataMap.values().forEach(x -> x.sort(Comparator.comparing(IPAddressStreaming::getIpAddress).thenComparing(IPAddressStreaming::getTimestamp)));
            return dataMap.values().stream()
                    .map(x -> x.stream().collect(Collectors.groupingBy(IPAddressStreaming::getIpAddress)).values().stream().map(this::findFirstInEachList).toList())
                    .flatMap(List::stream)
                    .sorted(Comparator.comparing(IPAddressStreaming::getIpAddress))
                    .collect(Collectors.groupingBy(IPAddressStreaming::getDevice));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private IPAddressStreaming findFirstInEachList(List<IPAddressStreaming> ipAddressStreamings) {
        ipAddressStreamings.sort(Comparator.comparing(IPAddressStreaming::getTimestamp));
        return ipAddressStreamings.get(0);
    }

    void saveFile(MultipartFile file, Path pathToZipFile) {
        try {
            InputStream in = file.getInputStream();
            OutputStream out = Files.newOutputStream(pathToZipFile);
            in.transferTo(out);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    List<String> getFiles() {
        try (FileSystem fs = FileSystems.newFileSystem(pathToZipFile)) {
            try (Stream<Path> entries = Files.walk(fs.getPath("/"))) {
                List<Path> filesInZip = entries.filter(Files::isRegularFile).toList();
                return filesInZip.stream().map(Path::toString).collect(Collectors.toList());
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<String>> getWatchedContent() {
        try (FileSystem fs = FileSystems.newFileSystem(pathToZipFile)) {
            List<String> lines = Files.readAllLines(fs.getPath(Constants.VIEWED_CONTENT_PATH));
            List<String> splitContent = Arrays.asList("episode", "limited series", "season");
            return IntStream.range(1, lines.size())
                    .mapToObj(index -> processViewedContentLine(lines.get(index)))
                    .filter(v-> !StringUtils.hasLength(v.getVideoType()))
                    .peek(v -> {
                        if(splitContent.stream().anyMatch(x -> v.getTitle().toLowerCase().contains(x))) {
                            String title = v.getTitle().split(":")[0];
                            v.setTitle(title);
                        }
                    })
                    .distinct()
                    .sorted(Comparator.comparing(ViewedContent::getTitle))
                    .collect(Collectors.groupingBy(ViewedContent::getProfile, Collectors.mapping(ViewedContent::getTitle, Collectors.toList())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private ViewedContent processViewedContentLine(String line) {
        String[] record = line.split(",");
        return new ViewedContent(record[0], record[1], record[4], record[5]);
    }
}
