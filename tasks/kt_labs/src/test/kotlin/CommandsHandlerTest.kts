import com.xenomachina.argparser.ArgParser
import org.junit.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandsHandlerTest {

    private val S = File.separator

    /**
     * -nr A 'Int' B 'Int' "Regex" "File/Dir"
     */
    val RESOURCES: String = "src/test/resources"
    val DIR: String = "$RESOURCES/dir"
    val DIR2: String = "$DIR/dir2"
    val DIR3: String = "$DIR2/dir3" //not exist
    val KISH: String = "$DIR2/KISH.txt"
    val MOMENTS: String = "$RESOURCES/Moments"
    val DDT: String = "$DIR/DDT"

    @ParameterizedTest
    @MethodSource("filesProvider")
    fun getFilesTest(path: String, expected: List<File>) {
        Assert.assertEquals(expected, getFiles(File(path)))
    }

    fun filesProvider() = listOf(
        Arguments.of(
            RESOURCES,
            listOf(
                File(MOMENTS),
                File(DDT),
                File(KISH)
            )
        ),
        Arguments.of(
            DIR,
            listOf(
                File(DDT),
                File(KISH)
            )
        ),
        Arguments.of(
            DIR2,
            listOf(
                File(KISH)
            )
        ),
        Arguments.of(
            DIR3,
            emptyList<File>()
        ),
        Arguments.of(
            KISH,
            listOf(
                File(KISH)
            )
        ),
        Arguments.of(
            MOMENTS,
            listOf(
                File(MOMENTS)
            )
        ),
        Arguments.of(
            DDT,
            listOf(
                File(DDT)
            )
        ),
    )


    @ParameterizedTest
    @MethodSource("CommandLineProvider")
    fun commandLineTest(args: Array<String>, expected: List<List<String>>) {
        Assert.assertEquals(expected, ArgParser(args).parseInto(::CommandsHandler).hand())
    }

    fun CommandLineProvider() = listOf(
        Arguments.of(
            arrayOf("-n", "^Н.*", MOMENTS),
            listOf(
                listOf("1:Не думай о секундах свысока,",
                    "2:Наступит время - сам поймешь, наверное:",
                    "21:Не думай о секундах свысока,",
                    "22:Наступит время - сам поймешь, наверное:"
                )
            )
        ),
        Arguments.of(
            arrayOf("-nr", "свое", "./$DIR"),
            listOf(
                listOf("./src/test/resources/dir/DDT:9:Я для собаки Бог, но раб своей наготы",
                    "./src/test/resources/dir/DDT:30:И если что не разрежу умом, распакую своей душой"
                )
            )
        ),
        Arguments.of(
            arrayOf("-r", "свое", DIR),
            listOf(
                listOf("src/test/resources/dir/DDT:Я для собаки Бог, но раб своей наготы",
                    "src/test/resources/dir/DDT:И если что не разрежу умом, распакую своей душой"
                )
            )
        ),
        Arguments.of(
            arrayOf("-n", "-B1", "свое", DDT),
            listOf(
                listOf("8:Смерть разносила цветы, собака грелась у ног",
                    "9:Я для собаки Бог, но раб своей наготы",
                    "29:Я называю плохое - \"дерьмом\", а хорошее - \"красотой\"",
                    "30:И если что не разрежу умом, распакую своей душой"
                )
            )
        ),
        Arguments.of(
            arrayOf("-nA1", "-B1", "свое", DDT),
            listOf(
                listOf("8:Смерть разносила цветы, собака грелась у ног",
                    "9:Я для собаки Бог, но раб своей наготы",
                    "10:Следы ушедших героев унёс весенний прибой",
                    "29:Я называю плохое - \"дерьмом\", а хорошее - \"красотой\"",
                    "30:И если что не разрежу умом, распакую своей душой",
                    "31:К чёрту слёзы - от них тоска, наше время не терпит соплей"
                )
            )
        ),
        Arguments.of(
            arrayOf("-n", "-r", "волк", RESOURCES),
            listOf(
                listOf("src/test/resources/dir/DDT:19:Я - пастырь, я - красный волк, дрессировке не поддаюсь"),
                listOf("src/test/resources/dir/dir2/KISH.txt:13:Что нравится ему подкармливать волков",
                    "src/test/resources/dir/dir2/KISH.txt:18:И волки среди ночи завыли под окном",)
            )
        )
    )

    @Test
    fun RecursiveExceptionTest() {
        val exception: Exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            ArgParser(arrayOf("-n", "волк", RESOURCES)).parseInto(::CommandsHandler).hand()
        }
        Assert.assertEquals("This is directory", exception.message)
    }
}