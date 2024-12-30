package com.hk.prj.netflix_data_analyzer.service;

import com.hk.prj.netflix_data_analyzer.entity.UploadAnalysis;
import com.hk.prj.netflix_data_analyzer.entity.UploadDetail;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.net.URI;

@Component
public class DynamoDBService {

    Region dynamoDbRegion;
    private final String uploadDetailTable = "upload_detail";
    private final String uploadAnalysisTable = "upload_analysis";

    @Value("${dynamodb.url}")
    private String dynamodbHostUrl;

    @Value("${dynamodb.region}")
    private String dynamoDbRegionString;

    private DynamoDbEnhancedClient enhancedClient;

    @PostConstruct
    public void init() {
        dynamoDbRegion = Region.of(dynamoDbRegionString);
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .region(dynamoDbRegion)
                .endpointOverride(URI.create(dynamodbHostUrl))
                .build();
        this.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    public UploadDetail getUserUpload(String email) {
        try{
            DynamoDbTable<UploadDetail> table = enhancedClient.table(uploadDetailTable, TableSchema.fromBean(UploadDetail.class));

            Key key = Key.builder()
                    .partitionValue(email)
                    .build();
           return table.getItem(r-> r.key(key));
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean saveUserUpload(UploadDetail uploadDetail) {
        try{
            DynamoDbTable<UploadDetail> table = enhancedClient.table(uploadDetailTable, TableSchema.fromBean(UploadDetail.class));
            table.updateItem(uploadDetail);
            return true;
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public UploadAnalysis getUploadAnalysis(String email) {
        try{
            DynamoDbTable<UploadAnalysis> table = enhancedClient.table(uploadAnalysisTable, TableSchema.fromBean(UploadAnalysis.class));

            Key key = Key.builder()
                    .partitionValue(email)
                    .build();

            return table.getItem(r-> r.key(key));
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean saveUploadAnalysis(UploadAnalysis uploadAnalysis) {
        try{
            DynamoDbTable<UploadAnalysis> table = enhancedClient.table(uploadAnalysisTable, TableSchema.fromBean(UploadAnalysis.class));
            table.updateItem(uploadAnalysis);
            return true;
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
