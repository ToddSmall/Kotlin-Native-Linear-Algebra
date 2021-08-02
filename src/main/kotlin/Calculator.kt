class Calculator {
    fun parse(s: String): Int {
        val (left, op, right) = s.split(" ")
        return when (op) {
            "*" -> left.toInt() * right.toInt()
            else -> throw IllegalArgumentException("Invalid operator")
        }
    }
}
