package utils

/**
 * User: leodagdag
 * Date: 13/03/12
 * Time: 22:24
 */

sealed abstract class ModuleType(val i18nKey: String)

object ModuleType {

  object INDEX extends ModuleType("module.index")
  
  object LOGIN extends ModuleType("module.login")
  
  object BLOG extends ModuleType("module.blog")
  
  object ADMINISTRATION extends ModuleType("module.admin")

  object PORTFOLIO extends ModuleType("module.portfolio")
  
  object SHOP extends ModuleType("module.shop")

}
