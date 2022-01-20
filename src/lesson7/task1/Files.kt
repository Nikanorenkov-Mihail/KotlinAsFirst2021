@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import ru.spbstu.wheels.Break
import java.io.File
import java.io.IOException
import java.io.PrintStream
import kotlin.math.pow
import kotlin.math.sqrt

// Урок 7: работа с файлами
// Урок интегральный, поэтому его задачи имеют сильно увеличенную стоимость
// Максимальное количество баллов = 55
// Рекомендуемое количество баллов = 20
// Вместе с предыдущими уроками (пять лучших, 3-7) = 55/103

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var currentLineLength = 0
    fun append(word: String) {
        if (currentLineLength > 0) {
            if (word.length + currentLineLength >= lineLength) {
                writer.newLine()
                currentLineLength = 0
            } else {
                writer.write(" ")
                currentLineLength++
            }
        }
        writer.write(word)
        currentLineLength += word.length
    }
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            writer.newLine()
            if (currentLineLength > 0) {
                writer.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(Regex("\\s+"))) {
            append(word)
        }
    }
    writer.close()
}

/**
 * Простая (8 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Некоторые его строки помечены на удаление первым символом _ (подчёркивание).
 * Перенести в выходной файл с именем outputName все строки входного файла, убрав при этом помеченные на удаление.
 * Все остальные строки должны быть перенесены без изменений, включая пустые строки.
 * Подчёркивание в середине и/или в конце строк значения не имеет.
 */
fun deleteMarked(inputName: String, outputName: String) {
    val input = File(inputName).readText()
    val res = StringBuilder()
    input.trimIndent().lines().map { nowLine ->
        if (nowLine.isEmpty() || nowLine[0] != '_') res.appendLine(nowLine)
        /*else {
            if (nowLine[0] != '_') res.appendLine(nowLine)
            else 0
        }*/
    }

    File(outputName).writeText(res.toString())
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> = TODO()


/**
 * Средняя (12 баллов)
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val lst = listOf('ж', 'ч', 'ш', 'щ', 'Ж', 'Ч', 'Ш', 'Щ')
    val alfavit = mapOf('ы' to 'и', 'Ы' to 'И', 'я' to 'а', 'Я' to 'А', 'ю' to 'у', 'Ю' to 'У')
    val error = Regex("""[жчшщЖЧШЩ][ыюяЫЮЯ]""")
    val result = StringBuilder()
    var last = '0'
    File(inputName).forEachLine { it ->
        if (error.containsMatchIn(it)) {
            for (i in it) {
                if (last in lst && i in alfavit) result.append(alfavit[i])
                else result.append(i)
                last = i
            }
            result.appendLine()
        } else result.appendLine(it)
    }
    File(outputName).writeText(result.toString())

}

/**
 * Средняя (15 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val input = File(inputName).readText()
    val longest = input.lines().maxOf { it.trim().length }
    val res = StringBuilder()
    input.lines().map { it.trim() }.forEach { nowLine ->
        val spaces = (longest - nowLine.length) / 2 // пробелы с 2х сторон
        val resultWithSpaces = " ".repeat(spaces) + nowLine
        res.appendLine(resultWithSpaces)
    }

    File(outputName).writeText(res.toString())
}

/**
 * Сложная (20 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 * Вернуть ассоциативный массив с числом слов больше 20, если 20-е, 21-е, ..., последнее слова
 * имеют одинаковое количество вхождений (см. также тест файла input/onegin.txt).
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    TODO()
    /* val input = inputName.lowercase()
     val result = mutableMapOf<String, Int>()
     val error = Regex("""[^а-яА-Я]""")
     return result.toMap()*/
}

/**
 * Средняя (14 баллов)
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная (22 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная (23 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body><p>...</p></body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<p>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>Или колбаса</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>Фрукты
<ol>
<li>Бананы</li>
<li>Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</p>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная (30 баллов)
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная (25 баллов)
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}
/*
fun main() {
    var max = 0
    val days = "Апрель 9..15"
    var arr = arrayOf("Март", "Апрель", "Май")
    val n = days.split(" ")
    if (n[0] in arr) {
        //if (!Regex("""\w+ \d+\.\.\d+""").matches(days)) throw IllegalArgumentException()
    } else throw IllegalArgumentException()
    val spl = n[1].split("..")
    val spl1 = spl[0]
    val spl2 = spl[1]

    val month = File("top20.txt").readText()
    month.lines().map { s ->
        s.split(" ").map {
            if (it == n[0]) {
                println(":)")
            } else if (it >= spl1 && it <= spl2) {
                if (it.toInt() > max) {
                    max = it.toInt()
                }
            }
        }
    }
    println(max)
}
*/

//максимальное количество осадков
/**
 * В файле с именем inputName заданы ежедневные сведения о
 * количестве выпавших осадков (в мм) в различные месяцы года,
 * всего не более чем 31 значение в каждой строке и
 * не более 12 строк во всём файле, например:
 *
 * Март 0 1 0 3 41 2 0 0 13 16 20 8 0 4 8 1 0 0 0 7 12 0 4 9
 * Апрель 0 0 0 17 0 0 11 48 42 0 0 1 7 15 18 0 0 0 0 0 8 2 17 0
 * Май 10 15 48 21 0 0 17 22 30 0 0 13 0 0 2 5 7 0 0 0 1 10 3
 *
 * Каждая строка начинается с названия месяца, за которым
 * следует последовательность целых чисел - уровень осадков в мм
 * в различные дни этого месяца, начиная с 1-го. Порядок месяцев
 * в файле должен соответствовать реальному (следующий месяц всегда
 * ниже предыдущего).
 *
 * В строковом параметре days задан интервал дат
 * либо в формате “Апрель 9..15”  (дни в одном месяце),
 * либо в формате “Март 22..Май 8” (дни в разных месяцах).
 *
 * Необходимо рассчитать максимальный уровень осадков за один день
 * в заданном интервале дат. Например, для “Апрель 9..15” это 42,
 * для “Март 22..Май 8” это 48. Отсутствующие дни игнорировать.
 *
 * “Удовлетворительно” -- используется только первый формат для
 * параметра days - все дни в одном месяце
 *
 * “Хорошо” -- может использоваться как первый, так и второй
 * формат для параметра days, то есть, интервал может содержать
 * дни в разных месяцах
 *
 * “Отлично” -- результат функции должен содержать не только
 * максимальный уровень осадков, но и список дней,
 * в которых он был достигнут
 * (42, 9 апреля или 48, 8 апреля, 3 мая для примеров выше)
 *
 * При нарушении форматов входных данных следует выбрасывать
 * исключение IllegalArgumentException. При невозможности
 * прочитать файл выбрасывать исключение IOException.
 *
 * Предложить имя и тип результата функции. Кроме функции
 * следует написать тесты, подтверждающие её работоспособность.
 */
fun myFun(inputName: String, days: String): Any {
    var max = 0
    val a = Regex("""[а-яА-я]+ \d+..\d+""")
    val b = Regex("""[а-яА-я]+ \d+..[а-яА-я]+ \d+""")

    val days = "Апрель 9..15"
    val days2 = "Март 22..Май 8"
    val arr = arrayOf("Март", "Апрель", "Май")
    val n = days.split(" ")
    val spl = n[1].split("..")
    val spl1 = spl[0].toInt()
    val spl2 = spl[1].toInt()
    try {
        val month = File(inputName).readText()
        var counter = 1
        if (n[0] in arr) {
            if (a.matches(days)) {
                month.lines().map { s ->
                    val ng = s.split(" ")
                    if (ng[0] == n[0]) {
                        ng.map {
                            if ((counter in spl1..spl2) && counter != 1) {
                                if (it.toInt() > max) {
                                    max = it.toInt()
                                }
                                counter++
                            } else counter++

                        }
                    }
                    counter = 0
                }
            } else if (b.matches(days2)) {
                month.lines().map { s ->
                    val ng = s.split(" ")
                    if (ng[0] == n[0]) {
                        ng.map {


                        }
                    }
                }
            } else throw IllegalArgumentException()
        } else throw IllegalArgumentException()
        return max
    } catch (readTextException: Exception) {
        throw IOException(":)")
    }

}


//Билет 7 - Семисегментный индикатор
/*
* Семисегментный светодиодный индикатор, как говорит его название,
* состоит из семи элементов индикации (сегментов),
* включающихся и выключающихся по отдельности. Включая их в разных
* комбинациях, из них можно составить упрощённые изображения арабских цифр.
* Сегменты обозначаются буквами от A до G;

* Например, цифра 0 кодируется последовательностью ABCDEF,
* а цифра 1 -- последовательностью BC
*
* В качестве результата необходимо вернуть:
*
* "Удовлетворительно" -- кодировку всех элементов промежутка времени в минутах,
* находящегося между tStart (00-59) и tEnd (00-59), например: tStart = 10, tEnd = 13,
* result: "BC ABCDEF, BC BC, BC ABDEG, BC ABCDG" (10, 11, 12, 13)
*
* "Хорошо" -- кодировку всех элементов промежутка времени в часах и минутах,
* находящегося между tStart и tEnd, на 4 индикаторах, например:
* tStart = 23:59, tEnd = 00:01, result: "ABDEG ABCDG:ACDFG ABCDFG,
* ABCDEF ABCDEF:ABCDEF ABCDEF, ABCDEF ABCDEF:ABCDEF BC" (23:59, 00:00, 00:01)
*
* "Отлично" -- дополнительно создать файл out.txt,
* в котором нарисован каждый элемент из временного промежутка,
* находящегося между tStart и tEnd, заданных параметрами,
* например: tStart = 16:59, tEnd = 17:00. Файл out.txt:
*    _     _   _
* | |_  . |_  |_|
* | |_| .  _|  _|
*    _     _   _
* | | | . | | | |
* |   | . |_| |_|
* Предложить имя, тип параметров, тип результата функции. Кроме функции
* следует написать тесты, подтверждающие её работоспособность.*/
fun foo(tStart: Any, tEnd: Any): Any {
    val mapa = mapOf(
        0 to "ABCDEF",
        1 to "BC",
        2 to "ABDEG",
        3 to "ABCDG",
        4 to "BCFG",
        5 to "ACDF",
        6 to "ACDFG",
        7 to "ABCF",
        8 to "ABCDEFG",
        9 to "ABCDFG"
    )
    val striing = StringBuilder()
    var counter = 0
    val result = mutableListOf<String>()
    val lst = mutableListOf<Int>(0, 0)
    for (i in tStart.toString().toInt()..tEnd.toString().toInt()) {
        var t = i.toInt()
        while (t > 0) {
            lst[counter] = t % 10

            t /= 10
            counter++
        }

        result.add("${mapa[lst[1]]} ${mapa[lst[0]]}")
        striing.append("${mapa[lst[1]]} ${mapa[lst[0]]}, ")
        lst[0] = 0
        lst[1] = 0
        counter = 0
    }
    println(striing.dropLast(2))
    return striing.dropLast(2)
}


//Билет 9 -- квартиры
/**
 * В файле с именем inputName заданы описания квартир,
 * предлагающихся для продажи, в следующем формате:
 *
 * Пионерская 9-17: комната 18, комната 14, кухня 7, коридор 4
 * Школьная 12-14: комната 19, кухня 8, коридор 3
 * Садовая 19-1-55: комната 12, комната 19, кухня 9, коридор 5
 * Железнодорожная 3-6: комната 21, кухня 6, коридор 4
 *
 * Строчка начинается с адреса квартиры, после двоеточия
 * перечисляются помещения квартиры через запятую, с указанием
 * их площади.
 *
 * Параметр query содержит запрос, начинающийся с названия
 * помещения, за которым следует его минимальная площадь,
 * например, “кухня 8”. Через точку с запятой могут следовать
 * другие ограничения, например “кухня 8; коридор 4”
 * Функция должна найти все квартиры в списке,
 * удовлетворяющие запросу (площадь кухни больше или равна 8,
 * площадь коридора больше или равна 4)
 *
 * “Удовлетворительно” -- в запросе может присутствовать только
 * одно помещение, например, “кухня 8”
 *
 * “Хорошо” -- в запросе может присутствовать несколько помещений,
 * например, “кухня 8; комната 15”
 *
 * “Отлично” -- в запросе может присутствовать два и более
 * однотипных помещения, например, “комната 19; комната 12” --
 * двухкомнатная квартира,
 * одна комната не менее 19, другая не менее 12
 *
 * При нарушении форматов входных данных следует выбрасывать
 * исключение IllegalArgumentException, при невозможности
 * прочитать файл выбрасывать исключение IOException.
 *
 * Предложить имя и тип результата функции. Кроме функции
 * следует написать тесты, подтверждающие её работоспособность.
 */
fun fo(inputName: String, query: String): Any {
    val result = StringBuilder()
    var stoper = false
    val query1 = query.split(" ")
    val enter = File(inputName).readText()
    enter.lines().map { line ->
        val kvart = line.split(":")
        kvart.map { kv ->
            val komnata = kv.split(",")

            for (i in komnata) {
                try {
                    i.toInt()
                } catch (NumberFormatExceptions: Exception) {
                    if (query1[0] == i) {
                        if ((i + 1).toInt() >= query1[1].toInt()) {
                            result.appendLine(line)
                            stoper = true
                        }
                    }
                }

            }
            if (stoper) {
                Break
                stoper = false
            }
            println("$stoper      $kv")
        }

    }
    return 0
}

fun fo1(inputName: String, query: String): Any {
    val result = StringBuilder()
    var stoper = false
    val query1 = query.split(" ")
    val enter = File(inputName).readText()
    enter.lines().map { line ->
        val ln = line.split(":")
        ln[1].split(",").map { it ->
            var counter = 0
            val rume = it.drop(1).split(" ")
            if (rume[0] == query1[0] && rume[1] >= query1[1]) stoper = true
        }
        if (stoper) result.appendLine(line)
        stoper = false
    }
    return result.dropLast(1)
}

fun main() {
    val i = "123"
    try {
        i.toInt()
    } catch (NumberFormatExceptions: Exception) {
        println(123)
    }
}
