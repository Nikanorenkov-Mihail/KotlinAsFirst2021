@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import kotlin.math.*

// Урок 3: циклы
// Максимальное количество баллов = 9
// Рекомендуемое количество баллов = 7
// Вместе с предыдущими уроками = 16/21

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая (2 балла)
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var sum: Int = 0
    var temp: Int = n
    do {
        temp /= 10
        sum++
    } while (temp > 0)
    return sum
}

/**
 * Простая (2 балла)
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    if (n == 1 || n == 2) return 1
    else return fib(n - 1) + fib(n - 2)
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    for (i in 2..n) {
        if (n % i == 0) return i
    }
    return n
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    for (i in (n / 2) downTo 1) {
        if (n % i == 0) return i
    }
    return 0
}

/**
 * Простая (2 балла)
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var n: Int = x
    var sum: Int = 0
    while (n != 1) {
        if (n % 2 == 0) n /= 2
        else n = n * 3 + 1
        sum++
    }
    return sum
}

/**
 * Средняя (3 балла)
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    for (i in min(m, n)..max(n, m) * min(m, n)) {
        if (i % m == 0 && i % n == 0) return i
    }
    return 0
}

/**
 * Средняя (3 балла)
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    for (i in 2..min(m, n)) {
        if (m % i == 0 && n % i == 0) return false
    }
    return true
}

/**
 * Средняя (3 балла)
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var r: Int = n
    var temp: Int = 0
    while (r > 0) {
        temp *= 10
        temp += (r % 10)
        r /= 10
    }
    return temp
}

/**
 * Средняя (3 балла)
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun step(n: Int, t: Int): Int {
    var r: Int = n
    for (i in 2..t) {
        r *= n
    }
    return r
}

fun isPalindrome(n: Int): Boolean {
    if (n < 10) return true
    var temp: Int = n
    var ch: Int = 0
    while (temp > 0) {
        temp /= 10
        ch++
    }
    var m: Int = n % step(10, ch / 2)
    if (ch % 2 == 0) ch /= 2
    else ch = ch / 2 + 1
    var l: Int = n / step(10, ch)
    return (m == revert(l))

}

/**
 * Средняя (3 балла)
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    if (n < 10) return false
    var temp: Int = n
    var r: Int = 0
    r = temp % 10
    temp /= 10
    while (temp > 0) {
        if (temp % 10 != r) return true
        temp /= 10
    }
    return false
}

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun StDouble(n: Double, t: Int): Double {
    var r: Double = n
    for (i in 2..t) {
        r *= n
    }
    return r
}

fun chlen(x: Double, n: Int): Double {
    print("||  ${StDouble(x, n)}  ||      ")
    print("|| ${factorial(n)}  ||     ")
    print("||  ${StDouble(x, n) / factorial(n)}  ||     ")
    //  println(" ")
    return (StDouble(x, n) / factorial(n))
}

fun sin(x: Double, eps: Double): Double {
    var result: Double = x
    if (x > 20 * PI) return 0.0 //после этого числа не хватает типа дабл для того, чтобы последний член в формуле стало меньше eps
    var tp: Int = 3

    var minus: Double = -1.0
    var cl: Double = 0.0

    do {
        cl = chlen(x, tp)
        result += minus * cl
        minus *= -1.0
        tp += 2
    } while (abs(cl) >= eps)

    return result
}

/*fun main() {
    var x: Double = 21 * PI
    var eps: Double = 1e-5
    var result: Double = x

    var tp: Int = 3

    var minus: Double = -1.0
    var chl: Double = 0.0

    var cl: Double
    do {
        cl = chlen(x, tp)
        result += minus * cl

        println(" ||||||||$eps           $result   $tp")
        minus *= -1.0
        tp += 2
    } while (abs(cl) >= eps)
    println("$eps       $result")
    println(result)
    if (result < 0.0 || result > 1.0) println("tent")
}*/

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */

fun cos(x: Double, eps: Double): Double {
    var result: Double = 1.0
    if (x > 20 * PI) return 1.0 //после этого числа не хватает типа дабл для того, чтобы последний член в формуле стало меньше eps
    var tp: Int = 2

    var minus: Double = -1.0
    var cl: Double = 0.0

    do {
        cl = chlen(x, tp)
        result += minus * cl
        minus *= -1.0
        tp += 2
    } while (abs(cl) >= eps)

    return result
}

/**
 * Сложная (4 балла)
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */

fun quantityCounter(n: Int): Int {
    var temp: Int = n
    var counter: Int = 0
    do {
        temp /= 10
        counter++
    } while (temp > 0)
    return counter
}

fun squareSequenceDigit(n: Int): Int {
    var counter: Int = 0

    var k: Int = 1
    var last: Int = 0
    var reserv: Int = 0

    do {
        counter += quantityCounter(step(k, 2))
        last = step(k, 2)
        k++
    } while (counter < n)
    if (n<2)return last
    if(counter == n)return last%10
    if (counter - n <= quantityCounter(last)) {
        reserv = counter - n


        for (i in 1..reserv) {

            last /= 10
        }

        return last%10
    }
    else return 0

}

/*fun main(){
    var n :Int = 17
    var counter: Int = 0
    var Value: Int = 0
    var k: Int = 1
    var last: Int = 0
    var reserv: Int = 0

    do {
        counter += quantityCounter(step(k, 2))
        last = step(k, 2)
        k++
    } while (counter < n)
    println("$counter      $n     $last")
    if (n<2) {
        println(last)
        println("----------------------")
    }
    else if(counter == n) {
        println(last%10)
        println("----------------------")
    }
   else if (counter - n <= quantityCounter(last)) {
        reserv = counter - n
        //last = revert(last)
        var LastNumber: Int = 0
        for (i in 1..reserv) {
            LastNumber = last % 10
            print("___$LastNumber ___")
            last /= 10
            print("---$last ---     ")
        }
        //println(LastNumber)
        println(last%10)
        println("----------------------")
    }



}
*/


/**
 * Сложная (5 баллов)
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var counter: Int = 0

    var k: Int = 1
    var last: Int = 0
    var reserv: Int = 0

    do {
        counter += quantityCounter(fib(k))
        last = fib(k)
        k++
    } while (counter < n)
    if (n<2)return last
    if(counter == n)return last%10
    if (counter - n <= quantityCounter(last)) {
        reserv = counter - n


        for (i in 1..reserv) {

            last /= 10
        }

        return last%10
    }
    else return 0
}
