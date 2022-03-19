package win.doyto.query.plugin.enhancement

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiField
import com.intellij.psi.impl.source.PsiClassImpl
import com.intellij.testFramework.JavaPsiTestCase
import com.intellij.testFramework.TestDataPath

/**
 * PsiFileTest
 *
 * @author f0rb on 2022-03-17
 */
@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class PsiFileTest : JavaPsiTestCase() {

    fun testVisitPsiElement() {
        val javaPsi = createFile(
            "UserQuery.java",
            "import javax.util.Collection;\n" +
                    "public class UserQuery {\n" +
                    "   private Collection idIn;\n" +
                    "}"
        )

        val children = javaPsi.children.first { e -> e is PsiClass } as PsiClass
        assertTrue(checkFieldType(children.fields[0]))
    }

    private fun checkFieldType(elem: PsiField): Boolean {
        return QueryTypeSuffix.resolve(elem.name).verifyType(project, elem.type)
    }
}