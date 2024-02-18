import org.junit.jupiter.api.Test

class MatrixTest {

    @Test
    fun testUp(){
        val matrix = Matrix(2, 2)
        matrix.goto(1,0)
        matrix.up()
        assert(matrix.pos().row == 0)
        matrix.up()
        assert(matrix.pos().row == 0)
    }

    @Test
    fun testDown(){
        val matrix = Matrix(2, 2)
        matrix.goto(0,0)
        matrix.down()
        assert(matrix.pos().row == 1)
        matrix.down()
        assert(matrix.pos().row == 1)
    }

    @Test
    fun testLeft(){
        val matrix = Matrix(2, 2)
        matrix.goto(0,1)
        matrix.left()
        assert(matrix.pos().col == 0)
        matrix.left()
        assert(matrix.pos().col == 0)
    }

    @Test
    fun testRight(){
        val matrix = Matrix(2, 2)
        matrix.goto(0,0)
        matrix.right()
        assert(matrix.pos().col == 1)
        matrix.right()
        assert(matrix.pos().col == 1)
    }

    @Test
    fun testAroundIn2x2(){
        val matrix = Matrix(2, 2)
        matrix.goto(0,0)
        var list = matrix.around()
        assert(list.size == 2)
        assert(list.find { it.location == "DOWN" } != null)
        assert(list.find { it.location == "RIGHT" } != null)

        matrix.goto(1,1)
        list = matrix.around()
        assert(list.size == 2)
        assert(list.find { it.location == "UP" } != null)
        assert(list.find { it.location == "LEFT" } != null)

        matrix.goto(0,1)
        list = matrix.around()
        assert(list.size == 2)
        assert(list.find { it.location == "DOWN" } != null)
        assert(list.find { it.location == "LEFT" } != null)

        matrix.goto(1,0)
        list = matrix.around()
        assert(list.size == 2)
        assert(list.find { it.location == "UP" } != null)
        assert(list.find { it.location == "RIGHT" } != null)
    }

    @Test
    fun testAroundIn3x3() {
        val matrix = Matrix(3, 3)
        matrix.goto(1, 1)
        var list = matrix.around()
        assert(list.size == 4)
        assert(list.find { it.location == "UP" } != null)
        assert(list.find { it.location == "DOWN" } != null)
        assert(list.find { it.location == "LEFT" } != null)
        assert(list.find { it.location == "RIGHT" } != null)
    }

    @Test
    fun testFind(){
        val matrix = Matrix(3, 3)
        val array2x2 = listOf("ab","cd")
        matrix.fill(array2x2)
        val elem = matrix.find("d")
        assert(elem != null)
        assert(elem?.row == 1)
        assert(elem?.col == 1)
    }

}