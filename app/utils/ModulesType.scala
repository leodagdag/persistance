package utils

/**
 * User: leodagdag
 * Date: 13/03/12
 * Time: 22:24
 */

sealed abstract class ModulesType(val i18nKey: String)

object ModulesType {

  object HOME extends ModulesType("module.home")

  object BLOG extends ModulesType("module.blog")
  
  object BLOG_ADMIN extends ModulesType("module.blog.admin")

  object PORTFOLIO extends ModulesType("module.portfolio")
  
  object SHOP extends ModulesType("module.shop")

}
