package win.doyto.query.plugin.enhancement

import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiType
import java.util.*
import java.util.regex.Pattern
import java.util.stream.Collectors

/**
 * QueryTypeSuffix
 *
 * @author f0rb on 2022-03-19
 */
enum class QueryTypeSuffix {
    In("java.util.Collection"),
    NONE;

    private var constraintType = ""

    constructor()

    constructor(type: String) {
        this.constraintType = type
    }

    fun verifyType(project: Project, type: PsiType): Boolean {
        val elementFactory = JavaPsiFacade.getElementFactory(project)
        val psiType = elementFactory.createTypeFromText(constraintType, null)
        return psiType == type
    }

    companion object {

        private val SUFFIX_PTN = Pattern.compile(
            Arrays.stream(values())
                .filter { querySuffix: QueryTypeSuffix -> querySuffix != NONE }
                .map { obj: QueryTypeSuffix -> obj.name }
                .collect(Collectors.joining("|", "(", ")$"))
        )

        fun resolve(fieldName: String): QueryTypeSuffix {
            val matcher = SUFFIX_PTN.matcher(fieldName)
            return if (matcher.find()) valueOf(matcher.group()) else NONE
        }
    }

}