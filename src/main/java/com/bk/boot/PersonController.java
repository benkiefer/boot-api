package com.bk.boot;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {
    private List<Person> people = new ArrayList<>();
    @PostConstruct
    public void init() {
        people.add(new Person("Ben", "Kiefer"));
    }

    @RequestMapping("/person")
    public @ResponseBody
    List<Person> people() {
        return people;
    }

    @RequestMapping("/person/{first}/{last}")
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