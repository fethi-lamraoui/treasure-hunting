package com.centreon.treasurehunting;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.centreon.treasurehunting.enums.OrientationEnum;
import com.centreon.treasurehunting.exception.BusinessException;
import com.centreon.treasurehunting.exception.LineException;
import com.centreon.treasurehunting.factory.AdventurerFactory;
import com.centreon.treasurehunting.factory.GameControllerProcessorFactory;
import com.centreon.treasurehunting.factory.TreasureMapFactory;
import com.centreon.treasurehunting.mapper.AdventurerMapper;
import com.centreon.treasurehunting.mapper.TreasureMapMapper;
import com.centreon.treasurehunting.model.Adventurer;
import com.centreon.treasurehunting.model.Position;
import com.centreon.treasurehunting.model.TreasureMap;
import com.centreon.treasurehunting.processor.AdventurerProcessor;
import com.centreon.treasurehunting.processor.GameControllerProcessor;
import com.centreon.treasurehunting.service.GameService;
import com.centreon.treasurehunting.util.FileUtil;
import com.centreon.treasurehunting.validator.FileLignesValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.centreon.treasurehunting.exception.BusinessException.*;
import static com.centreon.treasurehunting.exception.FileException.ERROR_WHILE_READING;
import static com.centreon.treasurehunting.exception.LineException.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class TreasureHuntingApplicationTests {

	@InjectMocks
	TreasureHuntingApplication treasureHuntingApplication;

	@Mock
	FileLignesValidator fileLignesValidator;
	@Mock
	AdventurerMapper adventurerMapper;
	@Mock
	TreasureMapMapper treasureMapMapper;
	@Mock
	GameService gameService;

	private ListAppender<ILoggingEvent> logWatcher;

	@BeforeEach
	void setup() {
		logWatcher = new ListAppender<>();
		logWatcher.start();
		((Logger) LoggerFactory.getLogger(TreasureHuntingApplication.class)).addAppender(logWatcher);
	}

	@AfterEach
	void teardown() {
		((Logger) LoggerFactory.getLogger(TreasureHuntingApplication.class)).detachAndStopAllAppenders();
	}

	@Test
	void run_incorrect_input() {
		String[] args = {""};
		treasureHuntingApplication.run(args);
		assertEquals("No input files", logWatcher.list.get(0).getMessage());
	}

	@Test
	void run_icorrect_files() {
		String[] args = {"src/test/resources/mm", "src/test/resources/adv"};
		treasureHuntingApplication.run(args);
		assertEquals(ERROR_WHILE_READING, logWatcher.list.get(0).getMessage());
	}

	@Test
	void run_invalid_map_file_lines() {
		String[] args = {"src/test/resources/map", "src/test/resources/adventurers"};

		doThrow(new LineException(NO_LINES))
				.when(fileLignesValidator).validateMapFileLines(anyList());
		treasureHuntingApplication.run(args);
		assertEquals(NO_LINES, logWatcher.list.get(0).getMessage());

		doThrow(new LineException(INVALID_LINE))
				.when(fileLignesValidator).validateMapFileLines(anyList());
		treasureHuntingApplication.run(args);
		assertEquals(INVALID_LINE, logWatcher.list.get(1).getMessage());

		doThrow(new LineException(NO_MAP_LINES))
				.when(fileLignesValidator).validateMapFileLines(anyList());
		treasureHuntingApplication.run(args);
		assertEquals(NO_MAP_LINES, logWatcher.list.get(2).getMessage());
	}

	@Test
	void run_invalid_adventurer_file_lines() {
		String[] args = {"src/test/resources/map", "src/test/resources/adventurers"};

		doThrow(new LineException(INVALID_ADVENTURER_LINE))
				.when(fileLignesValidator).validateAdventurerFileLines(anyList());
		treasureHuntingApplication.run(args);
		assertEquals(INVALID_ADVENTURER_LINE, logWatcher.list.get(0).getMessage());

		doThrow(new LineException(INVALID_ADVENTURER_LINE))
				.when(fileLignesValidator).validateAdventurerFileLines(anyList());
		treasureHuntingApplication.run(args);
		assertEquals(INVALID_ADVENTURER_LINE, logWatcher.list.get(1).getMessage());

		doThrow(new LineException(NO_LINES))
				.when(fileLignesValidator).validateAdventurerFileLines(anyList());
		treasureHuntingApplication.run(args);
		assertEquals(NO_LINES, logWatcher.list.get(2).getMessage());
	}

	@Test
	void run_incorrect_positions() {
		String[] args = {"src/test/resources/map", "src/test/resources/adventurers"};

		when(adventurerMapper.mapLinesToAdventurers(anyList())).thenReturn(List.of(AdventurerFactory.getAdventurer(AdventurerFactory.JOHN_CASE)));
		when(treasureMapMapper.mapLinesToMap(anyList())).thenReturn(TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE));

		doThrow(new BusinessException(TREASURE_EXCEEDS_BOUNDARIES))
				.when(gameService).validatePositions(any(TreasureMap.class), anyList());
		treasureHuntingApplication.run(args);
		assertEquals(TREASURE_EXCEEDS_BOUNDARIES, logWatcher.list.get(0).getMessage());

		doThrow(new BusinessException(MOUNTAIN_EXCEEDS_BOUNDARIES))
				.when(gameService).validatePositions(any(TreasureMap.class), anyList());
		treasureHuntingApplication.run(args);
		assertEquals(MOUNTAIN_EXCEEDS_BOUNDARIES, logWatcher.list.get(1).getMessage());

		doThrow(new BusinessException(ADVENTURER_INCORRECT_POSITION))
				.when(gameService).validatePositions(any(TreasureMap.class), anyList());
		treasureHuntingApplication.run(args);
		assertEquals(ADVENTURER_INCORRECT_POSITION, logWatcher.list.get(2).getMessage());
	}

	@Test
	void run_incorrect_adventurers_places() {
		String[] args = {"src/test/resources/map", "src/test/resources/adventurers"};

		when(adventurerMapper.mapLinesToAdventurers(anyList())).thenReturn(List.of(AdventurerFactory.getAdventurer(AdventurerFactory.JOHN_CASE)));
		when(treasureMapMapper.mapLinesToMap(anyList())).thenReturn(TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE));

		doThrow(new BusinessException(BUSINESS_ERROR_OCCURED))
				.when(gameService).placeAdventurersOnMap(any(TreasureMap.class), anyList());
		treasureHuntingApplication.run(args);
		assertEquals(BUSINESS_ERROR_OCCURED, logWatcher.list.get(0).getMessage());
	}

	@Test
	void run_happy_path() {
		String[] args = {"src/test/resources/map", "src/test/resources/adventurers"};

		Adventurer expectedJohnAdventurer = Adventurer
				.builder()
				.name("John")
				.position(Position
						.builder()
						.x(2)
						.y(3)
						.build())
				.orientation(OrientationEnum.S)
				.moves(List.of('A', 'A', 'D', 'A', 'D', 'A', 'G', 'A'))
				.treasures(new ArrayList<>())
				.elapsedTime(8)
				.build();

		Adventurer expectedDoeAdventurer = Adventurer
				.builder()
				.name("Doe")
				.position(Position
						.builder()
						.x(4)
						.y(2)
						.build())
				.orientation(OrientationEnum.N)
				.moves(List.of('A', 'A', 'D', 'A', 'D', 'A', 'G', 'A'))
				.treasures(List.of(Position
						.builder()
								.x(1)
								.y(4)
						.build(),
						Position
								.builder()
								.x(4)
								.y(2)
								.build()))
				.elapsedTime(15)
				.build();


		when(adventurerMapper.mapLinesToAdventurers(anyList()))
				.thenReturn(List.of(AdventurerFactory.getAdventurer(AdventurerFactory.JOHN_CASE),
				AdventurerFactory.getAdventurer(AdventurerFactory.DOE_CASE)));

		when(treasureMapMapper.mapLinesToMap(anyList()))
				.thenReturn(TreasureMapFactory.getTreasureMap(TreasureMapFactory.CASUAL_CASE));

		GameControllerProcessor gameControllerProcessor = GameControllerProcessorFactory
				.getGameControllerProcessor(GameControllerProcessorFactory.CASUAL_CASE);

		when(gameService.prepareGameControllerProcessor(any(TreasureMap.class), anyList()))
				.thenReturn(gameControllerProcessor);

		treasureHuntingApplication.run(args);

		AdventurerProcessor adventurerProcessor1 = gameControllerProcessor.getAdventurerProcessors().get(0);
		AdventurerProcessor adventurerProcessor2 = gameControllerProcessor.getAdventurerProcessors().get(1);

		assertEquals(Thread.State.TERMINATED, adventurerProcessor1.getState());
		assertEquals(Thread.State.TERMINATED, adventurerProcessor2.getState());

		if(adventurerProcessor1.getAdventurer().getName().equals("John")) {
			assertAdventurer(expectedJohnAdventurer, adventurerProcessor1.getAdventurer());
			assertAdventurer(expectedDoeAdventurer, adventurerProcessor2.getAdventurer());
		} else {
			assertAdventurer(expectedJohnAdventurer, adventurerProcessor2.getAdventurer());
			assertAdventurer(expectedDoeAdventurer, adventurerProcessor1.getAdventurer());
		}

		Path filePath = Paths.get("results");
		assertEquals(true, Files.exists(filePath));
	}

	private void assertAdventurer(Adventurer expectedAdventurer, Adventurer adventurer) {
		assertEquals(expectedAdventurer.getName(), adventurer.getName());
		assertEquals(expectedAdventurer.getPosition(), adventurer.getPosition());
		assertEquals(expectedAdventurer.getOrientation(), adventurer.getOrientation());
		assertEquals(expectedAdventurer.getTreasures(), adventurer.getTreasures());
		assertEquals(expectedAdventurer.getElapsedTime(), adventurer.getElapsedTime());
	}

}
