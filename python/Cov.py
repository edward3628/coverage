class Cov:
    def __init__(self, eff, term):
        self.eff = eff
        self.term = term

    def __str__(self):
        return "Cov({}, {})".format(self.eff, self.term)

    def __getitem__(self, eff):
        return self.eff


class Result:
    def __init__(self, cov, list):
        self.cov = cov
        self.list = list


def getContCov(coverages):
    contList = []
    tmpList = []
    coverages.sort(key=lambda i: i[0])

    for i in range(len(coverages)):
        aList = []
        start = coverages[i]
        aList.append(start)
        startVal = start.eff
        termVal = start.term
        for j in range(i + 1, len(coverages)):
            next = coverages[j]
            if (termVal + 1) == next.eff:
                aList.append(coverages[j])
                termVal = next.term
            elif termVal >= next.eff:
                if termVal < next.term:
                    termVal = next.term
                aList.append(coverages[j])
            else:
                break
        tmpList.append(Cov(startVal, termVal))
        contList.append(aList)

    index = 0
    max = 0
    for i in range(len(tmpList)):
        diff = tmpList[i].term - tmpList[i].eff
        if diff > max:
            max = diff
            index = i

    return Result(tmpList[index], contList[index])


def test(inputList, num):
    print "Scenario {}: a series of coverage data input:  ".format(num)
    print " ".join(map(str, inputList))
    result = getContCov(inputList)
    print("Longest Continuous Coverage List: ")
    print " ".join(map(str, result.list))
    print("Consolidated Longest Continuous Coverage : {}".format(result.cov))


def main():
    inputList = [Cov(1, 20), Cov(21, 30), Cov(15, 25), Cov(28, 40), Cov(50, 60), Cov(61, 200)]
    test(inputList, 1)

    inputList = [Cov(1, 20), Cov(21, 30), Cov(15, 25), Cov(28, 40), Cov(50, 60), Cov(61, 80)]
    test(inputList, 2)


if __name__ == "__main__":
    main()
