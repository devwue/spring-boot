package com.devwue.spring.api

import spock.lang.Specification

class TestCase extends Specification{
    def "기본"() {
        given:
        List<String> list = Stub();

        when:
        list.size() >> 1

        then:
        1 == list.size()
    }

    def "데이터 주입"(int a, int b, int expected) {
        expect:
        expected == a + b

        where:
        a | b | expected
        1 | 2 | 3
        2 | 3 | 5
    }
}
