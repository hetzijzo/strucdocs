package org.hetzijzo.strucdocs.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
class SampleAccountDataCLR implements CommandLineRunner {

    private final AccountRepository accountRepository;

    @Autowired
    public SampleAccountDataCLR(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        Stream.of("willem,strucdocs")
            .map(t -> t.split(","))
            .forEach(tuple ->
                accountRepository.save(Account.builder()
                    .username(tuple[0])
                    .password(tuple[1])
                    .active(true)
                    .build()
                )
            );
    }
}
