# racetrack

A grails application developed while learning [grails](http://grails.org) 
using this free pdf book: [Getting startted with Grails, InfoQ]
(http://www.infoq.com/minibooks/grails-getting-started)

# Commands and other stuff learned

### Create an application

`grails create-app racetrack`

Creates an application called racetrack. Then, you can see the application
structure inside the `racetrack`directory.

### Run your application

`grails [-Dserver.port=9090] run-app`

Run the web application. By default, the server is listening at port 8080,
you can change this port using the JVM option `-Dserver.port=<port>`

### Where grails store information about your projects

In the directory `~/.grails/<grails version>/projects/<project name>`

### Clean your project

`grails clean`

### Create a domain class

`grails create-domain-class <domain class name>`

Domain classes are where the data of your application is stored. Domain classes
are mapped to tables in the database.

### Create a controller

`grails create-controller <controller name>`

Controllers are classes where your bussiness logic lives. They are the C in the 
well known MVC model.

### Create a custom taglib

`grails create-tag-lib <taglib name>`

### Intall all templates to customize scaffolding of controllers, views, and so on

`grails install-templates`

### Testing the application

`grails test-app` launches all tests
`grails test-app -unit` launches unit tests
`grails test-app -integration` launches integration tests
`grails test-app User -unit` launches unit tests for domain class User

### Create filters

`grails create-filters <filter>` creates a filter to intercept request application wide 

### Install plugins

`grails install-plugin <plugin name>` search, download and install a plugin in your grails project

## How to validate your data

In a domain class, you can define a static closure called constraints. Inside this
closure, you can define validators for each field of your domain class. 

Here is an example:

`
static constraints {
    name(blank:false)
    startDate(validator: { it > new Date() })
}
`

There are some predefined validators, such as `blank`. Here you can find a list of
[predefined validators](http://grails.org/doc/latest/ref/Constraints/Usage.html). Or
you can define your own validator, providing a closure that returns true or false.

## How to define a one-to-many relationship between domain classes

There are two variables that you can define in a domain class: `hasMany` and `belongsTo`.
Let's imagine that class `Race` can hold several `Registration` classes. This is a
one-to-many relationship. You have to define `hasMany` in the ´Race´ class, and 
`belongsTo` in the `Registration` class.

In ´Race´ class: `def hasMany = [registration:Registration]` 
In `Registration` class: `def belongsTo = [race:Race]`

## GORM configuration

GORM stands for Grails Object Relational Mapping, and it is an abstraction of the ORM
framework used in grails, Hibernate.

You can configure your database settings in the `grails-app/conf/DataSource.groovy` file.
This file contains three blocks:
- `dataSource`: defines all common configuration for all environments.
- `hibernate`: defines specific Hibernate settings.
- `environments`: defines a specific `dataSource` for each environment: development, test 
and production.

## Controllers

The command `grails generate-all <domain class name>` generate the associated controller and
GSPs pages (views) to a domain class. This command does the same as the combination of
`grails generate-controller <domain class name>` and `grails generate-views <domain class name>`.

These commands are different from `grails create-controller`. They generate a fully implemented
controller while the later command creates and empty one.

*tip* Command `grails generate-all <domain class name>` seems to need the complete name of the
domain class that includes the package where de class is.

### URLs and Controllers

Grails uses a convention to form the URLs to call actions on controllers:

`http://localhost:8080/racetrack/user/show/2` means:

- racetrack: the name of the application. It can be changed in the file `application.properties`
in the project root directory.
- user: the name of the controller.
- show: the name of the action. It matches exactly with closures in the controller.
- 2: record id. Grails maps this value to `params.id` property, to be used in your controller code.

*tip* Scopes: grails has four scopes: request, flash, session and application

## GSP's pages

### GSP tags

You can call taglibs using `<g:` or `${}`

`<g:each>` iterates over a collection of items.
`<g:link>` creates a link, e.g. to a named controller
`<g:resource>` creates a link to a file
`<g:if test="condition">` 
`<g:render template="/layouts/header" />` renders the partial template that lives in the file
layouts/_header.gsp

### SiteMesh

Messes up two GSP pages to create a only one HTML page.

Grails uses SiteMesh to merge common, shared elements with individual GSPs using the `<g:layoutHead>` 
and `<g:layoutBody>` plug-points.   

### Partial templates

GSP pages that starts with an underscore. It means that the GSP page is not a complete page, only
a reusable part of it.

### Custom taglibs

You can create one with `grails create-tag-lib <taglib name>`.

The command creates a <tablib name>TagLib.groovy file into `grails-app/taglib` directory. You can 
define closures inside this file. Each closure will be a taglib. Let's say, you create a taglig in
a file named `FooterTagLib.groovy` called `thisYear`. The GSP code to use this taglib is:
`<g:thisYear />`, so easy.

### Customizing default templates

Command `grails install-templates`.

The template for all controllers is the file `src/templates/artifacts/Controller.groovy` and the 
template for all domain classes is the file `src/templates/artifacts/DomainClass.groovy`.

`artifacts` directory is for the `create-*` commands, while `scaffolding` directory is for 
customizing GSP pages.

Editing the `excludedProps` in `src/templates/scaffolding/create.gsp` and `edit.gsp` you can exclude
the properties of your domain classes to be shown in create and edit views. I've found `excludedProps`
in `_form.gsp` file under `scaffolding` directory, I'm using a different version of grails that the
version of the tutorial.

In a template, the code inside `test` in `` is executed during *run-time*, while the code in `id` is
executed during *generation-time*.

## Security, authentication and authorization

Whithin a domain class, use `static transients` list to tell GORM not to persist properties (or
methods like getters/setters) into a database.

### Unit tests, metaprogramming and domain classes

The `mockForConstraintsTest()` method tells the unit test to metaprogram the methods for 
constraints evaluation onto a domain class. On the other hand, `mockDomain()` takes mocking one step 
further, it allows you to mock out the entire database layer.

### Creating Codecs

Create a file that ends with `Codec` in the `grails-app/utils` directory. Let's say, you create a file
named `UnderscoreCodec.groovy`, grails will automatically metaprogram these two methods in the 
String class: `String.encodeAsUnderscore()` and `String.decodeUnderscore()`. Cool!!

### Interceptors

In the UserController class, you can create a closure called `beforeInterceptor` to add an interceptor 
that is called before every controller action.

### Filters

The `beforeInterceptor` allows you to intercept requests on a per-controller basis. Filters allow you to 
do the same thing on an applicationwide.
basis.

## Plugins

To install a plugin, e.g., called `searchable`, run the command `grails install-plugin searchable`.

This happened to me: I'm not able to see the directory where grails is supposed to install plugins:
`C:/Users/rchavarria/.grails/2.0.0/...`. To fix this and to be able to see plugin files, I had to modify
`grails-app/conf/BuildConfig.groovy` and add the following property `grails.project.plugins.dir='plugins'`.
Now, all plugins are installed project-wide.

## Services

From the tutorial: 'Controllers are usually focused on the care and feeding of a single domain class. 
Services, on the other hand, are places to put business logic and behavior that is applicable to 
more than one domain class'.

## URL mappings

One can redefine URL mappings in the file `grails-app/conf/UrlMapping.groovy`.

## Production deployment checklist

- `DataSource.groovy`: production datasource points to a different database from development datasource
- `BootStrap.groovy`: chech that we only create Users, Races, ..., on development mode
- `Config.groovy`: check `environment` block. the properties defined here will be accessible 
via: `grailsApplication.config.<property>`
- `application.properties`: modify accordingly `app.name` and `app.version`

- 