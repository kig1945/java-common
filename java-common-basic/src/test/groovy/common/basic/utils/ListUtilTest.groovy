package common.basic.utils

import spock.lang.Specification

class ListUtilTest extends Specification {
    def "ctor"() {
        when: new ListUtil()
        then: thrown(InstantiationException)
    }

    static class NoToString {
        final int i;
        final String s;

        NoToString(int i, String s) {
            this.i = i;
            this.s = s
        }
    }

    static class HasToString extends NoToString {
        HasToString(int i, String s) {
            super(i, s)
        }

        @Override
        public String toString() {
            return String.format("%d%s", i, s);
        }
    }

    def "isNull"() {
        expect:
        ListUtil.isNull(null) == []
        ListUtil.isNull([1, 2]) == [1, 2]
    }

    def "toListString"() {

        Object[] array = [null, 1, "A", new HasToString(20, "C")];
        List<String> list = ListUtil.toListString(array);

        expect:
        list == ["null", "1", "A", "20C"]
    }

    def "getIndexNext"() {
        expect:
        ListUtil.getIndexNext(size, i) == result

        where:
        size       | i      || result
        10         | 0      || 1
        10         | 5      || 6
        10         | 9      || 0
    }

    def "getIndexPrev"() {
        expect:
        ListUtil.getIndexPrev(size, i) == result

        where:
        size       | i      || result
        10         | 0      || 9
        10         | 5      || 4
        10         | 9      || 8
    }

    def "getByInfiniteIndex"() {
        expect:
        ListUtil.getByInfiniteIndex([1, 2, 3, 4, 5], i) == v

        where:
        i       | v
        -10     | 1
        -9      | 2
        -8      | 3
        -7      | 4
        -6      | 5
        -5      | 1
        -4      | 2
        -3      | 3
        -2      | 4
        -1      | 5
        0       | 1
        1       | 2
        2       | 3
        3       | 4
        4       | 5
        5       | 1
        6       | 2
        7       | 3
        8       | 4
        9       | 5
        10      | 1
        11      | 2
        12      | 3
        13      | 4
        14      | 5
        15      | 1
        16      | 2
        17      | 3
        18      | 4
        19      | 5
    }

    def "getByInfiniteIndexWithOffset"() {
        expect:
        ListUtil.getByInfiniteIndexWithOffset([1, 2, 3, 4, 5], i, 3) == v

        where:
        i       | v
        -10     | 4
        -9      | 5
        -8      | 1
        -7      | 2
        -6      | 3
        -5      | 4
        -4      | 5
        -3      | 1
        -2      | 2
        -1      | 3
        0       | 4
        1       | 5
        2       | 1
        3       | 2
        4       | 3
        5       | 4
        6       | 5
        7       | 1
        8       | 2
        9       | 3
        10      | 4
        11      | 5
        12      | 1
        13      | 2
        14      | 3
        15      | 4
        16      | 5
        17      | 1
        18      | 2
        19      | 3
    }

    def "Swap"() {
        def range = IntUtil.generateListRange(5)
        ListUtil.swap(range, i1, i2)

        expect:
        list == range

        where:
        list                      || i1 || i2
        [1, 0, 2, 3, 4]           || 0  || 1
        [0, 3, 2, 1, 4]           || 1  || 3
        [0, 4, 2, 3, 1]           || 4  || 1
        [4, 1, 2, 3, 0]           || 0  || 4
    }

    def "combine"() {
        expect:
        expect == ListUtil.combine(top, bottom)

        where:
        expect | top | bottom

        [1] | [1] | [1]

        [1, 2] | [1, 2] | [1, 2]
        [1, 2] | [1, 2] | [2]

        [1, 2] | [1] | [1, 2]
        [1, 2] | [1] | [2]

        [1, 2, 3] | [1, 2, 3] | [1, 2, 3]

        [1, 2, 3] | [1, 2] | [1, 2, 3]
        [1, 2, 3] | [1, 2] | [2, 3]
        [1, 2, 3] | [1, 2] | [3]

        [1, 2, 3] | [1] | [1, 2, 3]
        [1, 2, 3] | [1] | [2, 3]

        [1, 2, 3, 4] | [1, 2, 3, 4] | [1, 2, 3, 4]

        [1, 2, 3, 4] | [1, 2, 3] | [1, 2, 3, 4]
        [1, 2, 3, 4] | [1, 2, 3] | [2, 3, 4]
        [1, 2, 3, 4] | [1, 2, 3] | [3, 4]
        [1, 2, 3, 4] | [1, 2, 3] | [4]

        [1, 2, 3, 4] | [1, 2] | [1, 2, 3, 4]
        [1, 2, 3, 4] | [1, 2] | [2, 3, 4]
        [1, 2, 3, 4] | [1, 2] | [3, 4]

        [1, 2, 3, 4] | [1] | [1, 2, 3, 4]
        [1, 2, 3, 4] | [1] | [2, 3, 4]

        [1, 2, 3, 4, 5] | [1, 2, 3, 4, 5] | [1, 2, 3, 4, 5]
        [1, 2, 3, 4, 5] | [1, 2, 3, 4, 5] | [2, 3, 4, 5]
        [1, 2, 3, 4, 5] | [1, 2, 3, 4, 5] | [3, 4, 5]
        [1, 2, 3, 4, 5] | [1, 2, 3, 4, 5] | [4, 5]
        [1, 2, 3, 4, 5] | [1, 2, 3, 4, 5] | [5]

        [1, 2, 3, 4, 5] | [1, 2, 3, 4] | [1, 2, 3, 4, 5]
        [1, 2, 3, 4, 5] | [1, 2, 3, 4] | [2, 3, 4, 5]
        [1, 2, 3, 4, 5] | [1, 2, 3, 4] | [3, 4, 5]
        [1, 2, 3, 4, 5] | [1, 2, 3, 4] | [4, 5]
        [1, 2, 3, 4, 5] | [1, 2, 3, 4] | [5]

        [1, 2, 3, 4, 5] | [1, 2, 3] | [1, 2, 3, 4, 5]
        [1, 2, 3, 4, 5] | [1, 2, 3] | [2, 3, 4, 5]
        [1, 2, 3, 4, 5] | [1, 2, 3] | [3, 4, 5]
        [1, 2, 3, 4, 5] | [1, 2, 3] | [4, 5]

        [1, 2, 3, 4, 5] | [1, 2] | [1, 2, 3, 4, 5]
        [1, 2, 3, 4, 5] | [1, 2] | [2, 3, 4, 5]
        [1, 2, 3, 4, 5] | [1, 2] | [3, 4, 5]

        [1, 2, 3, 4, 5] | [1] | [1, 2, 3, 4, 5]
        [1, 2, 3, 4, 5] | [1] | [2, 3, 4, 5]


        [1, 1, 1, 1] | [1, 1, 1, 1] | [1, 1, 1, 1]
        [1, 1, 1, 1] | [1, 1, 1, 1] | [1, 1, 1]
        [1, 1, 1, 1] | [1, 1, 1, 1] | [1, 1]
        [1, 1, 1, 1] | [1, 1, 1, 1] | [1]

        [1, 1, 1, 1] | [1, 1, 1] | [1, 1, 1, 1]
        [1, 1, 1, 1] | [1, 1] | [1, 1, 1, 1]
        [1, 1, 1, 1] | [1] | [1, 1, 1, 1]

        [1, 2, 3, 4, 5, 1, 2, 3, 4] | [1, 2, 3, 4, 5] | [1, 2, 3, 4]

        ["A"] | ["A"] | ["A"]

        ["A", "B"] | ["A", "B"] | ["A", "B"]
        ["A", "B"] | ["A", "B"] | ["B"]

        ["A", "B"] | ["A"] | ["A", "B"]
        ["A", "B"] | ["A"] | ["B"]

        ["A", "B", "C"] | ["A", "B", "C"] | ["A", "B", "C"]

        ["A", "B", "C"] | ["A", "B"] | ["A", "B", "C"]
        ["A", "B", "C"] | ["A", "B"] | ["B", "C"]
        ["A", "B", "C"] | ["A", "B"] | ["C"]

        ["A", "B", "C"] | ["A"] | ["A", "B", "C"]
        ["A", "B", "C"] | ["A"] | ["B", "C"]

        ["A", "B", "C", "D"] | ["A", "B", "C", "D"] | ["A", "B", "C", "D"]

        ["A", "B", "C", "D"] | ["A", "B", "C"] | ["A", "B", "C", "D"]
        ["A", "B", "C", "D"] | ["A", "B", "C"] | ["B", "C", "D"]
        ["A", "B", "C", "D"] | ["A", "B", "C"] | ["C", "D"]
        ["A", "B", "C", "D"] | ["A", "B", "C"] | ["D"]

        ["A", "B", "C", "D"] | ["A", "B"] | ["A", "B", "C", "D"]
        ["A", "B", "C", "D"] | ["A", "B"] | ["B", "C", "D"]
        ["A", "B", "C", "D"] | ["A", "B"] | ["C", "D"]

        ["A", "B", "C", "D"] | ["A"] | ["A", "B", "C", "D"]
        ["A", "B", "C", "D"] | ["A"] | ["B", "C", "D"]

        ["A", "B", "C", "D", "E"] | ["A", "B", "C", "D", "E"] | ["A", "B", "C", "D", "E"]
        ["A", "B", "C", "D", "E"] | ["A", "B", "C", "D", "E"] | ["B", "C", "D", "E"]
        ["A", "B", "C", "D", "E"] | ["A", "B", "C", "D", "E"] | ["C", "D", "E"]
        ["A", "B", "C", "D", "E"] | ["A", "B", "C", "D", "E"] | ["D", "E"]
        ["A", "B", "C", "D", "E"] | ["A", "B", "C", "D", "E"] | ["E"]

        ["A", "B", "C", "D", "E"] | ["A", "B", "C", "D"] | ["A", "B", "C", "D", "E"]
        ["A", "B", "C", "D", "E"] | ["A", "B", "C", "D"] | ["B", "C", "D", "E"]
        ["A", "B", "C", "D", "E"] | ["A", "B", "C", "D"] | ["C", "D", "E"]
        ["A", "B", "C", "D", "E"] | ["A", "B", "C", "D"] | ["D", "E"]
        ["A", "B", "C", "D", "E"] | ["A", "B", "C", "D"] | ["E"]

        ["A", "B", "C", "D", "E"] | ["A", "B", "C"] | ["A", "B", "C", "D", "E"]
        ["A", "B", "C", "D", "E"] | ["A", "B", "C"] | ["B", "C", "D", "E"]
        ["A", "B", "C", "D", "E"] | ["A", "B", "C"] | ["C", "D", "E"]
        ["A", "B", "C", "D", "E"] | ["A", "B", "C"] | ["D", "E"]

        ["A", "B", "C", "D", "E"] | ["A", "B"] | ["A", "B", "C", "D", "E"]
        ["A", "B", "C", "D", "E"] | ["A", "B"] | ["B", "C", "D", "E"]
        ["A", "B", "C", "D", "E"] | ["A", "B"] | ["C", "D", "E"]

        ["A", "B", "C", "D", "E"] | ["A"] | ["A", "B", "C", "D", "E"]
        ["A", "B", "C", "D", "E"] | ["A"] | ["B", "C", "D", "E"]

        
        ["A", "A", "A", "A"] | ["A", "A", "A", "A"] | ["A", "A", "A", "A"]
        ["A", "A", "A", "A"] | ["A", "A", "A", "A"] | ["A", "A", "A"]
        ["A", "A", "A", "A"] | ["A", "A", "A", "A"] | ["A", "A"]
        ["A", "A", "A", "A"] | ["A", "A", "A", "A"] | ["A"]

        ["A", "A", "A", "A"] | ["A", "A", "A"] | ["A", "A", "A", "A"]
        ["A", "A", "A", "A"] | ["A", "A"] | ["A", "A", "A", "A"]
        ["A", "A", "A", "A"] | ["A"] | ["A", "A", "A", "A"]
    }

    def "removeFirstInPlace"() {
        def list = [1, 2, 3];
        ListUtil.removeFirstInPlace(list)

        expect:
        list == [2, 3]
    }


    def "removeLastInPlace"() {
        def list = [1, 2, 3];
        ListUtil.removeLastInPlace(list)

        expect:
        list == [1, 2]
    }
}
