package common.basic.geometiries

import spock.lang.Specification

class LeftTopRightBottomTest extends Specification {

    @SuppressWarnings("GroovyPointlessBoolean")
    def "Equals"() {

        expect:
        ltrbLhs.equals(ltrbRhs) == result

        where:
        ltrbLhs                                     ||  ltrbRhs                                     ||  result
        new LeftTopRightBottom(10)                  ||  new LeftTopRightBottom(10)                  ||  true
        new LeftTopRightBottom(10)                  ||  new LeftTopRightBottom(10, 10, 10, 10)      ||  true
        new LeftTopRightBottom(10)                  ||  new LeftTopRightBottom(11)                  ||  false
        new LeftTopRightBottom(10)                  ||  new LeftTopRightBottom(10, 10, 20, 10)      ||  false

        new LeftTopRightBottom(22)                  ||  new LeftTopRightBottom(22)                  ||  true
        new LeftTopRightBottom(22)                  ||  new LeftTopRightBottom(22, 22, 22, 22)      ||  true
        new LeftTopRightBottom(22)                  ||  new LeftTopRightBottom(22, 11, 22, 22)      ||  false

        new LeftTopRightBottom(10)                  ||  new LeftTopRightBottom(10.0)                ||  false
        new LeftTopRightBottom(10)                  ||  new LeftTopRightBottom(10.0F)               ||  false
        new LeftTopRightBottom(10)                  ||  new LeftTopRightBottom(10L)                 ||  false
        new LeftTopRightBottom(10)                  ||  null                                        ||  false
        new LeftTopRightBottom(null)                ||  null                                        ||  false
        new LeftTopRightBottom(null)                ||  new LeftTopRightBottom(null)                ||  true
        new LeftTopRightBottom(10, 10, 10, null)    ||  new LeftTopRightBottom(10, 10, 10, null)    ||  true
    }

    def "HashCode"() {

        expect:
        ltrbLhs.hashCode() == result

        where:
        ltrbLhs                                     ||  result
        new LeftTopRightBottom(10)                  ||  307840
        new LeftTopRightBottom(10, 10, 10, 10)      ||  307840
        new LeftTopRightBottom(10, 15, 34, 87)      ||  313466
        new LeftTopRightBottom(10, 10, null, 10)    ||  307530
        new LeftTopRightBottom(null, 10, null, 10)  ||  9620
        new LeftTopRightBottom(null)                ||  0
        new LeftTopRightBottom(22)                  ||  677248
    }

    def "ToString"() {
        expect: true;
    }
}
