package org.strucdocs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.EndpointMBeanExportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.strucdocs.component.artist.ArtistRepository;
import org.strucdocs.component.band.BandRepository;
import org.strucdocs.component.musician.MusicianRepository;
import org.strucdocs.component.repertoire.RepertoireRepository;
import org.strucdocs.component.song.SongRepository;
import org.strucdocs.component.user.UserRepository;
import org.strucdocs.model.Artist;
import org.strucdocs.model.Band;
import org.strucdocs.model.Musician;
import org.strucdocs.model.Repertoire;
import org.strucdocs.model.Song;
import org.strucdocs.model.User;

@SpringBootApplication(exclude = {EndpointMBeanExportAutoConfiguration.class})
@EnableJpaRepositories
@EnableWebMvc
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class StrucDocsApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(StrucDocsApplication.class, args);
    }

    @Bean
    public RelProvider relProvider() {
        return new JsonRootRelProvider();
    }

    @Bean
    CommandLineRunner createUser(UserRepository userRepository) {
        return args -> {
        };
    }

    @Bean
    CommandLineRunner createBand(UserRepository userRepository,
                                 BandRepository bandRepository,
                                 MusicianRepository musicianRepository,
                                 RepertoireRepository repertoireRepository) {
        return args -> {
            Band band = bandRepository.save(Band.builder()
                .name("Make My Day")
                .build());
            Musician musician = musicianRepository.save(Musician.builder()
                .name("Willem Cheizoo")
                .band(band)
                .build());

            userRepository
                .save(User.builder().username("1282567135137155").name("Willem Cheizoo").musician(musician).build());

            repertoireRepository.save(Repertoire.builder().name("Big Repertoire").band(band).build());
        };
    }

    @Bean
    CommandLineRunner createSongs(ArtistRepository artistRepository, SongRepository songRepository) {
        return args -> {
            Artist artist = Artist.builder()
                .name("Michael Jackson")
                .build();
            artistRepository.save(artist);

            songRepository.save(Song.builder().artist(artist).title("Thriller").build());
            songRepository.save(Song.builder().artist(artist).title("Bad").build());
        };
    }
}
