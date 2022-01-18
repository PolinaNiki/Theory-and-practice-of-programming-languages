fun main(args: Array<String>) {
    println("\nTEST 1: ")
    val tempMap1 = UltimateHashMap<String, Int>()

    tempMap1["value1"] = 1
    tempMap1["value2"] = 2
    tempMap1["value3"] = 3
    tempMap1["1"] = 10
    tempMap1["2"] = 20
    tempMap1["3"] = 30

    tempMap1["(1, 5)"] = 100
    tempMap1["(5, 5)"] = 200
    tempMap1["(10, 5)"] = 300
    tempMap1["(1, 5, 3)"] = 400
    tempMap1["(5, 5, 4)"] = 500
    tempMap1["(10, 5, 5)"] = 600

    println(tempMap1.ploc[">=1"]) // >>> {1=10, 2=20, 3=30}
    println(tempMap1.ploc["<3"]) // >>> {1=10, 2=20}

    println(tempMap1.ploc[">0, >0"]) // >>> {(1, 5)=100, (5, 5)=200, (10, 5)=300}
    println(tempMap1.ploc[">=10, >0"]) // >>> {(10, 5)=300}

    println(tempMap1.ploc["<5, >=5, >=3"]) // >>> {(1, 5, 3)=400}


    println("\nTEST 2: ")
    val tempMap2 = UltimateHashMap<String, Int>()

    tempMap2["HappyHolidays->1234->uWu"] = 100
    tempMap2["bracket800bracket"] = 200
    tempMap2["-100"] = 300
    tempMap2["100"] = 400
    tempMap2["value4321"] = 500
    tempMap2["{2}"] = 600
    tempMap2["value800"] = 700

    tempMap2["[1, 1, 1]"] = 10
    tempMap2["{2, 2, 2}"] = 20
    tempMap2["(3, 3, 3)"] = 30
    tempMap2["/4, 4/"] = 40
    tempMap2["(5; 5)"] = 50

    println(tempMap2.ploc["<5"])

    println(tempMap2.ploc["==5 ¯\\_(ツ)_/¯ ==5"])

    println(tempMap2.ploc[">0 DIVIDER >0 DIVIDER >0"])
}