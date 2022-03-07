package com.crewmeister.cmcodingchallenge;

import com.crewmeister.cmcodingchallenge.reader.impl.CurrencyCSVReader;
import com.crewmeister.cmcodingchallenge.reader.impl.HistoricalConversionRateCSVReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.transaction.Transactional;

@SpringBootApplication
@EnableJpaRepositories
public class CmCodingChallengeApplication implements CommandLineRunner {

	@Autowired
	private CurrencyCSVReader currencyCSVReader;

	@Autowired
	private HistoricalConversionRateCSVReader historicalCurrencyCSVReader;

	public static void main(String[] args) {
		SpringApplication.run(CmCodingChallengeApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... arg0){

		this.currencyCSVReader.processCSV();
		this.historicalCurrencyCSVReader.processCSV();
	}

}
