// The router is a Singleton.
// It is responsible of displaying your page given an URL.
//
// Some views in the application are always rendered: the AppViews.
per.Router = Backbone.Router.extend({

  routes: {
    "":                     "home",    // index.html
    "search/:query":        "search",  // index.html#search/kiwis
    "search/:query/p:page": "search"   // index.html#search/kiwis/p7
  },

  initialize: function(){
    this.createAppView();
    this.renderAppView();
  },

  // instantiate views that will be used in the whole application.
  createAppView: function(){
    per.view.todo = new per.view.TodoView();
    // ...
  },

  // insert into the DOM, views that need to be rendered in the whole
  // application.
  renderAppView: function(){
    $('body').append(per.view.todo.render().el);
    // ...
  },

  // Index page
  home: function() {
    console.log("Welcome home!");
  },

  // Search page
  search: function(query, page) {
    console.log("You search", query, page);
  }

});

$(function(){
  per.router = new per.Router();
  Backbone.history.start();
});
