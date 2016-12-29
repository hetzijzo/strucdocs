package org.strucdocs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.strucdocs.component.artist.ArtistRepository;
import org.strucdocs.component.band.BandRepository;
import org.strucdocs.component.musician.MusicianRepository;
import org.strucdocs.component.repertoire.RepertoireRepository;
import org.strucdocs.component.song.SongRepository;
import org.strucdocs.component.user.UserRepository;
import org.strucdocs.model.Artist;
import org.strucdocs.model.Band;
import org.strucdocs.model.Instrument;
import org.strucdocs.model.Musician;
import org.strucdocs.model.Repertoire;
import org.strucdocs.model.RepertoireSong;
import org.strucdocs.model.Song;
import org.strucdocs.model.User;

@Configuration
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

            Song song1 = songRepository.save(Song.builder().artist(artist).title("Thriller").build());
            Song song2 = songRepository.save(Song.builder().artist(artist).title("Bad").build());

            repertoireRepository.save(Repertoire.builder()
                .name("Big Repertoire")
                .band(band)
                .song(RepertoireSong.builder().songUuid(song1.getUuid()).build())
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

    @Bean
    CommandLineRunner createSongs_Artists() {
        return args -> {

        };
    }
}
