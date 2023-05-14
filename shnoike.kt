import java.util.*

fun main() {
    val fieldSize = 15
    val game = SnakeGame(fieldSize)
    game.play()
}

class SnakeGame(private val fieldSize: Int) {
    private val scanner = Scanner(System.`in`)
    private var score = 0
    private var gameOver = false
    private val snake = LinkedList<Pair<Int, Int>>()
    private var food = Pair(0, 0)
    private var direction = Pair(0, 1)
    private val random = Random()

    init {
        val startX = fieldSize / 2
        val startY = fieldSize / 2
        snake.add(Pair(startX, startY))
        placeFood()
    }

    fun play() {
        while (!gameOver) {
            printField()
            moveSnake()
            if (score >= 50) {
                println("Congratulations, you win!")
                gameOver = true
            }
        }
    }

    private fun moveSnake() {
        val newHead = Pair(snake.first().first + direction.first, snake.first().second + direction.second)
        if (newHead.first < 0 || newHead.first >= fieldSize || newHead.second < 0 || newHead.second >= fieldSize) {
            println("Game over! You hit the wall.")
            gameOver = true
        } else if (snake.contains(newHead)) {
            println("Game over! You ran into yourself.")
            gameOver = true
        } else if (newHead == food) {
            score++
            snake.addFirst(newHead)
            placeFood()
        } else {
            snake.addFirst(newHead)
            snake.removeLast()
        }
    }

    private fun placeFood() {
        do {
            food = Pair(random.nextInt(fieldSize), random.nextInt(fieldSize))
        } while (snake.contains(food))
    }

    private fun printField() {
        println("Score: $score")
        val field = Array(fieldSize) { CharArray(fieldSize) { ' ' } }
        snake.forEachIndexed { index, pair ->
            field[pair.second][pair.first] = if (index == 0) '@' else '#'
        }
        field[food.second][food.first] = '*'
        field.forEach {
            println(it.joinToString(" "))
        }
    }

    private fun readDirection() {
        when (scanner.nextLine().toLowerCase()) {
            "w" -> direction = Pair(-1, 0)
            "s" -> direction = Pair(1, 0)
            "a" -> direction = Pair(0, -1)
            "d" -> direction = Pair(0, 1)
        }
    }
}
