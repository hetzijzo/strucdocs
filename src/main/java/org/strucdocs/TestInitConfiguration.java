package org.strucdocs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.strucdocs.component.artist.ArtistRepository;
import org.strucdocs.component.band.BandRepository;
import org.strucdocs.component.document.DocumentGeneratorService;
import org.strucdocs.component.document.content.DocumentContentService;
import org.strucdocs.component.musician.MusicianRepository;
import org.strucdocs.component.repertoire.RepertoireRepository;
import org.strucdocs.component.song.SongRepository;
import org.strucdocs.component.user.UserRepository;
import org.strucdocs.model.Artist;
import org.strucdocs.model.Band;
import org.strucdocs.model.Chord;
import org.strucdocs.model.Instrument;
import org.strucdocs.model.Musician;
import org.strucdocs.model.Repertoire;
import org.strucdocs.model.RepertoireSong;
import org.strucdocs.model.Song;
import org.strucdocs.model.SongLine;
import org.strucdocs.model.SongPart;
import org.strucdocs.model.User;

@Configuration
@Profile("test")
public class TestInitConfiguration {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BandRepository bandRepository;
    @Autowired
    MusicianRepository musicianRepository;
    @Autowired
    RepertoireRepository repertoireRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    SongRepository songRepository;

    @Autowired
    DocumentGeneratorService documentGeneratorService;
    @Autowired
    DocumentContentService documentContentService;

    @Bean
    CommandLineRunner createBand_Members_Users() {
        return args -> {
            Band band = bandRepository.save(Band.builder()
                .name("Make My Day")
                .build());

            Musician musician = createMusician(band, "Willem Cheizoo", Instrument.PIANIST);
            createMusician(band, "Merel Bakker", Instrument.VOCALIST);
            createMusician(band, "Take Buis", Instrument.VOCALIST);
            createMusician(band, "Jos Kroon", Instrument.GUITARIST);
            createMusician(band, "Frans Peelen", Instrument.GUITARIST);
            createMusician(band, "Bram Haftka", Instrument.DRUMMER);

            userRepository.save(User.builder()
                .username("1282567135137155")
                .name("Willem Cheizoo")
                .musician(musician)
                .build()
            );

            Artist artist = Artist.builder()
                .name("Michael Jackson")
                .build();
            artistRepository.save(artist);

            Song song1 = songRepository.save(
                Song.builder()
                    .artist(artist)
                    .title("Thriller")
                    .part(SongPart.builder()
                        .type("Intro")
                        .line(SongLine.builder()
                            .chord(Chord.fromString("C#m7"))
                            .chord(Chord.fromString("F#7"))
                            .chord(Chord.fromString("G#"))
                            .build())
                        .line(SongLine.builder()
                            .chord(Chord.fromString("C#m"))
                            .chord(Chord.fromString("E"))
                            .chord(Chord.fromString("F#"))
                            .chord(Chord.fromString("C#m"))
                            .build())
                        .line(SongLine.builder()
                            .chord(Chord.fromString("C#m7"))
                            .chord(Chord.fromString("F#7"))
                            .build())
                        .line(SongLine.builder()
                            .chord(Chord.fromString("C#m7"))
                            .chord(Chord.fromString("F#7"))
                            .build())
                        .build())
                    .part(SongPart.builder()
                        .type("Verse 1")
                        .line(SongLine.builder()
                            .lyrics("It's close to midnight, and something evil's, lurking in the dark.")
                            .chord(Chord.fromString("C#m7"))
                            .chord(Chord.fromString("F#7"))
                            .build())
                        .line(SongLine.builder()
                            .lyrics("Under the moonlight, you see a sight, that almost stops your heart.")
                            .chord(Chord.fromString("C#m"))
                            .build())
                        .line(SongLine.builder()
                            .lyrics("You try to scream, but terror takes the sound, before you make it.")
                            .chord(Chord.fromString("F#7"))
                            .chord(Chord.fromString("C#m7"))
                            .build())
                        .line(SongLine.builder()
                            .lyrics("You start to freeze, as horror looks you right between the eyes.")
                            .chord(Chord.fromString("F#7"))
                            .chord(Chord.fromString("C#m7"))
                            .build())
                        .line(SongLine.builder()
                            .lyrics("You're para-lyzed.")
                            .chord(Chord.fromString("G#m"))
                            .build())
                        .build())
                    .build()
            );

            Song song2 = songRepository.save(Song.builder().artist(artist).title("Bad").build());

//            String dest1 = "/home/willem/Documents/song1.pdf";
//            File file1 = new File(dest1);
//            file1.getParentFile().mkdirs();
//            OutputStream outputStream1 = new FileOutputStream(file1);
//            documentGeneratorService.generateDocument(outputStream1, song1);
//
//            MockMultipartFile multipartFile = new MockMultipartFile("song1", "my document content".getBytes());
//            documentContentService.saveDocument(multipartFile);

//            String dest2 = "/home/willem/Documents/song2.pdf";
//            File file2 = new File(dest2);
//            file2.getParentFile().mkdirs();
//            OutputStream outputStream2 = new FileOutputStream(file2);
//            documentGeneratorService.generateDocument(outputStream2, song2);

            repertoireRepository.save(Repertoire.builder()
                .name("Big Repertoire")
                .band(band)
                .song(RepertoireSong.builder().songUuid(song1.getUuid()).build())
                .song(RepertoireSong.builder().songUuid(song2.getUuid()).build())
                .build()
            );
        };
    }

    private Musician createMusician(Band band, String name, Instrument instrument) {
        return musicianRepository.save(Musician.builder()
            .name(name)
            .band(band)
            .instrument(instrument)
            .build());
    }
}
