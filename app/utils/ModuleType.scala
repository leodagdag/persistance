package utils

/**
 * User: leodagdag
 * Date: 13/03/12
 * Time: 22:24
 */

sealed abstract class ModuleType

object ModuleType {

  object HOME extends ModuleType
  
  object LOGIN extends ModuleType
  
  object BLOG extends ModuleType
  
  object ADMINISTRATION extends ModuleType

  object PORTFOLIO extends ModuleType
  
  object SHOP extends ModuleType

}
