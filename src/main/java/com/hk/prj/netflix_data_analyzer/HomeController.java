package com.hk.prj.netflix_data_analyzer;

import com.hk.prj.netflix_data_analyzer.model.Device;
import com.hk.prj.netflix_data_analyzer.model.IPAddressStreaming;
import com.hk.prj.netflix_data_analyzer.model.Report;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Controller
public class HomeController {

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping
    public String getHome(){
        return "index";
    }

    @PostMapping
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            model.addAttribute("file", file.getOriginalFilename());
            model.addAttribute("report", process(file));

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }

        return "index";
    }

    private Report process(MultipartFile file) {

        //save file
        Path pathToZipFile = Path.of(uploadPath + File.separator + System.currentTimeMillis()+"_"+ file.getOriginalFilename());
        saveFile(file, pathToZipFile);

        Report report = new Report();

        List<String> files = getFiles(pathToZipFile);
        report.setFiles(files);
        try {
            if (files.contains(Constants.DEVICES_FILE_PATH)) {
                List<Device> devices = getDevices(pathToZipFile);
                report.setDevices(devices);
            }
            if (files.contains(Constants.IP_ADDRESS_STREAMING_PATH)) {
                List<IPAddressStreaming> ipAddressStreamings = getIpAddressStreaming(pathToZipFile);
                report.setIpAddressStreamings(ipAddressStreamings);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return report;
    }

    private List<Device> getDevices(Path pathToZipFile) throws IOException {
        try (FileSystem fs = FileSystems.newFileSystem(pathToZipFile)) {
            List<String> lines = Files.readAllLines(fs.getPath(Constants.DEVICES_FILE_PATH));
            return IntStream.range(1, lines.size()).mapToObj(index -> processDeviceLine(lines.get(index))).collect(Collectors.toList());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Device processDeviceLine(String line) {
        String[] record = line.split(",");
        return new Device(record[0], record[2], record[1], record[4]);
    }

    private IPAddressStreaming processIpAddressStreamingLine(String line) {
        String[] record = line.split(",");
        return new IPAddressStreaming(record[3], record[1], record[4], record[5], record[6], record[7]);
    }

    private List<IPAddressStreaming> getIpAddressStreaming(Path pathToZipFile) throws IOException {
        try (FileSystem fs = FileSystems.newFileSystem(pathToZipFile)) {
            List<String> lines = Files.readAllLines(fs.getPath(Constants.IP_ADDRESS_STREAMING_PATH));
            return IntStream.range(1, lines.size()).mapToObj(index -> processIpAddressStreamingLine(lines.get(index))).collect(Collectors.toList());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveFile(MultipartFile file, Path pathToZipFile) {
        try {
            InputStream in = file.getInputStream();
            OutputStream out = Files.newOutputStream(pathToZipFile);
            in.transferTo(out);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getFiles(Path pathToZipFile) {
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
}


