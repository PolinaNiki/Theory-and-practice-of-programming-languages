import java.lang.StringBuilder

fun isNumber(s: String): Boolean {
    if (s.isEmpty()) return false
    for (symbol in s) {
        if (!symbol.isDigit()) {
            return false
        }
    }
    return true
}

fun main(args: Array<String>) {

    print("Please input expression= ")
    val answer: String? = readLine()?.trim()
    val ops = arrayOf("+", "-", "*", "/")
    if (!answer.isNullOrEmpty()) {
        val parts = answer.split(' ')
        val stack = mutableListOf<String>()
        var op_diff = 0
        if (!parts.isNullOrEmpty()) {
            for (part in parts.reversed()) {
                if (isNumber(part)) {
                    stack.add(0, part)
                    op_diff += 1;
                } else if (part in ops) {
                    op_diff -= 1;
                    if (stack.size >= 2) {
                        val operation = part
                        val first = stack.first()
                        stack.removeAt(0)
                        val second = stack.first()
                        stack.removeAt(0)
                        val string = "(" + first + operation + second + ")"
                        stack.add(0, string)
                    } else {
                        stack.add(stack.lastIndex, part)
                    }

                } else {
                    print("Wrong exp! Symbols are not recognized")
                    return
                }
            }
        }

        if (op_diff < 1) {
            println("Not enough operands!")
        } else if (op_diff > 1){
            println("Not enough operators!")
        } else {
            if (stack.isEmpty()) {
                println("Stack is empty!")
            } else {
                println("Result of exp: ${stack.joinToString(separator = "")} ")
            }
        }

    } else {
        print("Expression is empty!")
    }
}