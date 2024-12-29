package com.hk.prj.netflix_data_analyzer;

import com.hk.prj.netflix_data_analyzer.model.*;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.time.DurationFormatUtils;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class AnalysisService {

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

    public void upload(MultipartFile file) {
        pathToZipFile = Path.of(uploadPath + File.separator + System.currentTimeMillis()+"_"+ file.getOriginalFilename());
        saveFile(file, pathToZipFile);
        getFiles().forEach(System.out::println);
    }

    public List<Device> getDevices() {
        try (FileSystem fs = FileSystems.newFileSystem(pathToZipFile)) {
            Optional<Path> path = findPath(fs, Constants.DEVICES_FILE_PATH);
            if (path.isPresent()) {
                List<String> lines = Files.readAllLines(path.get());
                return IntStream.range(1, lines.size()).mapToObj(index -> processDeviceLine(lines.get(index)))
                        .distinct()
                        .filter(d -> StringUtils.hasLength(d.getLastUsedTime()))
                        .sorted(Comparator.comparing(Device::getProfile).thenComparing(Device::getDeviceType).thenComparing(Device::getLastUsedTime))
                        .collect(Collectors.toList());
            }
            else return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    private Device processDeviceLine(String line) {
        String[] record = line.split(",");
        return new Device("", record[2].trim(), record[4].trim());
    }

    private DeviceIPAddress processIpAddressStreamingLine(String line) {
        String[] record = line.split(",");
        return new DeviceIPAddress(record[3], record[1], record[4], record[5], record[6], record[7]);
    }

    public Map<String, List<DeviceIPAddress>> getIpAddressStreaming() {
        try (FileSystem fs = FileSystems.newFileSystem(pathToZipFile)) {
            List<String> lines = Files.readAllLines(fs.getPath(Constants.IP_ADDRESS_STREAMING_PATH));
            Map<String, List<DeviceIPAddress>> dataMap = IntStream.range(1, lines.size())
                    .mapToObj(index -> processIpAddressStreamingLine(lines.get(index)))
                    .collect(Collectors.groupingBy(DeviceIPAddress::getDevice));
            dataMap.values().forEach(x -> x.sort(Comparator.comparing(DeviceIPAddress::getIpAddress).thenComparing(DeviceIPAddress::getTimestamp)));
            return dataMap.values().stream()
                    .map(x -> x.stream().collect(Collectors.groupingBy(DeviceIPAddress::getIpAddress)).values().stream().map(this::findFirstInEachList).toList())
                    .flatMap(List::stream)
                    .sorted(Comparator.comparing(DeviceIPAddress::getIpAddress))
                    .collect(Collectors.groupingBy(DeviceIPAddress::getDevice));
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    private DeviceIPAddress findFirstInEachList(List<DeviceIPAddress> ipAddressStreamings) {
        ipAddressStreamings.sort(Comparator.comparing(DeviceIPAddress::getTimestamp));
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

    public List<String> getFiles() {
        try (FileSystem fs = FileSystems.newFileSystem(pathToZipFile)) {
            try (Stream<Path> entries = Files.walk(fs.getPath("/"))) {
                List<Path> filesInZip = entries.filter(Files::isRegularFile).toList();
                return filesInZip.stream().map(Path::toString).collect(Collectors.toList());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<ViewedContentResponse> getWatchedContent() {
        try (FileSystem fs = FileSystems.newFileSystem(pathToZipFile)) {
            Optional<Path> path = findPath(fs, Constants.VIEWED_CONTENT_PATH);
            if (path.isPresent()) {
                List<String> lines = Files.readAllLines(path.get());
                List<String> splitContent = Arrays.asList("episode", "limited series", "season");
                return IntStream.range(1, lines.size())
                        .mapToObj(index -> processViewedContentLine(lines.get(index)))
                        .filter(v -> !StringUtils.hasLength(v.getVideoType()))
                        .peek(v -> {
                            if (splitContent.stream().anyMatch(x -> v.getTitle().toLowerCase().contains(x))) {
                                String title = v.getTitle().split(":")[0];
                                v.setTitle(title);
                            }
                        })
                        .distinct()
                        .sorted(Comparator.comparing(ViewedContent::getTitle))
                        .collect(Collectors.groupingBy(ViewedContent::getProfile))
                        .entrySet().stream().map(e->{
                            String profile = e.getKey();
                            return e.getValue().stream()
                                    .collect(Collectors.groupingBy(ViewedContent::getYear))
                                    .entrySet().stream().map(e1 -> {
                                        String year = e1.getKey();
                                        Integer watchedContent = e1.getValue().size();
                                        Duration watchedDuration = Duration.ofSeconds(e1.getValue().stream().map(ViewedContent::getDuration).map(Duration::getSeconds).reduce(Long::sum).orElse(0L));
                                        return new ViewedContentResponse(profile, watchedContent, watchedDuration, DurationFormatUtils.formatDurationWords(watchedDuration.toMillis(), true, true), year);
                                    }).toList();
                        }).flatMap(List::stream)
                        .sorted(Comparator.comparing(ViewedContentResponse::getDuration).reversed())
                        .collect(Collectors.toList());
                        //.collect(Collectors.groupingBy(ViewedContent::getProfile, Collectors.mapping(ViewedContent::getTitle, Collectors.toList())));
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<ViewedContent> getWatchedContentMap(String profile, String year) {
        try (FileSystem fs = FileSystems.newFileSystem(pathToZipFile)) {
            Optional<Path> path = findPath(fs, Constants.VIEWED_CONTENT_PATH);
            if (path.isPresent()) {
                List<String> lines = Files.readAllLines(path.get());
                List<String> splitContent = Arrays.asList("episode", "limited series", "season");
                return IntStream.range(1, lines.size())
                        .mapToObj(index -> processViewedContentLine(lines.get(index)))
                        .filter(v -> !StringUtils.hasLength(v.getVideoType()) && v.getProfile().trim().equalsIgnoreCase(profile) && v.getYear().trim().equalsIgnoreCase(year))
                        .peek(v -> {
                            if (splitContent.stream().anyMatch(x -> v.getTitle().toLowerCase().contains(x))) {
                                String title = v.getTitle().split(":")[0];
                                v.setTitle(title);
                            }
                        })
                        .distinct()
                        .sorted(Comparator.comparing(ViewedContent::getTitle))
                        .collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private Optional<Path> findPath(FileSystem fs, String fileName) {
        try {
            return Files.walk(fs.getPath("/")).filter(path -> Files.isRegularFile(path) && path.endsWith(fileName)).findFirst();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SpentByYear> getPaymentDetails() {
        try (FileSystem fs = FileSystems.newFileSystem(pathToZipFile)) {
            Optional<Path> path = findPath(fs, Constants.PAYMENTS_PATH);
            if (path.isPresent()) {
                List<String> lines = Files.readAllLines(path.get());
                List<PaymentDetail> paymentDetails = IntStream.range(1, lines.size())
                        .mapToObj(index -> processPaymentDetailLine(lines.get(index)))
                        .filter(Objects::nonNull)
                        .toList();
                Map<String, List<PaymentDetail>> groupByYear = paymentDetails.stream()
                        .filter(v -> StringUtils.hasLength(v.getPriceAmt()))
                        .filter(v -> v.getTxnType().contains("SALE") || v.getTxnType().contains("CAPTURE"))
                        .filter(v -> v.getPmtStatus().contains("APPROVED") && v.getFinalInvoiceResult().contains("SETTLED"))
                        .collect(Collectors.groupingBy(PaymentDetail::getYear));
                return groupByYear.entrySet().stream().map(e-> {
                            Double spent = e.getValue().stream()
                                    .map(v -> Double.valueOf(v.getGrossSaleAmt().replace("\"", "")))
                            .reduce(Double::sum).orElse(0.0);
                            return new SpentByYear(e.getKey(), String.format("%.2f %s", spent, e.getValue().get(0).getCurrency()));
                        }).toList();
            }
            else return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private PaymentDetail processPaymentDetailLine(String line) {
        String[] record = line.split(",");
        if(record.length >= 15) {
            try {
                return new PaymentDetail(getYearFormDateFormat(record[0]), record[0], record[8], record[9], record[10], record[11], record[12], record[13], record[14]);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        else return null;
    }

    private ViewedContent processViewedContentLine(String line) {
        String[] record = line.split(",");
        Duration duration = convertStringToDuration(record[2]);
        String year;
        try {
            year = getYearFromDateTimeFormat(record[1]);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new ViewedContent(record[0], record[1], duration, record[4], record[5], year);
    }

    private static String getYearFromDateTimeFormat(String dateString) throws ParseException {
        SimpleDateFormat sdf ;
        if(dateString.contains("/")){
            sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        }
        else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        Date date = sdf.parse(dateString);
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        return df.format(date);
    }

    private static String getYearFormDateFormat(String dateString) throws ParseException {
        SimpleDateFormat sdf ;
        if(dateString.contains("/")){
            sdf = new SimpleDateFormat("MM/dd/yyyy");
        }
        else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        Date date = sdf.parse(dateString);
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        return df.format(date);
    }

    private static Duration convertStringToDuration(String durationString) {
        String[] arr = durationString.split(":");
        return Duration.ofSeconds(Long.parseLong(arr[0])*60*60 + Long.parseLong(arr[1])*60 + Long.parseLong(arr[2]));
    }

    public AccountDetail getAccountDetail() {
        try (FileSystem fs = FileSystems.newFileSystem(pathToZipFile)) {
            Optional<Path> path = findPath(fs, Constants.ACCOUNT_DETAILS_PATH);
            if (path.isPresent()) {
                List<String> lines = Files.readAllLines(path.get());
                String[] recordArray = lines.get(1)
                        .replace("\"", "").split(",");

                return new AccountDetail(recordArray[0], recordArray[1], recordArray[2]);
            }else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
