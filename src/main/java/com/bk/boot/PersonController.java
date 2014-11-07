package com.bk.boot;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class PersonController {
    private List<Person> people = new ArrayList<>();

    @PostConstruct
    public void init() {
        people.add(new Person("Ben", "Kiefer"));
    }

    @RequestMapping(value = "/person", method = GET)
    public @ResponseBody List<Person> people() {
        return people;
    }

    @RequestMapping(value = "/person/{first}/{last}", method = GET)
    public @ResponseBody Person person(@PathVariable String first, @PathVariable String last) {
        return findBy(first, last).orElse(add(first, last));
    }

    private Person add(String first, String last) {
        Person person = new Person(first, last);
        people.add(person);
        return person;
    }

    private Optional<Person> findBy(String first, String last) {
        return people.stream()
                .filter(x ->
                        first.equalsIgnoreCase(x.getFirstName())
                                && last.equalsIgnoreCase(x.getLastName()))
                .findFirst();
    }

}