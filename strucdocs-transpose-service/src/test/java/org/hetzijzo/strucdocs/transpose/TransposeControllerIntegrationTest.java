package org.hetzijzo.strucdocs.transpose;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransposeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void transposeSimple() {
        Chord chordRequest = Chord.builder().note(Note.C).build();

        ResponseEntity<Chord> chordResponseEntity =
            this.restTemplate.postForEntity("/?key={key}", chordRequest, Chord.class, -1);

        assertThat(chordResponseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(chordResponseEntity.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));

        Chord chordResponse = chordResponseEntity.getBody();
        assertThat(chordResponse, notNullValue());
        assertThat(chordResponse.getNote(), is(Note.B));
    }

    @Test
    public void transposeNoKey() {
        Chord chordRequest = Chord.builder().note(Note.C).build();

        ResponseEntity<Chord> chordResponseEntity =
            this.restTemplate.postForEntity("/", chordRequest, Chord.class);

        assertThat(chordResponseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(chordResponseEntity.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));

        Chord chordResponse = chordResponseEntity.getBody();
        assertThat(chordResponse, equalTo(chordRequest));
    }
}
