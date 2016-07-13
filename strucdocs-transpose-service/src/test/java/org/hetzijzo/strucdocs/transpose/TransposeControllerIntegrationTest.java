package org.hetzijzo.strucdocs.transpose;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransposeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void transposeWithSimpleChord() {
        Chord chordRequest = Chord.builder().note(Note.C).build();

        ResponseEntity<TransposeResponse> transposeResponseEntity =
            restTemplate.postForEntity("/",
                TransposeRequest.builder().chord(chordRequest).key(-1).build(),
                TransposeResponse.class);

        assertThat(transposeResponseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(transposeResponseEntity.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));

        TransposeResponse transposeResponse = transposeResponseEntity.getBody();
        assertThat(transposeResponse, notNullValue());
        assertThat(transposeResponse.getChord().toString(), equalTo("B"));
    }

    @Test
    public void transposeWithoutKey() {
        Chord chordRequest = Chord.builder().note(Note.C).build();

        ResponseEntity<TransposeResponse> transposeResponseEntity =
            restTemplate.postForEntity("/",
                TransposeRequest.builder().chord(chordRequest).build(),
                TransposeResponse.class);

        assertThat(transposeResponseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(transposeResponseEntity.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));

        TransposeResponse transposeResponse = transposeResponseEntity.getBody();
        assertThat(transposeResponse.getChord(), equalTo(transposeResponse.getOriginalChord()));
        assertThat(transposeResponse.getKey(), equalTo(0));
    }

    @Test
    public void transposeComplexChord() {
        Chord chordRequest = Chord.builder()
            .note(Note.C)
            .groundNote(Note.G)
            .addition(Interval.major)
            .addition(Interval.seventh)
            .addition(Interval.dim5)
            .build();

        ResponseEntity<TransposeResponse> transposeResponseEntity =
            restTemplate.postForEntity("/",
                TransposeRequest.builder().chord(chordRequest).key(2).build(),
                TransposeResponse.class);

        assertThat(transposeResponseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(transposeResponseEntity.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));
        assertThat(transposeResponseEntity.getBody().getChord().toString(), equalTo("Dmaj7b5/A"));
        assertThat(transposeResponseEntity.getBody().getOriginalChord().toString(), equalTo("Cmaj7b5/G"));
        assertThat(transposeResponseEntity.getBody().getKey(), equalTo(2));
    }

    @Test
    public void transposeNoChord() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<String> exchange =
            restTemplate.exchange("/", HttpMethod.POST, new HttpEntity<>("{}", headers), String.class);

        assertThat(exchange.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void transposeInvalidChord() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<String> exchange =
            restTemplate.exchange("/", HttpMethod.POST, new HttpEntity<>("{\"chord\": \"J\"}", headers), String.class);

        assertThat(exchange.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }
}
