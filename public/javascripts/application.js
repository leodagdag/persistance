(function(){

  // Welcome
  // This is the starting point of our application.
  //
  // We begin by creating a namespace `xx`. Choose the name you want.
  window.per = {};

  // Then the view namespace.
  // We will put class (starting with an upper case),
  // and instances (starting with a lower case) shared in the whole application
  per.view = {};

  // Then the model namespace.
  // We will put here Models and Collections.
  // A collection share the Model name but end with a `s` (the plural of the Model).
  // For exemple:
  //
  // * Model:      Todo
  // * Collection: Todos
  //
  // Class and instances(global in the whole application) will be present under this namespace.
  per.model = {};

  // Put under this namespace home made utils functions.
  per.util = {};

})();
