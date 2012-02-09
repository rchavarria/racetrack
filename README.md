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