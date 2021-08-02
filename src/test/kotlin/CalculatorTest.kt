import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CalculatorTest {
    @Test
    internal fun testMultiply2By2() {
        val calculator = Calculator()
        val result = calculator.parse("2 * 2")
        assertEquals(4, result)
    }

    @Test
    internal fun testMultiply2By3() {
        val calculator = Calculator()
        val result = calculator.parse("2 * 3")
        assertEquals(6, result)
    }
}