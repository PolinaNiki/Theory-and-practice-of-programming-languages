class UltimateHashMap<Key : Comparable<Key>, Value> : HashMap<Key, Value>() {
    val iloc = IndexSearch()
    val ploc = ConditionSearch()

    // Поиск по условию
    inner class ConditionSearch {

        // Метод получения значения через ploc[]
        operator fun get(condition: String): HashMap<Key, Value> {
            val conditions: MutableList<Pair<String, Int>> = emptyList<Pair<String, Int>>().toMutableList()
            val results: HashMap<Key, Value> = HashMap()

            try {
                // Получаем список условий для чисел
                for (rx in Regex(
                        pattern = """((>=|<>|<=|>|<|=)(\d+))"""
                ).findAll(input = condition)) {
                    val (_, symbol, number) = rx.destructured
                    conditions.add(Pair(symbol, Integer.valueOf(number)))
                }

                // Если есть хотя-бы одно правильное условие
                if (conditions.isNotEmpty()) {

                    // Перебираем элементы
                    element_check@ for (element in this@UltimateHashMap) {

                        // Проверяем ключ на правильность
                        val regexGlobal = Regex(
                                pattern = """^([(\[{]?(((-?\d+)([^\d\n]+))*(-?\d+))[)\]}]?)${'$'}"""
                        ).find(input = element.key.toString())

                        if (regexGlobal != null) {

                            // Получаем условия без ограничивающих скобок
                            val regexFormatted = regexGlobal.groups[2]?.value

                            if (regexFormatted != null) {

                                // Получаем числовые значения
                                val regexNumbers = Regex(
                                        pattern = """-?\d+"""
                                ).findAll(input = regexFormatted)

                                // Проверка соответствия кол-ва ключей кол-ву условий
                                if (regexNumbers.count() == conditions.size) {

                                    // Получаем список разделителей
                                    val regexDividers = Regex(
                                            pattern = """[^(\-?\d)]+"""
                                    ).findAll(input = regexFormatted)
                                    var isDividersSame = true

                                    // Проверяем, что разделители одинаковые
                                    var regexDividersUnique: String? = null
                                    same_check@ for (divider in regexDividers) {
                                        val d = divider.groups[0]?.value
                                        if (d != null) {
                                            if (regexDividersUnique == null) {
                                                regexDividersUnique = d
                                            } else {
                                                if (regexDividersUnique != d) {
                                                    isDividersSame = false
                                                    break@same_check
                                                }
                                            }
                                        } else {
                                            isDividersSame = false
                                            break@same_check
                                        }
                                    }

                                    // Переход к сравнению ключа с условиями
                                    if (isDividersSame) {
                                        if (compareNumCond(regexNumbers, conditions)) {
                                            results[element.key] = element.value
//                                    } else {
//                                        // ПРОПУСК: Ключ не подошёл под условия
                                        }
                                    } else {
                                        // УВЕДОМЛЕНИЕ: У ключа разные разделители между числами
                                        throw Warning("Key isn't suitable for UltimateHashMap format")
                                    }
//                            } else {
//                                // ПРОПУСК: Кол-во ключей не равно кол-ву условий
                                }
//                        } else {
//                            // ПРОПУСК: Ключ не числовой
                            }
//                    } else {
//                        // ПРОПУСК: Ключ не числовой
                        }
                    }
                } else {
                    // ИСКЛЮЧЕНИЕ: Условия не заданы или заданы неправильно
                    throw Exception("Conditions have wrong format")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } catch (w: Warning) {
            }
            return results
        }

        private fun compareNumCond(regexNumbers: Sequence<MatchResult>, conditions: MutableList<Pair<String, Int>>): Boolean {
            var result = true
            for ((index, number) in regexNumbers.withIndex()) {
                val key = Integer.valueOf(number.value)
                val cond = conditions[index].second
                when (conditions[index].first) {
                    "==" -> {
                        result = result && (key == cond)
                    }
                    "<>" -> {
                        result = result && (key != cond)
                    }
                    ">" -> {
                        result = result && (key > cond)
                    }
                    "<" -> {
                        result = result && (key < cond)
                    }
                    ">=" -> {
                        result = result && (key >= cond)
                    }
                    "<=" -> {
                        result = result && (key <= cond)
                    }
                }

                if (!result) break
            }

            return result
        }
    }

    inner class IndexSearch {
        operator fun get(index: Int): Value? {
            return try {
                if (index >= 0 && index < this@UltimateHashMap.size) {
                    this@UltimateHashMap.toSortedMap().values.toList()[index]
                } else {
                    throw Exception("Wrong index format in UltimateHashMap.iloc()")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    class Exception(override val message: String?) : kotlin.Throwable()
    class Warning(override val message: String?) : kotlin.Throwable()
}