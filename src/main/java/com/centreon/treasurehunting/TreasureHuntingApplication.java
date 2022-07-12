package com.centreon.treasurehunting;

import com.centreon.treasurehunting.exception.TreasureHuntingException;
import com.centreon.treasurehunting.mapper.AdventurerMapper;
import com.centreon.treasurehunting.mapper.TreasureMapMapper;
import com.centreon.treasurehunting.model.Adventurer;
import com.centreon.treasurehunting.model.TreasureMap;
import com.centreon.treasurehunting.processor.GameControllerProcessor;
import com.centreon.treasurehunting.service.GameService;
import com.centreon.treasurehunting.util.FileUtil;
import com.centreon.treasurehunting.validator.FileLignesValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class TreasureHuntingApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(TreasureHuntingApplication.class);
	private final FileLignesValidator fileLignesValidator;
	private final AdventurerMapper adventurerMapper;
	private final TreasureMapMapper treasureMapMapper;
	private final GameService gameService;

	public TreasureHuntingApplication(FileLignesValidator fileLignesValidator, AdventurerMapper adventurerMapper, TreasureMapMapper treasureMapMapper, GameService gameService) {
		this.fileLignesValidator = fileLignesValidator;
		this.adventurerMapper = adventurerMapper;
		this.treasureMapMapper = treasureMapMapper;
		this.gameService = gameService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TreasureHuntingApplication.class, args);
	}

	@Override
	public void run(String... args) {

		if(args != null && args.length == 2) {

			try {
				List<String> treasureMapLines = FileUtil.readFile(args[0]);
				List<String> adventurerLines = FileUtil.readFile(args[1]);

				fileLignesValidator.validateMapFileLines(treasureMapLines);
				fileLignesValidator.validateAdventurerFileLines(adventurerLines);

				List<Adventurer> adventurers = adventurerMapper.mapLinesToAdventurers(adventurerLines);
				TreasureMap treasureMap = treasureMapMapper.mapLinesToMap(treasureMapLines);

				gameService.validatePositions(treasureMap, adventurers);
				gameService.placeAdventurersOnMap(treasureMap, adventurers);

				GameControllerProcessor gameControllerProcessor = gameService.prepareGameControllerProcessor(treasureMap, adventurers);
				gameControllerProcessor.startGame();
				gameControllerProcessor.gameStatus();
			} catch (TreasureHuntingException exception) {
				logger.info(exception.getMessage(), exception);
			} catch (IOException exception) {
				logger.info(exception.getMessage(), exception);
			}

		} else {
			logger.info("No input files");
		}

	}

}
