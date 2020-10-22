package com.printway.business.services;

import com.printway.business.models.Dispute;
import com.printway.business.repositories.DisputeRepository;
import com.printway.common.time.TimeParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DisputeServiceImpl implements DisputeService {
    @Autowired
    private DisputeRepository disputeRepository;

    public static String TYPE = "text/csv";
    static String[] HEADERs = {
            "Order ID",
            "Transaction",
            "Amount",
            "Dispute Fee",
            "Paygate Name",
            "Total",
            "Seller ID",
            "Reason",
            "Date",
    };

    @Override
    public List<Dispute> save(MultipartFile file) {
        try {
            var disputes = csvToDisputes(file.getInputStream());
            log.info(disputes.toString());
//            disputeRepository.saveAll(disputes);
            return disputes;
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    @Override
    public boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    @Override
    public List<Dispute> csvToDisputes(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            var csvRecords = csvParser.getRecords();

            return csvRecords.stream()
                    .map(csv -> new Dispute(
                            0,
                            csv.get(HEADERs[0]),
                            csv.get(HEADERs[1]),
                            Double.parseDouble(csv.get(HEADERs[2])),
                            Double.parseDouble(csv.get(HEADERs[3])),
                            csv.get(HEADERs[4]),
                            Double.parseDouble(csv.get(HEADERs[5])),
                            csv.get(HEADERs[6]),
                            csv.get(HEADERs[7]),
                            TimeParser.parseZonedDateTimeToLocalDateTime(csv.get(HEADERs[8]))
                    ))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException("Fail to parse CSV file: " + ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException("Somethings went wrong: " + ex.getMessage());
        }
    }

}
