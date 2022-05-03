import org.junit.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.io.FileNotFoundException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileStringsHandlerTest {

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

    val KISH_FILE: File = File(KISH)
    val MOMENTS_FILE: File = File(MOMENTS)
    val DDT_FILE: File = File(DDT)

    @ParameterizedTest
    @MethodSource("ContextProvider")
    fun getFilesTest(context: Context, expected: List<String>) {
        Assert.assertEquals(expected, FileStringsHandler(context).run())
    }

    fun ContextProvider(): List<Arguments> {
        return listOf(
            Arguments.of(
                Context(KISH_FILE, Regex("лес"), true, true, 0, 0, KISH),
                listOf(
                    "src/test/resources/dir/dir2/KISH.txt:2:И в доме лесника я ночлега попросил",
                    "src/test/resources/dir/dir2/KISH.txt:21:Друзья хотят покушать, пойдем приятель в лес"
                )
            ),
            Arguments.of(
                Context(KISH_FILE, Regex("лес"), true, false, 0, 0, KISH),
                listOf(
                    "2:И в доме лесника я ночлега попросил",
                    "21:Друзья хотят покушать, пойдем приятель в лес"
                )
            ),
            Arguments.of(
                Context(KISH_FILE, Regex("лес"), false, true, 0, 0, KISH),
                listOf(
                    "src/test/resources/dir/dir2/KISH.txt:И в доме лесника я ночлега попросил",
                    "src/test/resources/dir/dir2/KISH.txt:Друзья хотят покушать, пойдем приятель в лес"
                )
            ),
            Arguments.of(
                Context(KISH_FILE, Regex("лес"), false, false, 0, 0, KISH),
                listOf(
                    "И в доме лесника я ночлега попросил",
                    "Друзья хотят покушать, пойдем приятель в лес"
                )
            ),
            Arguments.of(
                Context(KISH_FILE, Regex("лес"), true, false, 1, 2, KISH),
                listOf(
                    "1:Замученный дорогой, я выбился из сил",
                    "2:И в доме лесника я ночлега попросил",
                    "3:С улыбкой добродушной старик меня впустил",
                    "19:Старик заулыбался и вдруг покинул дом",
                    "20:Но вскоре возвратился с ружьем на перевес",
                    "21:Друзья хотят покушать, пойдем приятель в лес",
                    "22:Будь как дома, путник, я ни в чём не откажу"
                )
            ),
            Arguments.of(
                Context(KISH_FILE, Regex("лес"), false, false, 1, 2, KISH),
                listOf(
                    "Замученный дорогой, я выбился из сил",
                    "И в доме лесника я ночлега попросил",
                    "С улыбкой добродушной старик меня впустил",
                    "Старик заулыбался и вдруг покинул дом",
                    "Но вскоре возвратился с ружьем на перевес",
                    "Друзья хотят покушать, пойдем приятель в лес",
                    "Будь как дома, путник, я ни в чём не откажу"
                )
            ),
            Arguments.of(
                Context(KISH_FILE, Regex("я ночлега попросил"), true, false, 1, 5000, KISH),
                listOf(
                    "1:Замученный дорогой, я выбился из сил",
                    "2:И в доме лесника я ночлега попросил",
                    "3:С улыбкой добродушной старик меня впустил"
                )
            ),
            Arguments.of(
                Context(KISH_FILE, Regex("Друзья хотят покушать"), true, true, 10000, 1, KISH),
                listOf(
                    "src/test/resources/dir/dir2/KISH.txt:20:Но вскоре возвратился с ружьем на перевес",
                    "src/test/resources/dir/dir2/KISH.txt:21:Друзья хотят покушать, пойдем приятель в лес",
                    "src/test/resources/dir/dir2/KISH.txt:22:Будь как дома, путник, я ни в чём не откажу",
                    "src/test/resources/dir/dir2/KISH.txt:23:Я ни в чём не откажу, я ни в чём не откажу, (хей)",
                    "src/test/resources/dir/dir2/KISH.txt:24:Множество историй, коль желаешь, расскажу",
                    "src/test/resources/dir/dir2/KISH.txt:25:Коль желаешь расскажу, коль желаешь расскажу"
                )
            )
        )
    }

    @Test
    fun DirectoryInputExceptionTest() {
        val exception: Exception = Assertions.assertThrows(FileNotFoundException::class.java) {
            FileStringsHandler(Context(File(DIR), Regex("лес"), true, false, 0, 0, DIR)).run()
        }
        exception.message?.contains(DIR.toRegex())?.let { Assert.assertTrue(it) }
    }

}