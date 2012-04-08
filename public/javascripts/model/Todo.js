// Create your model this way.
per.model.MyThing = Backbone.Model.extend({

});

// Don't forget `per.model.MyThing` is a class.
// If want to share the instance of this model in all your application (Singleton),
// just remove the following comment.
// per.model.myThing = new per.model.MyThing();


// And your collection like this.
per.model.MyThings = Backbone.Collection.extend({
  model: per.model.MyThing
});

// Like for Model, your can use Singleton for your Collections.
// Remove the following comment if you need one.
// per.model.myThings = new per.model.MyThings();
