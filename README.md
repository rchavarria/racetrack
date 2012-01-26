# racetrack

A grails application developed while learning [grails](http://grails.org) 
using this free pdf book: [Getting startted with Grails, InfoQ]
(http://www.infoq.com/minibooks/grails-getting-started)

# Commands and other stuff learned

## Create an application

`grails create-app racetrack`

Creates an application called racetrack. Then, you can see the application
structure inside the `racetrack`directory.

## Run your application

`grails [-Dserver.port=9090] run-app`

Run the web application. By default, the server is listening at port 8080,
you can change this port using the JVM option `-Dserver.port=<port>`

## Where grails store information about your projects

In the directory `~/.grails/<grails version>/projects/<project name>`

## Clean your project

`grails clean`

## Create a domain class

`grails create-domain-class <domain class name>`

Domain classes are where the data of your application is stored. Domain classes
are mapped to tables in the database.

## Create a controller

`grails create-controller <controller name>`

Controllers are classes where your bussiness logic lives. They are the C in the 
well known MVC model.

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

 


