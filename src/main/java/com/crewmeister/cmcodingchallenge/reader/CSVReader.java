package com.crewmeister.cmcodingchallenge.reader;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public interface CSVReader<T> {

  void processCSV();

  List<String[]> readCSV() throws FileNotFoundException;

  T mapRecordToDomain(String [] inputs);

  void insertRecords(List<T> records);
}
