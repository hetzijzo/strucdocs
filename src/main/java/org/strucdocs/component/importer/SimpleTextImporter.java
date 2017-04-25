package org.strucdocs.component.importer;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.strucdocs.model.Artist;
import org.strucdocs.model.Chord;
import org.strucdocs.model.Song;
import org.strucdocs.model.SongLine;
import org.strucdocs.model.SongPart;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SimpleTextImporter implements Importer {

    @Data
    @RequiredArgsConstructor
    private class ImportSongBuilder {
        private final Song.SongBuilder songBuilder = Song.builder();
        private SongPart currentPart;
        private SongLine currentSongLine;
    }


    @Override
    public Song importSong(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        ImportSongBuilder importSongBuilder = new ImportSongBuilder();
        bufferedReader.lines().forEach(line -> readLine(importSongBuilder, line));

        return importSongBuilder.getSongBuilder().build();
    }

    private void readLine(ImportSongBuilder importSongBuilder, String line) {
        Song.SongBuilder songBuilder = importSongBuilder.getSongBuilder();
        if (StringUtils.isBlank(line)) {
            return;
        }

        Pattern titlePattern = Pattern.compile("^Title:\\s*(\\w*)\\s*$");
        Matcher titleMatcher = titlePattern.matcher(line);
        if (titleMatcher.matches()) {
            MatchResult result = titleMatcher.toMatchResult();
            songBuilder.title(result.group(1));
            return;
        }

        Pattern artistPattern = Pattern.compile("^Artist:\\s*(\\w*)\\s*$");
        Matcher artistMatcher = artistPattern.matcher(line);
        if (artistMatcher.matches()) {
            MatchResult result = artistMatcher.toMatchResult();
            songBuilder.artist(
                Artist.builder()
                    .name(result.group(1))
                    .build()
            );
            return;
        }

        Pattern partPattern = Pattern.compile("^.*(Intro|Verse|Chorus|Solo|Outro)\\s*(.*):*\\s*$");
        Matcher partMatcher = partPattern.matcher(line);
        if (partMatcher.matches()) {
            MatchResult result = partMatcher.toMatchResult();
            SongPart songPart = SongPart.builder()
                .type(result.group(1))
                .build();
            songBuilder.part(songPart);
            importSongBuilder.setCurrentPart(songPart);
            importSongBuilder.setCurrentSongLine(null);
            return;
        }

        if (importSongBuilder.getCurrentSongLine() == null) {
            SongLine.SongLineBuilder songLineBuilder = SongLine.builder();
            Arrays.stream(StringUtils.split(line, ' '))
                .filter(Chord::isChord)
                .map(Chord::fromString)
                .forEach(songLineBuilder::chord);

            SongLine songLine = songLineBuilder.build();
            importSongBuilder.getCurrentPart().addLine(songLine);
            importSongBuilder.setCurrentSongLine(songLine);
        } else {
            SongLine songLine = importSongBuilder.getCurrentSongLine();
            songLine.setLyrics(line);
            importSongBuilder.setCurrentSongLine(null);
        }
    }
}
