package com.centreon.treasurehunting.processor;

import com.centreon.treasurehunting.exception.BusinessException;
import com.centreon.treasurehunting.util.FileUtil;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.centreon.treasurehunting.exception.BusinessException.BUSINESS_ERROR_OCCURED;

@Data
@Builder
public class GameControllerProcessor {

    private List<AdventurerProcessor> adventurerProcessors;

    public void startGame() {
        adventurerProcessors.forEach(AdventurerProcessor::start);
    }

    public void gameStatus() throws IOException {

        adventurerProcessors.forEach(adventurerProcessor -> {
            try {
                adventurerProcessor.join();
            } catch (InterruptedException e) {
                throw new BusinessException(BUSINESS_ERROR_OCCURED);
            }
        });

        Files.write(FileUtil.createFile(), adventurerProcessors
                .stream()
                .map(adventurerProcessor -> adventurerProcessor.getAdventurer().toString())
                .collect(Collectors.toList()));
    }

}
