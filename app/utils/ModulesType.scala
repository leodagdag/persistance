package utils

/**
 * User: leodagdag
 * Date: 13/03/12
 * Time: 22:24
 */

sealed abstract class ModuleType(val i18nKey: String)

object ModulesType {

  object BLOG extends ModuleType("module.blog")

  object PORTFOLIO extends ModuleType("module.portfolio")

  object VITRINE extends ModuleType("module.vitrine")

}
